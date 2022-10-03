package spring.testcode.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import spring.testcode.domain.BookRepository;
import spring.testcode.dto.request.BookSaveReqDto;
import spring.testcode.dto.response.BookRespDto;
import spring.testcode.service.BookService;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BookApiControllerTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestRestTemplate rt;

    private static ObjectMapper om;
    private static HttpHeaders headers;

    @BeforeAll
    static void init(){
        om = new ObjectMapper();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }


    @AfterEach
    void clear(){
        bookRepository.deleteAll();
    }

    @Test
    void saveBook() throws JsonProcessingException {
        //given
        BookSaveReqDto bookSaveReqDto = new BookSaveReqDto();
        bookSaveReqDto.setTitle("스프링");
        bookSaveReqDto.setAuthor("저자");

        String body = om.writeValueAsString(bookSaveReqDto);

        //when
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = rt.exchange("/api/v1/book", HttpMethod.POST, request, String.class);

        //then
        System.out.println("response.getBody() = " + response.getBody());
        DocumentContext dc = JsonPath.parse(response.getBody());
        String title = dc.read("$.data.title");
        String author = dc.read("$.data.author");

        assertThat(title).isEqualTo(bookSaveReqDto.getTitle());
        assertThat(author).isEqualTo(bookSaveReqDto.getAuthor());
    }

    @Test
    void saveBook_exception() throws JsonProcessingException {
        //given
        BookSaveReqDto bookSaveReqDto = new BookSaveReqDto();
        bookSaveReqDto.setTitle("스프링");
        bookSaveReqDto.setAuthor("");

        String body = om.writeValueAsString(bookSaveReqDto);

        //when
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = rt.exchange("/api/v1/book", HttpMethod.POST, request, String.class);

        //then
        DocumentContext dc = JsonPath.parse(response.getBody());
        boolean success = Boolean.parseBoolean(dc.read("$.success").toString());

        assertThat(success).isFalse();
    }

    @Test
    void getBookList(){
        //given
        BookSaveReqDto bookSaveReqDto = new BookSaveReqDto();
        bookSaveReqDto.setTitle("스프링");
        bookSaveReqDto.setAuthor("저자");
        BookRespDto save = bookService.save(bookSaveReqDto);

        //when
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = rt.exchange("/api/v1/book", HttpMethod.GET, request, String.class);

        //then
        DocumentContext dc = JsonPath.parse(response.getBody());
        boolean success = Boolean.parseBoolean(dc.read("$.success").toString());

        String title = dc.read("$.data.items[0].title");
        String author = dc.read("$.data.items[0].author");

        assertThat(success).isTrue();
        assertThat(title).isEqualTo("스프링");
        assertThat(author).isEqualTo("저자");
    }

    @Test
    void getBookOne(){
        //given
        BookSaveReqDto bookSaveReqDto = new BookSaveReqDto();
        bookSaveReqDto.setTitle("스프링");
        bookSaveReqDto.setAuthor("저자");
        BookRespDto save = bookService.save(bookSaveReqDto);

        //when
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = rt.exchange("/api/v1/book/" + save.getId(), HttpMethod.GET, request, String.class);

        //then
        DocumentContext dc = JsonPath.parse(response.getBody());
        boolean success = Boolean.parseBoolean(dc.read("$.success").toString());

        String title = dc.read("$.data.title");
        String author = dc.read("$.data.author");

        assertThat(success).isTrue();
        assertThat(title).isEqualTo("스프링");
        assertThat(author).isEqualTo("저자");
    }

    @Test
    void getBookOne_exception(){
        //given
        String exception_url = "/api/v1/book/2";

        //when
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = rt.exchange(exception_url, HttpMethod.GET, request, String.class);

        //then
        DocumentContext dc = JsonPath.parse(response.getBody());

        boolean success = Boolean.parseBoolean(dc.read("$.success").toString());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(success).isFalse();
    }

    @Test
    void deleteBook_test(){
        BookSaveReqDto bookSaveReqDto = new BookSaveReqDto();
        bookSaveReqDto.setTitle("스프링");
        bookSaveReqDto.setAuthor("저자");
        BookRespDto save = bookService.save(bookSaveReqDto);

        //when
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = rt.exchange("/api/v1/book/" + save.getId(), HttpMethod.DELETE, request, String.class);

        //then
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void updateBook_test() throws JsonProcessingException {
        BookSaveReqDto bookSave = new BookSaveReqDto();
        bookSave.setTitle("스프링");
        bookSave.setAuthor("저자");
        BookRespDto save = bookService.save(bookSave);

        //given
        BookSaveReqDto bookSaveReqDto = new BookSaveReqDto();
        bookSaveReqDto.setTitle("junit");
        bookSaveReqDto.setAuthor("woojin");

        //when
        HttpEntity<String> request = new HttpEntity<>(om.writeValueAsString(bookSaveReqDto), headers);
        ResponseEntity<String> response = rt.exchange("/api/v1/book/"+ save.getId(), HttpMethod.PUT, request, String.class);

        //then
        BookRespDto findBook = bookService.findById(save.getId());
        assertThat(findBook.getTitle()).isEqualTo(bookSaveReqDto.getTitle());
        assertThat(findBook.getAuthor()).isEqualTo(bookSaveReqDto.getAuthor());
    }
}

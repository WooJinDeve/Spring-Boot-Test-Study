package spring.testcode.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.testcode.domain.Book;
import spring.testcode.domain.BookRepository;
import spring.testcode.dto.response.BookListRespDto;
import spring.testcode.dto.response.BookRespDto;
import spring.testcode.dto.request.BookSaveReqDto;
import spring.testcode.exception.NoSuchBookItemException;
import spring.testcode.util.MailSender;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MailSender mailSender;

    @Test
    void 책_등록(){
        //given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("junit");
        dto.setAuthor("create");

        //stub
        when(bookRepository.save(any())).thenReturn(dto.toEntity());
        when(mailSender.sender()).thenReturn(true);

        //when
        BookRespDto saveBook = bookService.save(dto);

        //then
        assertEquals(dto.getTitle(), saveBook.getTitle());
        assertEquals(dto.getAuthor(), saveBook.getAuthor());

        assertThat(saveBook.getTitle()).isEqualTo(dto.getTitle());
        assertThat(saveBook.getAuthor()).isEqualTo(dto.getAuthor());
    }

    @Test
    void 책_전체조회(){
        //given
        Book book1 = Book.builder().title("title1").author("author1").build();
        Book book2 = Book.builder().title("title2").author("author2").build();

        //stub
        List<Book> books = Arrays.asList(book1, book2);
        when(bookRepository.findAll()).thenReturn(books);

        //when
        BookListRespDto bookListRespDto = bookService.findAll();
        List<BookRespDto> bookList = bookListRespDto.getItems();

        //then
        assertThat(bookList).containsExactly(BookRespDto.toDto(book1), BookRespDto.toDto(book2));
        assertThat(bookList.get(0).getTitle()).isEqualTo("title1");
        assertThat(bookList.get(0).getAuthor()).isEqualTo("author1");
        assertThat(bookList.get(1).getTitle()).isEqualTo("title2");
        assertThat(bookList.get(1).getAuthor()).isEqualTo("author2");
    }

    @Test
    void 책_한건조회(){
        //given
        Long id = 1L;

        //stub
        Book book = new Book(id, "title1", "author1");
        Optional<Book> bookOptional = Optional.of(book);
        when(bookRepository.findById(id)).thenReturn(bookOptional);

        //when
        BookRespDto findBook = bookService.findById(id);

        //then
        assertThat(findBook).isEqualTo(BookRespDto.toDto(book));
        assertThat(findBook.getTitle()).isEqualTo(book.getTitle());
        assertThat(findBook.getAuthor()).isEqualTo(book.getAuthor());
    }

    @Test
    void 책_한건조회_예외처리(){
        //given
        Long id = 1L;

        //when

        //then
        assertThatThrownBy(() -> bookService.findById(id))
                .isInstanceOf(NoSuchBookItemException.class);
    }

    @Test
    void 책_수정(){
        //given
        Long id = 1L;
        BookSaveReqDto bookSaveReqDto = new BookSaveReqDto();
        bookSaveReqDto.setTitle("junit");
        bookSaveReqDto.setAuthor("woojin");

        //stub
        Book book = new Book(id, "title1", "author1");
        Optional<Book> bookOptional = Optional.of(book);
        when(bookRepository.findById(id)).thenReturn(bookOptional);

        //when
        bookService.update(1L, bookSaveReqDto);
        BookRespDto findBook = bookService.findById(1L);

        //then
        assertThat(findBook.getTitle()).isEqualTo(bookSaveReqDto.getTitle());
        assertThat(findBook.getAuthor()).isEqualTo(bookSaveReqDto.getAuthor());
    }
}

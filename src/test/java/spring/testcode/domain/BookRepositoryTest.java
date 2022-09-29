package spring.testcode.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest // DB와 관련된 컴포넌트만 메모리에 로딩
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void init(){
        String title = "junit";
        String author = "developer";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();
        bookRepository.save(book);
    }


    @Test
    @DisplayName("책 등록")
    void 책_등록(){
        //given
        String title = "junit5";
        String author = "devel";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();

        //when
        Book bookEntity = bookRepository.save(book);

        //then
        assertEquals(title, bookEntity.getTitle());
        assertEquals(author, bookEntity.getAuthor());

        assertThat(title).isEqualTo(bookEntity.getTitle());
        assertThat(author).isEqualTo(bookEntity.getAuthor());
    }

    @Test
    @DisplayName("책 목록보기")
    void 책목록보기(){
        //given (데이터 준비)
        String title = "junit";
        String author = "developer";

        //when
        List<Book> books = bookRepository.findAll();

        //then
        assertEquals(title, books.get(0).getTitle());
        assertEquals(author, books.get(0).getAuthor());
        assertEquals(1, books.size());


        assertThat(title).isEqualTo(books.get(0).getTitle());
        assertThat(author).isEqualTo(books.get(0).getAuthor());
        assertThat(1).isEqualTo(books.size());
    }

    @Sql("classpath:db/tableInit.sql")
    @Test
    @DisplayName("책 한건보기")
    void 책_한건보기(){
        //given (데이터 준비)
        String title = "junit";
        String author = "developer";

        //when
        Book findBook = bookRepository.findById(1L).get();

        //then
        assertEquals(title, findBook.getTitle());
        assertEquals(author, findBook.getAuthor());

        assertThat(title).isEqualTo(findBook.getTitle());
        assertThat(author).isEqualTo(findBook.getAuthor());

    }

    @Sql("classpath:db/tableInit.sql")
    @Test
    @DisplayName("책 수정")
    void 책_수정(){
        //given
        Long id = 1L;
        String title = "google";
        String author = "korea";
        Book book = new Book(1L, title, author);

        //when
        Book findBook = bookRepository.save(book);

        //then
        assertEquals(title, findBook.getTitle());
        assertEquals(author, findBook.getAuthor());

        assertThat(title).isEqualTo(findBook.getTitle());
        assertThat(author).isEqualTo(findBook.getAuthor());
    }

    @Sql("classpath:db/tableInit.sql")
    @Test
    @DisplayName("책 삭제")
    void 책_삭제(){
        //given (데이터 준비)
        Long id = 1L;

        //when
        bookRepository.deleteById(id);

        // then
        Optional<Book> findBook = bookRepository.findById(id);

        assertFalse(findBook.isPresent());
        assertThat(findBook.isPresent()).isFalse();
    }
}

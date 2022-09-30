package spring.testcode.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import spring.testcode.dto.BookRespDto;
import spring.testcode.dto.BookSaveReqDto;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    public void 책등록(){
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("junit");
        dto.setAuthor("create");
        BookRespDto save = bookService.save(dto);

        assertEquals(dto.getTitle(), save.getTitle());
        assertEquals(dto.getAuthor(), save.getAuthor());

        assertThat(dto.getTitle()).isEqualTo(save.getTitle());
        assertThat(dto.getAuthor()).isEqualTo(save.getAuthor());
    }
}

package spring.testcode.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@DataJpaTest // DB와 관련된 컴포넌트만 메모리에 로딩
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("책 등록")
    void 책_등록(){
        log.info("책 등록 test 실행");
        Book book = Book.builder()
                .title("book1")
                .author("author1")
                .build();

    }

    @Test
    @DisplayName("책 목록보기")
    void selectAll(){

    }

    @Test
    @DisplayName("책 한건보기")
    void selectOne(){

    }

    @Test
    @DisplayName("책 수정")
    void update(){

    }

    @Test
    @DisplayName("책 삭제")
    void delete(){

    }
}

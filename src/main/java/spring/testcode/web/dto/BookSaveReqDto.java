package spring.testcode.web.dto;

import lombok.Data;
import spring.testcode.domain.Book;

@Data
public class BookSaveReqDto {
    private String title;
    private String author;

    public Book toEntity(){
        return Book.builder()
                .title(title)
                .author(author)
                .build();
    }
}

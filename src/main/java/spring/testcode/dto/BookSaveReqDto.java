package spring.testcode.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

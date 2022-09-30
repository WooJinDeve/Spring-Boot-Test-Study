package spring.testcode.dto.request;

import lombok.Data;
import spring.testcode.domain.Book;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class BookSaveReqDto {

    @NotBlank
    @Size(min = 1, max = 50)
    private String title;

    @NotBlank
    @Size(min = 1, max = 20)
    private String author;

    public Book toEntity(){
        return Book.builder()
                .title(title)
                .author(author)
                .build();
    }
}

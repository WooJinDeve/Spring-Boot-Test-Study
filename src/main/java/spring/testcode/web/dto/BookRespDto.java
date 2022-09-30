package spring.testcode.web.dto;

import lombok.Data;
import spring.testcode.domain.Book;

@Data
public class BookRespDto {
    private Long id;
    private String title;
    private String author;

    public static BookRespDto toDto(Book book){
        BookRespDto dto = new BookRespDto();
        dto.id = book.getId();
        dto.title = book.getTitle();
        dto.author = book.getAuthor();
        return dto;
    }
}

package spring.testcode.dto.response;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BookListRespDto {
    List<BookRespDto> items;
}

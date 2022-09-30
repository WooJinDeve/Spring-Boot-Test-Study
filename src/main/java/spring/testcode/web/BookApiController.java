package spring.testcode.web;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.testcode.dto.response.BookListRespDto;
import spring.testcode.dto.response.BookRespDto;
import spring.testcode.dto.request.BookSaveReqDto;
import spring.testcode.dto.response.CommonResponse;
import spring.testcode.exception.BookSaveValidateException;
import spring.testcode.service.BookService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookApiController {

    private final BookService bookService;

    /**
     * 도서 등록
     */
    @GetMapping("/api/v1/book")
    public ResponseEntity<?> save(@RequestBody @Valid BookSaveReqDto bookSaveReqDto, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            Map<String, String> errorMap = bindingResult.getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage, (a, b) -> b));
            throw new BookSaveValidateException(errorMap.toString());
        }

        BookRespDto bookRespDto = bookService.save(bookSaveReqDto);
        return  new ResponseEntity<>(CommonResponse.success(bookRespDto),HttpStatus.CREATED);
    }

    @GetMapping("/api/v1/list")
    public ResponseEntity<?>  bookList(){
        BookListRespDto bookList = bookService.findAll();
        return new ResponseEntity<>(CommonResponse.success(bookList),HttpStatus.OK);
    }

    public ResponseEntity<?>  getBookOne(){
        return null;
    }

    public ResponseEntity<?>  update(){
        return null;
    }

    public ResponseEntity<?>  delete(){
        return null;
    }
}

package spring.testcode.web;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/api/v1/book")
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

    @GetMapping("/api/v1/book")
    public ResponseEntity<?>  bookList(){
        BookListRespDto bookList = bookService.findAll();
        return new ResponseEntity<>(CommonResponse.success(bookList),HttpStatus.OK);
    }

    @GetMapping("/api/v1/book/{id}")
    public ResponseEntity<?> getBookOne(@PathVariable Long id){
        BookRespDto findBook = bookService.findById(id);
        return new ResponseEntity<>(CommonResponse.success(findBook), HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/book/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        bookService.delete(id);
        return new ResponseEntity<>(CommonResponse.success(), HttpStatus.OK);
    }

    public ResponseEntity<?> update(){
        return null;
    }
}

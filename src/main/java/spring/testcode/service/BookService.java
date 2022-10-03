package spring.testcode.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.testcode.domain.Book;
import spring.testcode.domain.BookRepository;
import spring.testcode.dto.response.BookListRespDto;
import spring.testcode.exception.NoSuchBookItemException;
import spring.testcode.dto.response.BookRespDto;
import spring.testcode.dto.request.BookSaveReqDto;
import spring.testcode.util.MailSender;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final MailSender mailSender;

    /**
     * save : 책등록
     */
    @Transactional
    public BookRespDto save(BookSaveReqDto dto){
        Book book = bookRepository.save(dto.toEntity());
        if (book != null){
            if (!mailSender.sender()){
                throw new RuntimeException("메일이 전송되지 않았습니다.");
            }
        }
        return BookRespDto.toDto(book);
    }


    /**
     * 책 전체 목록
     */
    public BookListRespDto findAll(){
        List<BookRespDto> bookList = bookRepository.findAll().stream()
                .map(BookRespDto::toDto)
                .collect(Collectors.toList());

        return BookListRespDto.builder().items(bookList).build();
    }


    /**
     * 책 한건
     */
    public BookRespDto findById(Long id){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchBookItemException("존재하지 않는 책입니다."));
        return BookRespDto.toDto(book);
    }


    /**
     * 책 삭제
     */
    @Transactional
    public void delete(Long id){
        bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchBookItemException("존재하지 않는 책입니다."));
        bookRepository.deleteById(id);
    }

    /**
     * 책 수정하기
     */
    @Transactional
    public BookRespDto update(Long id, BookSaveReqDto dto){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchBookItemException("존재하지 않는 책입니다."));
        book.update(dto);
        return BookRespDto.toDto(book);
    }
}

package spring.testcode.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private Integer errorCode;
    private String errorMessage;
}
package net.socle.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.socle.dto.SchemeDto;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * @author Sai Regati
 * @version 1.0
 * @since 22-03-2022
 */

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class ApiResponse {
    private HttpStatus status;
    private Integer statusCode;
    private String message;
    private Object responseData;
     private Integer totalPages;

    public ApiResponse (HttpStatus httpStatus,String message){
        this.status=httpStatus;
        this.statusCode=httpStatus.value();
        this.message=message;
    }
    public ApiResponse (HttpStatus httpStatus,String message,Object response){
        this.status=httpStatus;
        this.statusCode=httpStatus.value();
        this.message=message;
        this.responseData=response;
    }

    public ApiResponse(HttpStatus status, List<SchemeDto> schemeDto, int totalPages) {

        this.status=status;
        this.statusCode=status.value();
        this.responseData=schemeDto;
        this.totalPages=totalPages;
    }
}


/**
 * 200 - Success
 * 404 - Resource Not Found
 * 403 - Authentication / accessibility failures
 * 400 - Bad Request // all exceptions related validations or input request related to be handled 403 by default
 * 500 - System error // All unexpected errors/ exceptions to be considered
 *
 */

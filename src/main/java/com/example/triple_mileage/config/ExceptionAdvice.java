package com.example.triple_mileage.config;

//import com.example.triple_mileage.response.ResponseException;
import com.example.triple_mileage.exception.AlreadyWroteReviewException;
import com.example.triple_mileage.response.ResponseObj;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import static com.example.triple_mileage.response.ResponseStatusCode.*;

@RestControllerAdvice
public class ExceptionAdvice {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseObj methodArgumentValidException(MethodArgumentNotValidException e) {
        return new ResponseObj(REQUEST_ERROR,extractErrorMessages(e));
    }

    private String extractErrorMessages(MethodArgumentNotValidException e) {
        return e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();
    }



    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseObj AlreadyExistsExceptionHandler(){
        return new ResponseObj(EXISTING_DATA);
    }

    @ExceptionHandler(value = {AlreadyWroteReviewException.class})
    public ResponseObj AlreadyWroteReview(){
        return new ResponseObj(ALREADY_WROTE_REVIEW);
    }

    @ExceptionHandler(value= {NullPointerException.class,Exception.class})
    public ResponseObj EtcExceptionHandler(Exception e){

        return new ResponseObj(DATABASE_ERROR);
    }

}

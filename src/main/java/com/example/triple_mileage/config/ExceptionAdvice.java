package com.example.triple_mileage.config;

//import com.example.triple_mileage.response.ResponseException;
import com.example.triple_mileage.response.ResponseObj;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.charset.MalformedInputException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;

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

    @ExceptionHandler(value= {DataAccessException.class})
    public ResponseObj DataAccessExceptionHandler(){
        //db관련 exception
        return new ResponseObj(DATA_NOT_FOUND);
    }



    @ExceptionHandler(value= {NullPointerException.class,Exception.class})
    public void EtcExceptionHandler(Exception e){

        System.err.println(e);
    }

}

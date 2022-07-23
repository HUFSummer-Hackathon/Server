package hackathon.nomadworker.api;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.ServiceUnavailableException;
import javax.persistence.NoResultException;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response ServerException2(Exception e) {
        e.printStackTrace();
        return new Response("서버에러", 500);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response MissingRequestHeaderException(Exception e) {
        e.printStackTrace();
        return new Response("MissingRequestHeaderException",400);
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response UnsupportedJwtException(Exception e) {
        e.printStackTrace();
        return new Response( "UnsupportedJwtException",401);
    }

    @ExceptionHandler(MalformedJwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response MalformedJwtException(Exception e) {
        e.printStackTrace();
        return new Response( "MalformedJwtException",402);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response ExpiredJwtException(Exception e) {
        e.printStackTrace();
        return new Response( "ExpiredJwtException",403);
    }

    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response SignatureException(Exception e) {
        e.printStackTrace();
        return new Response( "SignatureException",404);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response ServiceUnavailableExpection(Exception e) {
        e.printStackTrace();
        return new Response("Service Unavailable", 503);
    }

    //@ExceptionHandler(NoResultException.class)
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    //public Response NoContentExpection(Exception e) {
     //   e.printStackTrace();
     //   return new Response("No results", 400);
    //}

    //Response DTO
    @Data
    @AllArgsConstructor
    static class Response
    {
        private String message;
        private int status;
    }
}

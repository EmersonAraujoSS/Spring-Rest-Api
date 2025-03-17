package br.com.avancard;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.List;


@RestControllerAdvice
@ControllerAdvice
public class ControleExcecoes extends ResponseEntityExceptionHandler {

    //esse método vai interceptar todo erro que pertencer a essas classes
    @ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

        String msg = "";
        if (ex instanceof MethodArgumentNotValidException) {
            List<ObjectError> list = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
            for (ObjectError error : list) {
                msg += error.getDefaultMessage() + "\n";
            }
        }else {
            msg = ex.getMessage();
        }

        ObjetoError objetoError = new ObjetoError();
        objetoError.setError(msg);
        objetoError.setCode(statusCode.value()+"==> "+ HttpStatus.valueOf(statusCode.value()).getReasonPhrase());

        return new ResponseEntity<>(objetoError, headers, statusCode);
    }


    //tratamento da maioria dos erros a nível de banco de dados
    @ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class, SQLException.class})
    protected ResponseEntity<Object> handlerExceptionDataIntegry(Exception ex) {

        String msg = "";
        if (ex instanceof DataIntegrityViolationException) {
            msg = ((DataIntegrityViolationException) ex).getCause().getCause().getMessage();
        }
        else if (ex instanceof ConstraintViolationException) {
            msg = ((ConstraintViolationException) ex).getCause().getCause().getMessage();
        }
        else if (ex instanceof SQLException) {
            msg = ((SQLException) ex).getSQLState();
        }
        else {
            msg = ex.getMessage();
        }

        ObjetoError objetoError = new ObjetoError();
        objetoError.setError(msg);
        objetoError.setCode(HttpStatus.INTERNAL_SERVER_ERROR+ " ==>" + HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());


        return new ResponseEntity<>(objetoError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

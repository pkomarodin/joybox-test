package joybox.test.exception;

import org.springframework.http.HttpStatus;


public class HttpException extends Exception {

    private static final long serialVersionUID = 94544320342200359L;

    private HttpStatus httpStatus;

    public HttpException() {

    }

    public HttpException(HttpStatus httpStatus) {
        super(httpStatus.name());
        this.httpStatus = httpStatus;
    }

    public HttpException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }


    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

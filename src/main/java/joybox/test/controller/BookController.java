package joybox.test.controller;


import joybox.test.model.BaseResponse;
import joybox.test.model.Book;
import joybox.test.model.ListDataBaseResponse;
import joybox.test.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/v1/book")
public class BookController {

    private final BookService bookService;
    private BaseResponse response;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping()
    public ResponseEntity<BaseResponse> index(
            @RequestParam(name = "subject") String subject
    ) {
        try {
            List<Book> bankResponseList = bookService.getBookBySubject(subject);
            List<Object> resList = new ArrayList<>(bankResponseList);
            ListDataBaseResponse res = new ListDataBaseResponse(resList, resList.size());
            response = new BaseResponse(HttpStatus.OK.value(), "success", res);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response = new BaseResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

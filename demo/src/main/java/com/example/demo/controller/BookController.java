package com.example.demo.controller;

import com.example.demo.dto.BookDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/book")
public class BookController {
    private final BookService bookService;
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseDTO createBook(@RequestBody BookDTO bookDto) throws Exception {
        try{
            ResponseDTO responseDTO = bookService.createBook(bookDto);
            return responseDTO;
        }
        catch (Exception e){
            logger.error("<BookController> - <createBook> - Throw Error." + e);
            return new ResponseDTO(e.getMessage(),"500",null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBookById(@PathVariable Long id) throws Exception {
        BookDTO bookDto = bookService.getBookById(id);
        return ResponseEntity.ok(bookDto);
    }

    @GetMapping
    public ResponseEntity<Object> getAllBooks(Pageable pageable) {
        List<BookDTO> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @RequestMapping("/bookId={id}/newStock={stock}")
    public ResponseDTO updateBook(@PathVariable Long id,@PathVariable Long stock) throws Exception {
        try {
            ResponseDTO responseDTO = bookService.updateBookStock(id, stock.intValue());
            return responseDTO;
        } catch (Exception e) {
            logger.error("<BookController> - <updateBook> - Throw Error." + e);
            return new ResponseDTO(e.getMessage(),"500",null);
        }
    }

}

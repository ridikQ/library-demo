package com.example.movie1.controller;

import com.example.movie1.model.BookEntity;
import com.example.movie1.service.BookService;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/all")
    public ResponseEntity<List<BookEntity>> getAllBooks() {
        return bookService.getAllBooks();
    }
    @GetMapping("/{id}")
    public ResponseEntity<BookEntity> getBooksById(@PathVariable long id) {
        return bookService.getBookById(id);
    }
    @PostMapping("/add")
    public ResponseEntity < BookEntity > addBook(@RequestBody BookEntity bookEntity) {
        return ResponseEntity.ok().body(bookService.addBook(bookEntity));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponseEntity<BookEntity>> updateBook(@RequestBody BookEntity bookEntity) {
        return ResponseEntity.ok().body(bookService.updateBook(bookEntity));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<BookEntity> deleteBook(@PathVariable("id") Long id) {
        return bookService.deleteBookById(id);
    }

    @PostMapping ("/{bookId}/author/{authorId}")
    boolean enrollAuthorToBook(
            @PathVariable Long bookId,
            @PathVariable Long authorId
    ){
        return bookService.assignAuthorToBook(bookId,authorId);
    }
    @GetMapping("/{authorId}/book")
    public ResponseEntity<List<BookEntity>>getAllBooksByAuthorId(@PathVariable Long authorId){
        return bookService.getAllBooksByAuthorId(authorId);
    }
    @DeleteMapping("/{bookId}/author/{authorId}")
    public ResponseEntity<BookEntity>deleteBookByAuthorId(@PathVariable("bookId") Long bookId,@PathVariable ("authorId")Long authorId) {
        return bookService.deleteBookByAuthorId(bookId,authorId);
    }



}

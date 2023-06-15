package com.example.movie1.service;

import com.example.movie1.exception.ResourceNotFoundException;
import com.example.movie1.model.AuthorEntity;
import com.example.movie1.model.BookEntity;
import com.example.movie1.repository.AuthorRepository;
import com.example.movie1.repository.BookRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRespository bookRespository;

    @Autowired
    private AuthorRepository authorRepository;

    public ResponseEntity<List<BookEntity>>  getAllBooks() {
        List<BookEntity>bookEntityList=bookRespository.findAll();
        if (!bookEntityList.isEmpty()){
            return ResponseEntity.ok().body(bookEntityList);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    public BookEntity addBook(BookEntity bookEntity) {
        return bookRespository.save(bookEntity);

    }

    public ResponseEntity<BookEntity> getBookById(Long id) {
        BookEntity bookEntity=bookRespository.findById(id).orElse(null);
        if (bookEntity!=null){
            return ResponseEntity.ok().body(bookEntity);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<BookEntity>  updateBook(BookEntity bookEntity) {
         BookEntity bookEntityl=bookRespository.save(bookEntity);
        if (!bookEntityl.equals(null)){
            return ResponseEntity.ok().body(bookEntityl);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<BookEntity> deleteBookById(Long id) {
        Optional<BookEntity>bookEntity=bookRespository.findById(id);
        if (bookEntity.isPresent()) {
            BookEntity book=bookEntity.get();
            bookRespository.delete(book);
            return ResponseEntity.ok().body(book);
        }
        return ResponseEntity.notFound().build();
    }
    public boolean assignAuthorToBook(Long bookId,Long authorId){
        BookEntity book=bookRespository.findById(bookId).orElseThrow(()-> new IllegalArgumentException("Book not found"));
        AuthorEntity author=authorRepository.findById(authorId).orElseThrow(()-> new IllegalArgumentException("Author not found"));
         authorRepository.assignAuthorToBook(bookId,authorId);
        return true;

    }
    public ResponseEntity<List<BookEntity>>getAllBooksByAuthorId(Long authorId){
        if (!authorRepository.existsById(authorId)){
            throw new ResourceNotFoundException("Not Found author ");
        }
        List<BookEntity>bookEntities=bookRespository.findBooksByAuthorId(authorId);
            return new ResponseEntity<>(bookEntities,HttpStatus.OK);
    }
    public ResponseEntity<BookEntity>deleteBookByAuthorId(Long authorId,Long bookId){
        BookEntity book=bookRespository.findById(bookId).orElseThrow((() -> new ResourceNotFoundException("Not found Book with id = " + bookId)));
        bookRespository.deleteAuthorById(authorId);
        bookRespository.save(book);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


}
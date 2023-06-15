package com.example.movie1.controller;

import com.example.movie1.model.AuthorEntity;
import com.example.movie1.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping(path = "/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/all")
    public ResponseEntity<List<AuthorEntity>> getAllAuthors(){
      return authorService.getAllAuthors();
  }
    @GetMapping("/{id}")
    public ResponseEntity<AuthorEntity> getAuthorById(@PathVariable long id) {
        return authorService.getAuthorById(id);
    }
    @PostMapping("/add")
    public ResponseEntity < AuthorEntity > addAuthor(@RequestBody AuthorEntity authorEntity) {
        return ResponseEntity.ok().body(authorService.addAuthor(authorEntity));
    }
    @PutMapping("/{id}")
    public ResponseEntity<AuthorEntity> updateAuthor(@RequestBody  AuthorEntity authorEntity) {
        return authorService.updateAuthor(authorEntity);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseEntity<AuthorEntity>> deleteAuthor(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(authorService.deleteAuthorById(id));
    }
    @GetMapping("/{bookId}/author")
    public ResponseEntity<List<AuthorEntity>> getAllAuthorsByBookId(@PathVariable long bookId){
      return authorService.getAllAuthorsByBookId(bookId);
    }

    @DeleteMapping("{authorId}/book/{bookId}")
    public ResponseEntity<AuthorEntity> deleteAuthorByBookId(@PathVariable ("authorId")Long authorId, @PathVariable ("bookId")Long bookId){
        return authorService.deleteAuthorByBookId(authorId,bookId);
    }


}

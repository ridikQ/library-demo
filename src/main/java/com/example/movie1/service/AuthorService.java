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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRespository bookRespository;

    public ResponseEntity<List<AuthorEntity>> getAllAuthors() {
        List<AuthorEntity>authorEntityList=authorRepository.findAll();
        if (!authorEntityList.isEmpty()){
            return ResponseEntity.ok().body(authorEntityList);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
    public AuthorEntity addAuthor(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);

    }
    public ResponseEntity<AuthorEntity> getAuthorById(Long id) {
        AuthorEntity authorEntity=authorRepository.findById(id).orElse(null);
        if (authorEntity!=null){
            return ResponseEntity.ok().body(authorEntity);
        }
        return ResponseEntity.notFound().build();
    }
    public ResponseEntity<AuthorEntity>  updateAuthor(AuthorEntity authorEntity) {
        AuthorEntity authorEntity1=authorRepository.save(authorEntity);
        if (!authorEntity1.equals(null)){
            return ResponseEntity.ok().body(authorEntity1);
        }
        return ResponseEntity.notFound().build();
    }
    public ResponseEntity <AuthorEntity> deleteAuthorById(Long id) {
        Optional<AuthorEntity> authorEntity=authorRepository.findById(id);
        if (authorEntity.isPresent()) {
            AuthorEntity author=authorEntity.get();
            authorRepository.delete(author);
            return ResponseEntity.ok().body(author);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<AuthorEntity>>getAllAuthorsByBookId(Long bookId){
        if (!bookRespository.existsById(bookId)){
            throw new ResourceNotFoundException("Not Found book ");
        }
        List<AuthorEntity>authorEntities=authorRepository.findAuthorByBookId(bookId);
        return new ResponseEntity<>(authorEntities, HttpStatus.OK);
    }
    public ResponseEntity<AuthorEntity>deleteAuthorByBookId(Long bookId,Long authorId){
        AuthorEntity author=authorRepository.findById(authorId).orElseThrow((() -> new ResourceNotFoundException("Not found Author with id = " + authorId)));
        authorRepository.deleteBooksById(bookId);
        authorRepository.save(author);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}

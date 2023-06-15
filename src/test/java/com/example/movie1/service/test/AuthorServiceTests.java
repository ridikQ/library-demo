package com.example.movie1.service.test;

import com.example.movie1.model.AuthorEntity;
import com.example.movie1.model.BookEntity;
import com.example.movie1.repository.AuthorRepository;
import com.example.movie1.repository.BookRespository;
import com.example.movie1.service.AuthorService;
import org.junit.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorServiceTests {

    @Autowired
    private AuthorService authorService;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private BookRespository bookRespository;

    @Test
    public void addAuthorTest(){
        AuthorEntity authors=new AuthorEntity();
        authors.setId(1L);
        authors.setName("Sosa");
        Mockito.when(authorRepository.save(Mockito.any(AuthorEntity.class))).thenReturn(authors);
        AuthorEntity created=authorService.addAuthor(authors);
        assertEquals(1L,created.getId(),1L);
        assertEquals("Sosa",created.getName());

    }

    @Test
   public void getAllAuthorsTest() {
        List<AuthorEntity> authors = new ArrayList<>();
        authors.add(new AuthorEntity(1L, "John"));

        Mockito.when(authorRepository.findAll()).thenReturn(authors);

        ResponseEntity<List<AuthorEntity>> authorList = authorService.getAllAuthors();

        Assert.assertEquals("John", authorList.getBody().get(0).getName());
        Assert.assertEquals(Long.valueOf(1), authorList.getBody().get(0).getId());
    }

    @Test
    public void deleteAuthorTest(){
        AuthorEntity author=new AuthorEntity(1L,"Anna");
        Mockito.when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));
        authorService.deleteAuthorById(author.getId());
        verify(authorRepository,times(1)).delete(author);
    }

    @Test
    public void getAuthorByIdTest(){
        AuthorEntity author=new AuthorEntity(1L,"Tory");
        Mockito.when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));
        AuthorEntity created= authorService.getAuthorById(author.getId()).getBody();
        assertEquals(1L,created.getId(),1L);
    }

    @Test
    public void updateAuthorTest() {
        AuthorEntity author = new AuthorEntity(1L, "Karl");
        Mockito.when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));
        Mockito.when(authorRepository.save(Mockito.any(AuthorEntity.class))).thenReturn(author);
        authorService.updateAuthor(author);
        verify(authorRepository, times(1)).save(author);
    }

//    @Test
//    public void getAllAuthorsByBookId(){
//        AuthorEntity author=new AuthorEntity(1L,"Tory");
//        Mockito.when(bookRespository.existsById(Mockito.any(Long.class))).thenReturn(true);
//        Mockito.when(authorRepository.findAuthorByBookId(Mockito.any(Long.class))).thenReturn((List<AuthorEntity>) author);
//
//        AuthorEntity created=authorService.getAllAuthorsByBookId(Mockito.any(Long.class)).getBody();
//        assertEquals(1L,created.);
//
//    }




}

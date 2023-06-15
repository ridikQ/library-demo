package com.example.movie1.service.test;

import com.example.movie1.model.AuthorEntity;
import com.example.movie1.model.BookEntity;
import com.example.movie1.repository.AuthorRepository;
import com.example.movie1.repository.BookRespository;
import com.example.movie1.service.AuthorService;
import com.example.movie1.service.BookService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTests {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRespository bookRespository;

    @Test
    public void addBookTest(){
        BookEntity book =new BookEntity();
        book.setId(1L);
        book.setName("Poter");
        book.setDescription("Adv");
        book.setPrice(32);
        Mockito.when(bookRespository.save(Mockito.any(BookEntity.class))).thenReturn(book);
        BookEntity created=bookService.addBook(book);

        assertEquals(1L,created.getId(),1L);
        assertEquals("Poter",created.getName());
        assertEquals("Adv",created.getDescription());
        assertEquals(32,created.getPrice(),32);
    }

    @Test
    public void getAllBooksTest() {
        List<BookEntity> books = new ArrayList<>();
        books.add(new BookEntity(1L,"Parry","Akl",21));

        Mockito.when(bookRespository.findAll()).thenReturn(books);

        ResponseEntity<List<BookEntity>> bookList = bookService.getAllBooks();

        Assert.assertEquals("Parry",bookList.getBody().get(0).getName());
        Assert.assertEquals(Long.valueOf(1),bookList.getBody().get(0).getId());
        Assert.assertEquals("Akl",bookList.getBody().get(0).getDescription());
        Assert.assertEquals(Integer.valueOf(21),bookList.getBody().get(0).getPrice());
    }

    @Test
    public void deleteBookTest(){
        BookEntity book=new BookEntity(1L,"Kentrall","4Kt",38);
        Mockito.when(bookRespository.findById(book.getId())).thenReturn(Optional.of(book));
        bookService.deleteBookById(book.getId());
        verify(bookRespository,times(1)).delete(book);
    }
    @Test
    public void getBookByIdTest(){
        BookEntity book=new BookEntity(1L,"Kentrall","4Kt",38);
        Mockito.when(bookRespository.findById(book.getId())).thenReturn(Optional.of(book));
        BookEntity created=bookService.getBookById(book.getId()).getBody();
        assertEquals(1L,created.getId(),1L);
    }

    @Test
    public void updateBookTest(){
        BookEntity book=new BookEntity(1L,"Kentrall","4Kt",38);
        Mockito.when(bookRespository.findById(book.getId())).thenReturn(Optional.of(book));
        Mockito.when(bookRespository.save(Mockito.any(BookEntity.class))).thenReturn(book);
        bookService.updateBook(book);
        verify(bookRespository,times(1)).save(book);

    }
}

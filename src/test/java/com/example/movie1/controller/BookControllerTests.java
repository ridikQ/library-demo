package com.example.movie1.controller;

import com.example.movie1.model.AuthorEntity;
import com.example.movie1.model.BookEntity;
import com.example.movie1.repository.AuthorRepository;
import com.example.movie1.repository.BookRespository;
import com.example.movie1.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.http.ResponseEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    ObjectMapper objectMapper=new ObjectMapper();
    ObjectWriter objectWriter=objectMapper.writer();

    @MockBean
    AuthorRepository authorRepository;

    @MockBean
    BookRespository bookRespository;

    @MockBean
    BookService bookService;

    BookEntity REC_1 = new BookEntity(1L,"The cave of time","Adventure",34);
    BookEntity REC_2 = new BookEntity(2L,"Pinocchio","Novel",44);
    BookEntity REC_3 = new BookEntity(3L,"Harry Potter","Novel",43);

    @Test
    public void getAllBooks_success() throws Exception{
        List<BookEntity> bookEntities=new ArrayList<>(Arrays.asList(REC_1, REC_2, REC_3));
        Mockito.when(bookRespository.findAll()).thenReturn(bookEntities);

        mockMvc.perform(MockMvcRequestBuilders.get("/book/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {ResponseEntity.ok().body(bookEntities);
                });

    }
    @Test
    public void getBookById_success() throws Exception {
       List<BookEntity> bookEntityList=new ArrayList<>();
       BookEntity bookEntity=new BookEntity(Long.valueOf(4),"Scalise","Adv",23);
       bookEntityList.add(bookEntity);

       Mockito.when(bookRespository.findById(Mockito.any(Long.class))).thenReturn(java.util.Optional.of(bookEntity));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/book/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {ResponseEntity.ok().body(bookEntityList);
                });
    }
    @Test
    public void deleteBook_success() throws Exception{
        Mockito.when(bookRespository.findById(REC_2.getId())).thenReturn(Optional.of(REC_2));
        mockMvc.perform(MockMvcRequestBuilders.delete("/book/2").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
    @Test
    public void addBook_success()throws Exception{
        BookEntity book=BookEntity.builder().id(4L).name("Scalise").description("Adv").price(34).build();

        Mockito.when(bookRespository.save(Mockito.any(BookEntity.class))).thenReturn(book);
        String content=objectWriter.writeValueAsString(book);

        MockHttpServletRequestBuilder mockRequest=MockMvcRequestBuilders.post("/book/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(result -> {ResponseEntity.ok().body(book);
                });
    }
    @Test
    public void updateBook_success()throws Exception{
        BookEntity book=BookEntity.builder().id(1l).name("William").build();
        book.setName("Lord of the Rings");
        Mockito.when(bookRespository.findById(Mockito.any(Long.class))).thenReturn(java.util.Optional.of(REC_1));
        Mockito.when(bookRespository.save(Mockito.any(BookEntity.class))).thenReturn(book);

        String content=objectWriter.writeValueAsString(book);
        MockHttpServletRequestBuilder mockRequest=MockMvcRequestBuilders.put("/book/id")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk()).andExpect(result -> {
                    ResponseEntity.ok(book);
                });
    }
    @Test
    public void getAllBooksByAuthorId_success() throws Exception{
        List<BookEntity> bookEntityList=new ArrayList<>();
        BookEntity bookEntity=new BookEntity(Long.valueOf(4),"Kadare","Klm",23);
        bookEntityList.add(bookEntity);

        Mockito.when(authorRepository.existsById(Mockito.any(Long.class))).thenReturn(true);
        Mockito.when(bookRespository.findBooksByAuthorId(Mockito.any(Long.class))).thenReturn(bookEntityList);

      Mockito.when(bookService.getAllBooksByAuthorId(Mockito.any(Long.class))).thenReturn(ResponseEntity.ok(bookEntityList));

        mockMvc.perform(MockMvcRequestBuilders.get("/book/4/book").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(result -> {
                            ResponseEntity.ok(bookEntityList);
                        }
                );
    }



}

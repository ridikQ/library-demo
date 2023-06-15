package com.example.movie1.controller;

import com.example.movie1.model.AuthorEntity;
import com.example.movie1.model.BookEntity;
import com.example.movie1.repository.AuthorRepository;
import com.example.movie1.repository.BookRespository;
import com.example.movie1.service.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerTests {

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
	AuthorService authorService;

	AuthorEntity RECORD_1 = new AuthorEntity(1L,"William Shakespeare");
	AuthorEntity RECORD_2 = new AuthorEntity(2L,"William Faulkner");
	AuthorEntity RECORD_3 = new AuthorEntity(3L,"Franz Kafka");

	BookEntity RECORD_4=new BookEntity(4L,"Ramen","History",45);

	@Test
	public void getAllAuthors_success() throws Exception{
		List<AuthorEntity>authorEntities=new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));
		Mockito.when(authorRepository.findAll()).thenReturn(authorEntities);

	mockMvc.perform(MockMvcRequestBuilders.get("/author/all").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(result -> {ResponseEntity.ok().body(authorEntities);
			});

	}
@Test
public void getAuthorById_success() throws Exception {
	List<AuthorEntity> authorEntityList=new ArrayList<>();
	AuthorEntity authorEntity=new AuthorEntity(Long.valueOf(4),"Scalise");
	authorEntityList.add(authorEntity);

	Mockito.when(authorRepository.findById(Mockito.any(Long.class))).thenReturn(java.util.Optional.of(authorEntity));
	mockMvc.perform(MockMvcRequestBuilders
					.get("/author/1")
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(result -> {ResponseEntity.ok().body(authorEntityList);
});
}
@Test
	public void addAuthor_success()throws Exception{
		AuthorEntity author=AuthorEntity.builder()
				.id(4L)
				.name("Klm").build();

		Mockito.when(authorRepository.save(Mockito.any(AuthorEntity.class))).thenReturn(author);
		String content=objectWriter.writeValueAsString(author);

		MockHttpServletRequestBuilder mockRequest=MockMvcRequestBuilders.post("/author/add")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(content);
		mockMvc.perform(mockRequest)
				.andExpect(status().isOk())
				.andExpect(result -> {ResponseEntity.ok().body(author);
				});


}

@Test
	public void deleteAuthor_success() throws Exception{
		Mockito.when(authorRepository.findById(RECORD_2.getId())).thenReturn(Optional.of(RECORD_2));
		mockMvc.perform(MockMvcRequestBuilders.delete("/author/2")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
}

@Test
	public void updateAuthor_success()throws Exception{
	AuthorEntity author=AuthorEntity.builder().id(1l).name("William Shakesp").build();
    author.setName("George Orwell");
	Mockito.when(authorRepository.findById(Mockito.any(Long.class))).thenReturn(java.util.Optional.of(RECORD_1));
	Mockito.when(authorRepository.save(Mockito.any(AuthorEntity.class))).thenReturn(author);

	String content=objectWriter.writeValueAsString(author);
	MockHttpServletRequestBuilder mockRequest=MockMvcRequestBuilders.put("/author/id")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content(content);

	mockMvc.perform(mockRequest)
			.andExpect(status().isOk()).andExpect(result -> {
                ResponseEntity.ok(author);
			});
}


@Test
	public void getAllAuthorsByBookId_success() throws Exception{
    List<AuthorEntity> authorEntityList=new ArrayList<>();
	AuthorEntity authorEntity=new AuthorEntity(Long.valueOf(4),"Scalise");
	authorEntityList.add(authorEntity);

	Mockito.when(bookRespository.existsById(Mockito.any(Long.class))).thenReturn(true);
	Mockito.when(authorRepository.findAuthorByBookId(Mockito.any(Long.class))).thenReturn(authorEntityList);

	Mockito.when(authorService.getAllAuthorsByBookId(Mockito.any(Long.class))).thenReturn(ResponseEntity.ok(authorEntityList));

		mockMvc.perform(MockMvcRequestBuilders.get("/author/4/author").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(result -> {
					ResponseEntity.ok(authorEntityList);
						}
						);
}
//@Test
//	public void deleteAuthorByBookId_success() throws  Exception {
//		List<AuthorEntity>authorEntityList=new ArrayList<>();
//		AuthorEntity authorEntity=new AuthorEntity(Long.valueOf(4),"Tom");
//		authorEntityList.add(authorEntity);
//
//		Mockito.when(authorRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(authorEntity));
//		Mockito.when(authorRepository.deleteBooksById(Mockito.any(Long.class))).thenReturn(ResponseEntity.ok());
//}
//


}

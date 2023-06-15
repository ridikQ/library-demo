package com.example.movie1.repository;


import com.example.movie1.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BookRespository extends JpaRepository<BookEntity,Long> {
    @Transactional
    @Modifying
    @Query(value = "SELECT b FROM BookEntity b JOIN AuthorBook c ON b.id=c.bookId WHERE c.authorId = :authorId")
    List<BookEntity> findBooksByAuthorId(@Param("authorId") Long authorId);

    @Transactional
    @Modifying
    @Query("DELETE FROM AuthorBook c WHERE c.bookId = :bookId")
    void deleteAuthorById(@Param("bookId") Long bookId);
}

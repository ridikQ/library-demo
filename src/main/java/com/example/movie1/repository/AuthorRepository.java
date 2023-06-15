package com.example.movie1.repository;

import com.example.movie1.model.AuthorEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity,Long> {
    @Query("SELECT b FROM AuthorEntity b JOIN AuthorBook c ON b.id=c.authorId WHERE c.bookId= :bookId")
   List<AuthorEntity>  findAuthorByBookId(@Param("bookId") Long bookId);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO author_book  (book_id,author_id) VALUES (:bookId,:authorId)",nativeQuery = true)
    void assignAuthorToBook(@Param("bookId") Long bookId,@Param("authorId") Long authorId);

    @Transactional
    @Modifying
    @Query("DELETE FROM AuthorBook c WHERE c.authorId = :authorId")
    void deleteBooksById(@Param("authorId") Long authorId);

}

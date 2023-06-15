package com.example.movie1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "author")
@Builder
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="SEQUENCE2")
    @SequenceGenerator(name="SEQUENCE2", sequenceName="SEQUENCE2", allocationSize=1)
    private Long id;

//    @JsonIgnore
//   @ManyToMany(mappedBy = "enrolledAuthors")
//   private Set<BookEntity> enrolledBooks=new HashSet<>();


    @Column(name = "name")
    private String name;

}

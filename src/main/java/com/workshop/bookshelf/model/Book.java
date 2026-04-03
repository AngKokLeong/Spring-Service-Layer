package com.workshop.bookshelf.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "books")
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name="title", nullable = false, length = 150)
    private String title;

    @NotBlank
    @Column(name="author", nullable = false, length = 100)
    private String author;

    @Min(1000)
    @Max(2099)
    @Column(name="genre", nullable=false, length= 100)
    private String genre;

    @NotBlank
    @Column(name="publication_year")
    private Integer publicationYear;



}

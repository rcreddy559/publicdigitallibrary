package com.library.mapper;

import com.library.dto.BookResponse;
import com.library.model.Book;
import org.springframework.stereotype.Service;

@Service
public class BookMapper {

    public Book toBook(BookResponse bookResponse) {
        return Book.builder().id(bookResponse.getId())
                .title(bookResponse.getTitle())
                .author(bookResponse.getAuthor())
                .isbn(bookResponse.getIsbn())
                .publishedYear(bookResponse.getPublishedYear())
                .available(bookResponse.getAvailable())
                .build();

    }

    public BookResponse toBookResponse(Book bookResponse) {
        return BookResponse.builder().id(bookResponse.getId())
                .title(bookResponse.getTitle())
                .author(bookResponse.getAuthor())
                .isbn(bookResponse.getIsbn())
                .publishedYear(bookResponse.getPublishedYear())
                .available(bookResponse.getAvailable())
                .build();

    }
}

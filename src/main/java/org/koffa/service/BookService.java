package org.koffa.service;

import org.koffa.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getBooks();
    Book getBook(String id);
    Book addBook(Book book);
    Book updateBook(Book book);
    String deleteBook(Book book);
}

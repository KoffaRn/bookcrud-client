package org.koffa;

import org.koffa.model.Book;
import org.koffa.service.BookService;

import java.util.*;

public class App {
    private final BookService bookService;
    private boolean running = true;
    public App(BookService bookService) {
        this.bookService = bookService;
    }
    public void run() {
        while(running)
            mainMenu();
    }

    private void mainMenu() {
        System.out.println("1. List books");
        System.out.println("2. Add book");
        System.out.println("3. Exit");
        switch (getIntInput(3)) {
            case 1:
                listBooks();
                break;
            case 2:
                addBook();
                break;
            case 3:
                running = false;
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    private void addBook() {
        String title = getStringInput("Enter title");
        String author = getStringInput("Enter author");
        Book book = Book.builder().title(title).author(author).build();
        try {
            bookService.addBook(book);
        } catch (Exception e) {
            System.err.println("Failed to add book: " + e.getMessage());
        }
    }

    private void listBooks() {
        List<Book> books;
        try {
            books = bookService.getBooks();
        }
        catch (Exception e) {
            System.err.println("Failed to list books: " + e.getMessage());
            return;
        }
        if(books.isEmpty()) {
            System.out.println("No books found");
            return;
        }
        for(int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i).getTitle() + " by " + books.get(i).getAuthor());
        }
        System.out.println(books.size() + 1 + ". Back");
        int choice = getIntInput(books.size() + 1);
        if(choice == books.size() + 1)
            return;
        Book book;
        try {
            book = books.get(choice - 1);
        } catch (Exception e) {
            System.err.println("Failed to get book: " + e.getMessage());
            return;
        }
        System.out.println("1. Edit");
        System.out.println("2. Delete");
        System.out.println("3. Back");
        switch (getIntInput(3)) {
            case 1:
                editBook(book);
                break;
            case 2:
                try {
                    bookService.deleteBook(book);
                } catch (Exception e) {
                    System.err.println("Failed to delete book: " + e.getMessage());
                }
                break;
            case 3:
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    private void editBook(Book book) {
        String title = getStringInput("Enter title (leave blank to keep old title) [" + book.getTitle() + "]");
        String author = getStringInput("Enter author (leave blank to keep old author) [" + book.getAuthor() + "]");
        if(!title.isBlank())
            book.setTitle(title);
        if(!author.isBlank())
            book.setAuthor(author);
        try {
            bookService.updateBook(book);
        } catch (Exception e) {
            System.err.println("Failed to update book: " + e.getMessage());
        }
    }

    private int getIntInput(int max) {
        Scanner scanner = new Scanner(System.in);
        int input;
        while(true) {
            System.out.println("Enter choice");
            input = scanner.nextInt();
            if(input > 0 && input <= max)
                return input;
            else
                System.out.println("Invalid input");
        }
    }
    private String getStringInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(prompt);
        return scanner.nextLine();
    }
}

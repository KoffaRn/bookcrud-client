package org.koffa;

import org.koffa.helper.PasswordGetter;
import org.koffa.service.BookService;
import org.koffa.service.HttpBookService;

public class Main {
    public static void main(String[] args) {
        BookService bookService = new HttpBookService("username", PasswordGetter.getPassword(), "http://bookcrud-cicd-aws.duckdns.org");
        App app = new App(bookService);
        app.run();
    }
}
package org.koffa.service;

import com.google.gson.Gson;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.utils.Base64;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.koffa.helper.BookResponseHandler;
import org.koffa.helper.ListBookResponseHandler;
import org.koffa.model.Book;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class HttpBookService implements BookService {
    private final byte[] encodedAuth;
    private final String url;
    public HttpBookService(String username, String password, String url) {
        this.encodedAuth = Base64.encodeBase64((username + ":" + password).getBytes());
        this.url = url;
    }
    public List<Book> getBooks() {
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url + "/books");
            httpGet.setHeader("Authorization", "Basic " + new String(encodedAuth));
            return httpClient.execute(httpGet, new ListBookResponseHandler());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Book getBook(String id) {
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url + "/books/" + id);
            httpGet.setHeader("Authorization", "Basic " + new String(encodedAuth));
            return httpClient.execute(httpGet, new BookResponseHandler());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Book addBook(Book book) {
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            if(book.getId() != null)
                throw new IllegalArgumentException("Book ID must be null");
            HttpPost httpPost = new HttpPost(url + "/books");
            httpPost.setHeader("Authorization", "Basic " + new String(encodedAuth));
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(new StringEntity(new Gson().toJson(book), StandardCharsets.UTF_8));
            return httpClient.execute(httpPost, new BookResponseHandler());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Book updateBook(Book book) {
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            if(book.getId() == null)
                throw new IllegalArgumentException("Book ID must not be null");
            HttpPut httpPut = new HttpPut(url + "/books");
            httpPut.setHeader("Authorization", "Basic " + new String(encodedAuth));
            httpPut.setHeader("Content-type", "application/json");
            httpPut.setEntity(new StringEntity(new Gson().toJson(book), StandardCharsets.UTF_8));
            return httpClient.execute(httpPut, new BookResponseHandler());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public String deleteBook(Book book) {
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            if(book.getId() == null)
                throw new IllegalArgumentException("Book ID must not be null");
            HttpDelete httpDelete = new HttpDelete(url + "/books");
            httpDelete.setHeader("Authorization", "Basic " + new String(encodedAuth));
            httpDelete.setHeader("Content-type", "application/json");
            httpDelete.setEntity(new StringEntity(new Gson().toJson(book), StandardCharsets.UTF_8));
            return httpClient.execute(httpDelete, new BasicHttpClientResponseHandler());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
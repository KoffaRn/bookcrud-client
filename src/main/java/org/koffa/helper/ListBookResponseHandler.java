package org.koffa.helper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.koffa.model.Book;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ListBookResponseHandler implements HttpClientResponseHandler<List<Book>> {
    @Override
    public List<Book> handleResponse(ClassicHttpResponse classicHttpResponse) throws HttpException, IOException {
        if(classicHttpResponse.getCode() >= 200 && classicHttpResponse.getCode() < 300) {
            if(classicHttpResponse.getCode() == 204) return List.of();
            try(InputStream body = classicHttpResponse.getEntity().getContent()) {
                Gson gson = new Gson();
                return gson.fromJson(new String(body.readAllBytes()), new TypeToken<List<Book>>() {
                }.getType());
            } catch (Exception e) {
                throw new HttpException("Unexpected response status: " + classicHttpResponse.getCode());
            }
        } else {
            throw new HttpException("Unexpected response status: " + classicHttpResponse.getCode());
        }
    }
}

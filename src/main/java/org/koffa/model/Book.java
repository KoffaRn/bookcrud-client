package org.koffa.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Book {
    private String id;
    private String title;
    private String author;
}

package com.example.library.dto.response;

import com.example.library.dto.Book;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@SuppressWarnings("unchecked")
public class FindBooksResponse extends FindCollectionResponse<Book> implements Serializable {
}

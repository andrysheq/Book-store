package com.example.library.config.jpa;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;
    private String logic;

    public SearchCriteria() {}

    public SearchCriteria(final String key, final String operation, final Object value, final String logic) {
        super();
        this.key = key;
        this.operation = operation;
        this.value = value;
        this.logic = logic;
    }

    public boolean equals(String value) {
        return this.getOperation().equalsIgnoreCase(value);
    }
}


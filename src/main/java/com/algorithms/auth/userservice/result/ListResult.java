package com.algorithms.auth.userservice.result;

import java.util.List;

public class ListResult<T> extends Result{
    private List<T> items;
    private ListResult(List<T> items) {
        super(true);

        this.items = items;
    }

    private ListResult(String... errors) {
        super(errors);
    }

    public List<T> getItems() {
        return items;
    }
}

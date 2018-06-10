package com.algorithms.auth.userservice.result;

public class EntityResult<T> extends Result {
    private T entity;
    public EntityResult(T entity) {
        super(true);

        this.entity = entity;
    }

    private EntityResult(String... errors) {
        super(errors);
    }

    public T getEntity() {
        return entity;
    }
}

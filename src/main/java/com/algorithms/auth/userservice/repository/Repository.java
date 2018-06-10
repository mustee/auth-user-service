package com.algorithms.auth.userservice.repository;


import java.util.Optional;

public interface Repository<T> {

    Optional<T> get(long id);

    int update(T t);

    int delete(long id);
}

package com.algorithms.auth.userservice.repository.impl;

import com.algorithms.auth.userservice.repository.Repository;

import java.util.List;
import java.util.Optional;

public abstract class RepositoryBase<T> implements Repository<T> {


    protected Optional<T> singleResult(List<T> list){
        return Optional.ofNullable(list.stream().findFirst().orElse(null));
    }
}

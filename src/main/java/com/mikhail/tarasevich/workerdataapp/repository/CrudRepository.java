package com.mikhail.tarasevich.workerdataapp.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<E> {

    //create
    E save(E entity);

    //read
    Optional<E> findById(long id);
    List<E> findAll();
    Optional<E> findByName(String name);

    //update
    void update(E entity);

    //delete
    boolean deleteById(long param);

}

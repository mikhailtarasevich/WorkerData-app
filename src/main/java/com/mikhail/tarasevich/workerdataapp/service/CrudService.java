package com.mikhail.tarasevich.workerdataapp.service;

import java.util.List;

public interface CrudService<REQUEST, RESPONSE>{

    RESPONSE register(REQUEST request);

    RESPONSE findById(long id);
    List<RESPONSE> findAll();

    void edit(REQUEST request);

    boolean deleteById(long id);

}

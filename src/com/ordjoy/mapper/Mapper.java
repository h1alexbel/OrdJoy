package com.ordjoy.mapper;

public interface Mapper<F, T> {

    /**
     * Create < T > object from < F >
     * @param object < F > object
     * @return < T > object
     */
    T mapFrom(F object);
}
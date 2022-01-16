package com.ordjoy.mapper;

public interface Mapper<F, T> {

    T mapFrom(F object);
}
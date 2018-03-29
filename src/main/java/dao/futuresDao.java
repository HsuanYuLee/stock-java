package dao;

import domain.futures;

import java.util.ArrayList;

public interface futuresDao
{
    //增
    void add(futures f);

    //删
    void delete(String Date);

    //改
    void update(futures f);

    //查
    ArrayList<futures> findByDate(String StartDate, String EndDate);

    //查找所有
    ArrayList<futures> findAll();
}

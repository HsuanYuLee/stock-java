package dao;

import domain.stock;

import java.util.ArrayList;

public interface stockDao
{
    //增
    void add(stock s);

    //删
    void delete(String Date);

    //改
    void update(stock s);

    //查
    ArrayList<stock> findByDate(String StartDate, String EndDate);

    //查找所有
    ArrayList<stock> findAll();
}

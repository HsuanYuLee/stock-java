package dao;

import domain.futures;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface futuresDao
{

    //增
    void add(futures f);

    //删
    void delete(String Date)throws SQLException;

    //改
    void update(futures f)throws SQLException;

    //查
    ArrayList<futures> findByDate(String StartDate, String EndDate);

    //查找所有
    ArrayList<futures> findAll()throws SQLException;
}

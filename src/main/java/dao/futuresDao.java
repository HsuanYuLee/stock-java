package dao;

import domain.futures;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface futuresDao
{

    //添加方法
    void add(futures f);

    //删除方法
    void delete(String Date)throws SQLException;

    //更新方法
    void update(futures f)throws SQLException;

    //查找方法
    HashMap<String, futures> findByDate(String Date);

    //查找所有
    List<futures> findAll()throws SQLException;
}

package dao;

import Manager.ConfigManager;
import db.MySQLconnect;
import domain.futures;
import domain.stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class stockDaoImpl implements stockDao
{
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public void add(stock s)
    {
        try
        {
            conn = MySQLconnect.instance().getStockConnection();
            String SQL = "INSERT ignore into stock.tpe2330 values(?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(SQL);
            ps.setDate(1,s.getDate());
            ps.setString(2,s.getTrading_shares());
            ps.setString(3,s.getTurnover());
            ps.setString(4,s.getOpening_price());
            ps.setString(5,s.getHighest_price());
            ps.setString(6,s.getLowest_price());
            ps.setString(7,s.getClosing_price());
            ps.setString(8,s.getGross_Spread());
            ps.setString(9,s.getNumber_of_transactions());
            ps.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("資料新增失敗");
        }
        finally
        {
            MySQLconnect.instance().close(null,ps,conn);
        }

    }

    @Override
    public void delete(String Date) { }

    @Override
    public void update(stock s) { }

    @Override
    public ArrayList<stock> findByDate(String StartDate, String EndDate)
    {
        return null;
    }

    @Override
    public ArrayList<stock> findAll()
    {
        return null;
    }
}

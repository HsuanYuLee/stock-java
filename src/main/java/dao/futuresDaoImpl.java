package dao;

import db.MySQLconnect;
import domain.futures;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class futuresDaoImpl implements futuresDao
{
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;


    HashMap<String,futures> flist= new HashMap<>();
    //ArrayList<futures> flist = new ArrayList<>();

    @Override
    public void add(futures f)
    {
        try
        {
            conn = MySQLconnect.instance().getFuturesConnection();
            String SQL = "INSERT ignore into mtx1 values(?,?,?,?,?,?)";
            ps = conn.prepareStatement(SQL);
            ps.setDate(1,f.getDate());
            ps.setString(2,f.getOpening_price());
            ps.setString(3,f.getHighest_price());
            ps.setString(4,f.getLowest_price());
            ps.setString(5,f.getClosing_price());
            ps.setString(6,f.getNumber_of_transactions());
            ps.executeUpdate();
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
    public void delete(String Date) throws SQLException {

    }

    @Override
    public void update(futures f) throws SQLException {

    }

    @Override
    public HashMap<String, futures> findByDate(String Date)
    {
        try
        {
            conn = MySQLconnect.instance().getFuturesConnection();
            String SQL = "SELECT * from mtx1 where date >";
            ps = conn.prepareStatement(SQL + Date);
            rs = ps.executeQuery();
            int i = 0;
            while (rs.next())
            {
                futures fdata = new futures();
                fdata.setDate(String.valueOf(rs.getDate(1)));
                fdata.setOpening_price(rs.getString(2));
                fdata.setHighest_price(rs.getString(3));
                fdata.setLowest_price(rs.getString(4));
                fdata.setClosing_price(rs.getString(5));
                fdata.setNumber_of_transactions(rs.getString(6));

                flist.put(""+i+"",fdata);
                i++;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("資料查找失敗");
        }
        finally
        {
            MySQLconnect.instance().close(rs,ps,conn);
        }
        return flist;
    }

    @Override
    public List<futures> findAll() throws SQLException {
        return null;
    }

}

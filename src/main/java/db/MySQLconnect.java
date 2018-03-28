package db;


import Manager.ConfigManager;

import java.sql.*;

public class MySQLconnect
{
    private static MySQLconnect connect;

    public String stockURL,futuresURL,USERNAME,PASSWORD;

    public static MySQLconnect instance()
    {
        if (connect == null)
        {
            connect = new MySQLconnect();
        }
        return connect;
    }

    private MySQLconnect()
    {
        stockURL = ConfigManager.instance().dbstockurl;
        futuresURL = ConfigManager.instance().dbfuturesurl;
        USERNAME = ConfigManager.instance().dbusername;
        PASSWORD = ConfigManager.instance().dbpassword;
    }

    public Connection getStockConnection()
    {
        Connection conn = null;
        try
        {
            conn = DriverManager.getConnection(stockURL,USERNAME,PASSWORD);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return conn;
    }

    public Connection getFuturesConnection()
    {
        Connection conn = null;
        try
        {
            conn = DriverManager.getConnection(futuresURL,USERNAME,PASSWORD);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return conn;
    }

    public void close(ResultSet rs, Statement st, Connection conn)
    {
        try
        {
            if(rs!=null){rs.close();}
            if(st!=null){st.close();}
            if(conn!=null){conn.close();}
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}

package servlet.getdata;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.sql.*;

public class getdata
{
    private URL stockurl,futuresurl;
    private Connection conn;
    private Statement statement;

    public void setStockUrl(URL url)    //存入股票資料連結
    {
        stockurl = url;
    }
    public void setFuturesurl(URL url)
    {
        futuresurl = url;
        System.out.println("存入期貨資料連結");
    }
    public void connectMySQL()  //連接MySQLToJava
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void inputstock() throws IOException, SQLException   ////存入股市資料
    {
        Document data = Jsoup.parse(stockurl, 5000);

        try
        {
            //建立表格
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/stock-history", "root", "12345");
            statement = conn.createStatement();
            statement.executeUpdate
                    (
                    "CREATE TABLE if not exists Stock_2330" +
                            "(Date CHAR(20) not null," +
                            "Trading_shares TEXT," +
                            "Turnover TEXT," +
                            "Opening_price TEXT," +
                            "Highest_price TEXT," +
                            "Lowest_price TEXT," +
                            "Closing_price TEXT," +
                            "Gross_Spread TEXT," +
                            "Number_of_transactions TEXT," +
                            "primary key(Date));"
                    );

            //存入資料
            Elements trs = data.select("tr");
            for(int i=2;i<trs.size();i++)
            {
                Elements tds = trs.get(i).select("td");
                String[] TD = new String[tds.size()];


                for(int j=0;j<tds.size();j++)
                {
                    String Td = tds.get(j).text();
                    TD[j] = Td;
                }

                statement.executeUpdate
                        ("insert ignore into Stock_2330 values" +
                                "('"+TD[0]+"','" +TD[1]+"','" +TD[2]+"','" +TD[3]+"','" +TD[4]+"','"
                                +TD[5]+"','" +TD[6]+"','" +TD[7]+"','" +TD[8]+"')");
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        statement.close();
        conn.close();
    }


    /*


    public void inputfutures() throws IOException, SQLException
    {
        Document data = Jsoup.parse(futuresurl, 5000);

        //建立表格
        try
        {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/futures-history", "root", "12345");
            statement = conn.createStatement();
            statement.executeUpdate
                    (
                            "CREATE TABLE if not exists Futures_MTX" +
                                    "(Date CHAR(20) not null," +
                                    "Trading_shares TEXT," +
                                    "Turnover TEXT," +
                                    "Opening_price TEXT," +
                                    "Highest_price TEXT," +
                                    "Lowest_price TEXT," +
                                    "Closing_price TEXT," +
                                    "Gross_Spread TEXT," +
                                    "Number_of_transactions TEXT," +
                                    "primary key(Date));"
                    );
            System.out.println("表格建立成功");

            Elements trs = data.select("tr");
            for(int i=2;i<trs.size();i++)
            {
                Elements tds = trs.get(i).select("td");
                String[] TD = new String[tds.size()];


                for(int j=0;j<tds.size();j++)
                {
                    String Td = tds.get(j).text();
                    TD[j] = Td;
                }

                statement.executeUpdate
                        ("insert ignore into Stock_2330 values" +
                                "('"+TD[0]+"','" +TD[1]+"','" +TD[2]+"','" +TD[3]+"','" +TD[4]+"','"
                                +TD[5]+"','" +TD[6]+"','" +TD[7]+"','" +TD[8]+"')");
            }
            System.out.println("資料存入成功");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        statement.close();
        conn.close();
    }
    */


}


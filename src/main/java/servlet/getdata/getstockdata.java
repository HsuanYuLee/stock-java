package servlet.getdata;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.sql.*;

public class getstockdata
{
    private static URL stockurl;

    public static void setStockUrl(URL url)    //存入股票資料連結
    {
        stockurl = url;
    }
    public static void inputstock()  ////存入股市資料
    {
        try
        {
            Document data = Jsoup.parse(stockurl, 5000);

            //建立表格
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/stock-history", "root", "12345");
            Statement statement = conn.createStatement();
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
            statement.close();
            conn.close();
        }
        catch (SQLException | IOException e)
        {
            e.printStackTrace();
        }
    }
}


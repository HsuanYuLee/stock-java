package getdata;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class runstockdata extends Thread
{
    private int startYear;
    private String Stocklink;

    /*
    取得現在時間
    */
    private Calendar rightNow = Calendar.getInstance();
    private int NowYear = rightNow.get(Calendar.YEAR);
    private int NowMonth = rightNow.get(Calendar.MONTH) + 1;

    /*
    設定開始時間
    */
    public void setStartYear(int startYear)
    {
        this.startYear = startYear;
    }
    /*
    設定股票資料源
     */
    public void setStocklink(String Stocklink)
    {
        this.Stocklink = Stocklink;
    }

    @Override
    public void run()
    {
        super.run();
        try
        {
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/stock-history", "root", "12345");
            Statement statement = conn.createStatement();
            getstockdata.setStatement(statement);
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
            for(;startYear<NowYear;startYear++)
            {
                for(int j=1;j<=12;j++)
                {
                    //初始化資料來源
                    String year = String.valueOf(startYear);
                    String month = String.format("%02d",j);
                    String URL = Stocklink.replace("******",year+month);

                    getstockdata.setStockUrl(new URL(URL));
                    //存入資料
                    getstockdata.inputstock();
                    System.out.println("存入"+year+month+"月資料");
                    //延遲寫入
                    sleep((int)(Math.random()*2000+3000));
                }
            }
            for(int j=1;j<=NowMonth;j++)
            {
                //初始化資料來源
                String year = String.valueOf(NowYear);
                String month = String.format("%02d",j);
                String URL = Stocklink.replace("******",year+month);

                getstockdata.setStockUrl(new URL(URL));
                //存入資料
                getstockdata.inputstock();
                System.out.println("存入"+year+month+"月資料");
                //延遲寫入
                sleep((int)(Math.random()*2000+3000));
            }
            statement.close();
            conn.close();
        }
        catch (SQLException | MalformedURLException | InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}

class getstockdata
{
    private static URL stockurl;
    private static Statement statement;

    public static void setStockUrl(URL stockurl)    //存入股票資料連結
    {
        getstockdata.stockurl = stockurl;
    }
    public static void setStatement(Statement statement)
    {
        getstockdata.statement = statement;
    }
    public static void inputstock()  ////存入股市資料
    {
        try
        {
            Document data = Jsoup.parse(stockurl, 5000);
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
        catch (SQLException | IOException e)
        {
            e.printStackTrace();
        }
    }
}

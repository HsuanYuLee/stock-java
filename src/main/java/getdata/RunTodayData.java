package getdata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

public class RunTodayData extends TimerTask
{


    @Override
    public void run()
    {
        System.out.println("執行時間為：" + new Date() +"時間間隔一天");

        try
        {
            /*
            歷史資料擷取
            預設台積電
            目前設定股票資料來源為證交所，：http://www.twse.com.tw/exchangeReport/STOCK_DAY?response=html&date=20180201&stockNo=2330
            */
            String Stock_2330_Link = "http://www.twse.com.tw/exchangeReport/STOCK_DAY?response=html&date=********&stockNo=2330";
            RunStockData Get2330 = new RunStockData();
            Get2330.setStocklink(Stock_2330_Link);
            Get2330.setStockName("STOCK_2330");
            /*
            設定資料開始時間
            */
            Get2330.setTime(2017,12);
            /*
            設定要連接的MySQL伺服器
            */
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/stock-history", "root", "12345");
            Get2330.setMySQL(conn);
            Get2330.start();

            //---------------------------------------------------------------------------------------------------------------

            /*
            歷史資料擷取
            預設台指期
            台灣加權指數(台指現貨)資料來源為證交所：http://www.twse.com.tw/indicesReport/MI_5MINS_HIST?response=html&date=20180301
            台指期(小台指)近月資料來源為鉅亨網：http://www.cnyes.com/futures/History.aspx?mydate=20180311&code=MTX1
            */
            String MTX1Link = "http://www.cnyes.com/futures/History.aspx?mydate=********&code=MTX1";
            RunFuturesData GetMTX1 = new RunFuturesData();
            GetMTX1.setFutureslink(MTX1Link);
            /*
            設定資料開始時間
            */
            GetMTX1.setTime(2018,3, 5);
            /*
            設定要連接的MySQL伺服器
            */
            Connection conn2 = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/futures-history", "root", "12345");
            GetMTX1.setMySQL(conn2);
            GetMTX1.start();

        }
        catch(Exception e) { e.printStackTrace(); }
    }
}

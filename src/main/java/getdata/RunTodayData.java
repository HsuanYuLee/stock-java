package getdata;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;
import java.util.Properties;
import java.util.TimerTask;

public class RunTodayData extends TimerTask
{

    @Override
    public void run()
    {
        System.out.println("執行時間為：" + new Date() +"時間間隔一天");

        try
        {
            Properties properties = new Properties();
            FileInputStream FIS = new FileInputStream("C:/Users/USER/Documents/stock-java/src/config");
            properties.load(FIS);

            /*
            歷史資料擷取
            預設台積電
            目前設定股票資料來源為證交所，：http://www.twse.com.tw/exchangeReport/STOCK_DAY?response=html&date=20180201&stockNo=2330
            */
            RunStockData Get2330 = new RunStockData();
            Get2330.setStocklink(properties.getProperty("StockLink"));
            Get2330.setStockName(properties.getProperty("StockID"));
            /*
            設定資料開始時間
            */
            Get2330.setTime
                    (Integer.valueOf(properties.getProperty("Stock_Start_Year")),
                            Integer.valueOf(properties.getProperty("Stock_Start_Month")));
            /*
            設定要連接的MySQL伺服器
            */
            Connection conn = DriverManager.getConnection
                    (properties.getProperty("Stock_MySQL_URI"),
                            properties.getProperty("MySQL_ID"),
                            properties.getProperty("MySQL_PW"));
            Get2330.setMySQL(conn);
            Get2330.start();

            //---------------------------------------------------------------------------------------------------------------

            /*
            歷史資料擷取
            預設台指期
            台灣加權指數(台指現貨)資料來源為證交所：http://www.twse.com.tw/indicesReport/MI_5MINS_HIST?response=html&date=20180301
            台指期(小台指)近月資料來源為鉅亨網：http://www.cnyes.com/futures/History.aspx?mydate=20180311&code=MTX1
            */
            RunFuturesData GetMTX1 = new RunFuturesData();
            GetMTX1.setFutureslink(properties.getProperty("FuturesLink"));
            /*
            設定資料開始時間
            */
            GetMTX1.setTime
                    (Integer.valueOf(properties.getProperty("Futures_Start_Year")),
                            Integer.valueOf(properties.getProperty("Futures_Start_Month")),
                            Integer.valueOf(properties.getProperty("Futures_Start_Day")));
            /*
            設定要連接的MySQL伺服器
            */
            Connection conn2 = DriverManager.getConnection
                    (properties.getProperty("Futures_MySQL_URI"),
                            properties.getProperty("MySQL_ID"),
                            properties.getProperty("MySQL_PW"));
            GetMTX1.setMySQL(conn2);
            GetMTX1.start();

        }
        catch(Exception e) { e.printStackTrace(); }
    }
}

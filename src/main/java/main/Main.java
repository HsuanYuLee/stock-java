package main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import servlet.HelloServlet;
import getdata.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;

public class Main
{

    public static void main(String[] args)
    {

        try
        {
            /*
            歷史資料擷取
            預設台積電
            目前設定股票資料來源為證交所，：http://www.twse.com.tw/exchangeReport/STOCK_DAY?response=html&date=20180201&stockNo=2330
            */
            String Stock_2330_Link = "http://www.twse.com.tw/exchangeReport/STOCK_DAY?response=html&date=********&stockNo=2330";
            RunStockData Get2330 = new RunStockData();
            /*
            設定資料開始時間
            */
            Calendar startDate = Calendar.getInstance();
            startDate.set(2017, Calendar.JANUARY,1);
            Get2330.setStartDate(startDate);
            Calendar NextMonthDate = Calendar.getInstance();
            NextMonthDate.set(2017, Calendar.FEBRUARY,1);
            Get2330.setNextMonthDate(NextMonthDate);
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/stock-history", "root", "12345");
            Get2330.setMySQL(conn);
            Get2330.setStocklink(Stock_2330_Link);
            Get2330.setStockName("STOCK_2330");
            Get2330.start();
        }
        catch(Exception e) { e.printStackTrace(); }

        //---------------------------------------------------------------------------------------------------------------

        /*
        歷史資料擷取
        預設台指期
        台灣加權指數(台指現貨)資料來源為證交所：http://www.twse.com.tw/indicesReport/MI_5MINS_HIST?response=html&date=20180301
        台指期(小台指)近月資料來源為鉅亨網：http://www.cnyes.com/futures/History.aspx?mydate=20180311&code=MTX1
        */
        String Futuresdatalink1 = "http://www.twse.com.tw/indicesReport/MI_5MINS_HIST?response=html&date=********";
        String MTX1Link = "http://www.cnyes.com/futures/History.aspx?mydate=********&code=MTX1";

        RunFuturesData GetMTX1 = new RunFuturesData();
        /*
        設定資料開始時間
        */
        Calendar setDate2 = Calendar.getInstance();
        setDate2.set(2018, Calendar.JANUARY,1);
        GetMTX1.setStartDate(setDate2);

        GetMTX1.setFutureslink(MTX1Link);
        /*
        設定要連接的MySQL伺服器
        */
        try
        {
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/futures-history", "root", "12345");
            GetMTX1.setMySQL(conn);
        }
        catch(SQLException e) { e.printStackTrace(); }
        /*
        開始接收
        */
        //GetMTX1.start();

        //---------------------------------------------------------------------------------------------------------------

        /*
        當日資料擷取
         */
        //Timer timer = new Timer();
        //建立TimerTask物件
        RunTodayData runtodaydata = new RunTodayData();
        //設定開始執行時間，設定每日執行時間為下午兩點
        Calendar start = Calendar.getInstance(Locale.CHINESE);
        start.getTime();
        start.set(Calendar.HOUR_OF_DAY,14);
        start.set(Calendar.MINUTE,0);
        start.set(Calendar.SECOND,0);
        Date startdate = start.getTime();
        //System.out.println("排程將在" + startdate + "開始執行");

        //將資料連結寫入物件
        //runtodaydata.setStocklink(Stock_2330_Link);
        //runtodaydata.setFutureslink(MTX1Link);
        //timer.schedule(runtodaydata,startdate, 86400000);

        /*
        建立伺服器
        Server server = new Server(8080);
        //建立Server內可調用的handler
        //並將handler裝進server內
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);

        //將Servlet裝進Handler內
        handler.addServletWithMapping(HelloServlet.class, "/*");
        //啟動伺服器
        server.start();
        //等待連線
        server.join()
         */
    }

}
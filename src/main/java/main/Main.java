package main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import servlet.HelloServlet;
import getdata.*;

import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class Main
{

    public static void main(String[] args)
    {
        /*
        歷史資料擷取
        預設台積電
        目前設定股票資料來源為證交所，：http://www.twse.com.tw/exchangeReport/STOCK_DAY?response=html&date=20180201&stockNo=2330
        */
        String Stockdatalink = "http://www.twse.com.tw/exchangeReport/STOCK_DAY?response=html&date=********&stockNo=2330";

        runstockhistorydata runstockdata = new runstockhistorydata();
        /*
        設定資料開始時間
         */
        Calendar setDate1 = Calendar.getInstance();
        setDate1.set(2017, Calendar.NOVEMBER,1);
        runstockdata.setStartDate(setDate1);
        runstockdata.setStocklink(Stockdatalink);

        /*
        歷史資料擷取
        預設台指期
        台灣加權指數(台指現貨)資料來源為證交所：http://www.twse.com.tw/indicesReport/MI_5MINS_HIST?response=html&date=20180301
        台指期(小台指)近月資料來源為鉅亨網：http://www.cnyes.com/futures/History.aspx?mydate=20180311&code=MTX1
         */
        String Futuresdatalink1 = "http://www.twse.com.tw/indicesReport/MI_5MINS_HIST?response=html&date=********";
        String Futuresdatalink2 = "http://www.cnyes.com/futures/History.aspx?mydate=********&code=MTX1";

        runfutureshistorydata runfuturesdata = new runfutureshistorydata();
        /*
        設定資料開始時間
         */
        Calendar setDate2 = Calendar.getInstance();

        setDate2.set(2018, Calendar.JANUARY,1);
        runfuturesdata.setStartDate(setDate2);

        runfuturesdata.setFutureslink1(Futuresdatalink1);
        runfuturesdata.setFutureslink2(Futuresdatalink2);
        /*
        開始接收
        */
        //runstockdata.start();
        runfuturesdata.start();


        /*
        當日資料擷取
        */

        //Timer timer = new Timer();
        //建立TimerTask物件
        runtodaydata runtodaydata = new runtodaydata();
        //將資料連結寫入物件
        runtodaydata.setStocklink(Stockdatalink);
        runtodaydata.setFutureslink1(Futuresdatalink1);
        runtodaydata.setFutureslink2(Futuresdatalink2);

        //timer.schedule(new runtodaydata(),new Date(), 10000);

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
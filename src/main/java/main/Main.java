package main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import servlet.HelloServlet;
import getdata.*;

public class Main
{
    public static void main(String[] args) throws Exception
    {

        //目前設定股票資料來源為證交所，預設台積電
        String Stockdatalink = "http://www.twse.com.tw/exchangeReport/STOCK_DAY?response=html&date=20180201&stockNo=2330";
        String StockURL = Stockdatalink.replace("201802","******");

        runstockdata runstockdata = new runstockdata();
        runstockdata.setStartYear(2017);
        runstockdata.setStocklink(StockURL);

        /*
        預設台指期
        台灣加權指數(台指現貨)資料來源為證交所：http://www.twse.com.tw/indicesReport/MI_5MINS_HIST?response=html&date=20180301
        台指期(小台指)近月資料來源為鉅亨網：http://www.cnyes.com/futures/History.aspx?mydate=20180311&code=MTX1
         */
        String Futuresdatalink1 = "http://www.twse.com.tw/indicesReport/MI_5MINS_HIST?response=html&date=20180301";
        String Futuresdatalink2 = "http://www.cnyes.com/futures/History.aspx?mydate=20180311&code=MTX1";
        String FuturesURL1 = Futuresdatalink1.replace("20180301","********");
        String FuturesURL2 = Futuresdatalink2.replace("20180311","********");

        runfuturesdata runfuturesdata = new runfuturesdata();
        runfuturesdata.setStartYear(2017);
        runfuturesdata.setFutureslink1(FuturesURL1);
        runfuturesdata.setFutureslink2(FuturesURL2);

        /*
        開始接收
         */
        runstockdata.start();
        runfuturesdata.start();


        /*
        建立伺服器
        */

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
        server.join();


    }

}
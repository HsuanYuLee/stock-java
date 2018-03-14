package org.test;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import servlet.HelloServlet;
import servlet.getdata.getdata;

import java.io.FileWriter;
import java.net.URL;

public class Main
{
    static String day;

    public static void main(String[] args) throws Exception
    {
        /*
        取得資料
         */

        //建立取得資料物件
        getdata getdata = new getdata();
        //連到MySQL
        getdata.connectMySQL();

        for(int i=2018;i<=2018;i++)
        {
            for(int j=1;j<=12;j++)
            {
                //初始化資料來源
                //目前設定股市資料來源為台灣證券交易所，預設台積電
                String year = String.valueOf(i);
                String month = String.format("%02d",j);
                String URL = "http://www.twse.com.tw/exchangeReport/STOCK_DAY?response=html&date="+year+month+"01&stockNo=2330";

                getdata.setStockUrl(new URL(URL));

                //存入資料
                getdata.inputstock();
                System.out.println("存入"+year+month+"月資料");
            }
        }



        //目前設定股市資料來源為鉅亨網，預設小台指
        //getdata.setFuturesurl(new URL("http://www.cnyes.com/futures/History.aspx?mydate=20180314&code=MTX1"));



        /*
        建立伺服器

        Server server = new Server(8080);
        //建立Server內可調用的handler
        //並將handler裝進server內
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);

        //將Servlet裝進Handler內
        handler.addServletWithMapping(HelloServlet.class, "/*");
        //handler.addServletWithMapping(getdata.class, "/*");

        //啟動伺服器
        server.start();
        //
        server.join();
        */

    }
}
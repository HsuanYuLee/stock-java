package org.test;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import servlet.HelloServlet;
import servlet.getdata.getdata;

import java.io.FileWriter;
import java.net.URL;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        /*
        取得資料
         */

        //建立取得資料物件
        getdata getdata = new getdata();
        //初始化資料來源
        getdata.setUrl(new URL("http://www.twse.com.tw/exchangeReport/STOCK_DAY?response=html&date=20180201&stockNo=2330"));
        //初始化資料存取路徑
        getdata.setFileWriter(new FileWriter("/Users/USER/Desktop/data.txt",false));
        //連到MySQL
        getdata.connectMySQL();
        //下載資料
        getdata.download();

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
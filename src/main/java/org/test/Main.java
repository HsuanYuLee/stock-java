package org.test;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import servlet.HelloServlet;
import servlet.getdata.runstockdata;

public class Main
{
    public static void main(String[] args) throws Exception {
        runstockdata runstockdata = new runstockdata();
        runstockdata.setStartYear(2017);
        runstockdata.start();


        //目前設定股市資料來源為鉅亨網，預設小台指
        //getdata.setFuturesurl(new URL("http://www.cnyes.com/futures/History.aspx?mydate=20180314&code=MTX1"));

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
        //handler.addServletWithMapping(getdata.class, "/*");

        //啟動伺服器
        server.start();
        //
        server.join();



    }

}
package main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import servlet.HelloServlet;
import getdata.*;

import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            //String Futuresdatalink1 = "http://www.twse.com.tw/indicesReport/MI_5MINS_HIST?response=html&date=********";

            //每日資料擷取
            Timer timer = new Timer();
            //建立TimerTask物件
            RunTodayData runTodayData = new RunTodayData();
            //設定開始執行時間，設定每日執行時間為下午兩點
            Calendar start = Calendar.getInstance(Locale.CHINESE);
            start.getTime();
            start.set(Calendar.HOUR_OF_DAY,1);
            start.set(Calendar.MINUTE,12);
            start.set(Calendar.SECOND,0);

            System.out.println("排程將在" + start.getTime() + "開始執行");

            timer.schedule(runTodayData,start.getTime(), 86400000);

            /*
            //建立伺服器
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
            */

        }catch (Exception e){ e.printStackTrace(); }
    }
}
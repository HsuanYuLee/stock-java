
import Manager.ConfigManager;
import Service.GetFuturesData;
import Service.GetFuturesDataToday;
import controller.HelloServlet;
import controller.ShowFuturesData;
import controller.ShowFuturesDataAll;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class Main
{
    public static void main(String[] args)
    {
        String t = ConfigManager.instance().Timer_Start_time;
        String p = ConfigManager.instance().Timer_Period_time;

        try
        {
            /*
            程式開始時自動執行歷史資料下載
            */
            GetFuturesData getFuturesData = new GetFuturesData();
            getFuturesData.start();

            /*
            排成下載每日資料
            */
            Timer timer = new Timer();

            int StartHour = Integer.valueOf(t.substring(0,2));
            int StartMinute = Integer.valueOf(t.substring(3,5));
            int StartSecond = Integer.valueOf(t.substring(6,8));

            int period = Integer.valueOf(p);

            Calendar Start = Calendar.getInstance();
            Start.set(Calendar.HOUR_OF_DAY,StartHour);
            Start.set(Calendar.MINUTE,StartMinute);
            Start.set(Calendar.SECOND,StartSecond);
            Date StartTime = Start.getTime();

            GetFuturesDataToday getFuturesDataToday = new GetFuturesDataToday();
            timer.schedule(getFuturesDataToday,StartTime,period);
            System.out.println("預計"+StartTime+"開始執行，每"+ period +"毫秒執行一次");



            /*
            伺服器
            */
            //建立伺服器
            Server server = new Server(8080);
            //建立Server內可調用的handler
            //並將handler裝進server內
            ServletHandler handler = new ServletHandler();
            server.setHandler(handler);

            //將Servlet裝進Handler內
            handler.addServletWithMapping(HelloServlet.class,"/hello");
            handler.addServletWithMapping(ShowFuturesData.class, "/ShowFuturesData");
            handler.addServletWithMapping(ShowFuturesDataAll.class, "/ShowFuturesDataAll");

            //啟動伺服器
            server.start();
            //等待連線
            server.join();

        }catch (Exception e){ e.printStackTrace(); }
    }
}

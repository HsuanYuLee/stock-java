import Manager.ConfigManager;
import Service.GetFuturesData;
import Service.GetFuturesDataToday;
import Service.GetStocKData;
import controller.HelloServlet;
import controller.ShowFuturesData;
import controller.ShowFuturesDataAll;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class test
{
    public static void main(String[] args)
    {
        GetStocKData getStocKData = new GetStocKData();

        getStocKData.start();



    }
}

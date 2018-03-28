import Service.getFuturesHistory;
import controller.HelloServlet;
import controller.SearchFuturesData;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class test
{
    public static void main(String[] args)
    {
        try
        {
            getFuturesHistory getFuturesHistory = new getFuturesHistory();
            getFuturesHistory.start();


            //建立伺服器
            Server server = new Server(8080);
            //建立Server內可調用的handler
            //並將handler裝進server內
            ServletHandler handler = new ServletHandler();
            server.setHandler(handler);

            //將Servlet裝進Handler內
            handler.addServletWithMapping(SearchFuturesData.class, "/findFutureData");
            handler.addServletWithMapping(HelloServlet.class,"/hello");
            //啟動伺服器
            server.start();
            //等待連線
            server.join();


        }catch (Exception e){ e.printStackTrace(); }



    }
}

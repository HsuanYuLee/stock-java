package getdata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

public class RunTodayData extends TimerTask
{
    private String Stocklink,Futureslink;


    //設定股票資料連結
    public void setStocklink(String Stocklink) { this.Stocklink = Stocklink; }

    //設定期貨資料連結
    public void setFutureslink(String futureslink) { this.Futureslink = futureslink; }

    @Override
    public void run()
    {
        System.out.println("執行時間為：" + new Date() +"時間間隔一天");

        /*
        RunStockData Get2330 = new RunStockData();
        /*
        設定資料開始時間

        Calendar setDate1 = Calendar.getInstance();
        setDate1.getTime();
        Get2330.setStartDate(setDate1);
        Get2330.setStocklink(Stocklink);
        Get2330.start();
        System.out.println("當日股票匯入時間為：" + new Date());
        */

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





























        /*
------------------------------------------------------------------------------------------------------------------------
         */

        RunFuturesData GetMTX1 = new RunFuturesData();
        /*
        設定資料開始時間
         */
        Calendar setDate2 = Calendar.getInstance();
        setDate2.getTime();
        GetMTX1.setStartDate(setDate2);
        GetMTX1.setFutureslink(Futureslink);
        GetMTX1.start();
        System.out.println("當日期貨匯入時間為：" + new Date());


    }
}

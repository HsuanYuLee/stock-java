package servlet.getdata;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

public class runstockdata extends Thread
{
    private int startYear;

    /*
    取得現在時間
    */
    private Calendar rightNow = Calendar.getInstance();
    private int NowYear = rightNow.get(Calendar.YEAR);
    private int NowMonth = rightNow.get(Calendar.MONTH) + 1;

    /*
    設定開始時間
    */
    public void setStartYear(int startYear)
    {
        this.startYear = startYear;
    }

    @Override
    public void run()
    {
        super.run();

        for(;startYear<NowYear;startYear++)
        {
            for(int j=1;j<=12;j++)
            {
                try
                {
                    //初始化資料來源
                    //目前設定股市資料來源為台灣證券交易所，預設台積電
                    String year = String.valueOf(startYear);
                    String month = String.format("%02d",j);
                    String URL = "http://www.twse.com.tw/exchangeReport/STOCK_DAY?response=html&date="+year+month+"01&stockNo=2330";

                    getstockdata.setStockUrl(new URL(URL));
                    //存入資料
                    getstockdata.inputstock();

                    System.out.println("存入"+year+month+"月資料");

                    //延遲寫入
                    sleep(5000);
                }
                catch (InterruptedException | MalformedURLException e)
                {
                    e.printStackTrace();
                }
            }
        }

        for(int j=1;j<=NowMonth;j++)
        {
            try
            {
                //初始化資料來源
                //目前設定股市資料來源為台灣證券交易所，預設台積電
                String year = String.valueOf(NowYear);
                String month = String.format("%02d",j);
                String URL = "http://www.twse.com.tw/exchangeReport/STOCK_DAY?response=html&date="+year+month+"01&stockNo=2330";

                getstockdata.setStockUrl(new URL(URL));
                //存入資料
                getstockdata.inputstock();
                System.out.println("存入"+year+month+"月資料");

                //延遲寫入
                sleep(5000);
            }
            catch (InterruptedException | MalformedURLException e)
            {
                e.printStackTrace();
            }
        }
    }
}

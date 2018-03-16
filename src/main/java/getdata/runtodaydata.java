package getdata;

import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

public class runtodaydata extends TimerTask
{
    private String Stocklink,Futureslink1,Futureslink2;


    //設定股票資料連結
    public void setStocklink(String Stocklink) { this.Stocklink = Stocklink; }

    //設定期貨資料連結
    public void setFutureslink1(String futureslink) { this.Futureslink1 = futureslink; }
    public void setFutureslink2(String futureslink) { this.Futureslink2 = futureslink; }


    @Override
    public void run()
    {
        System.out.println("執行時間為：" + new Date());

    }
}

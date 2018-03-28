package Service;

import Manager.ConfigManager;
import dao.futuresDaoImpl;
import domain.futures;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class getFuturesHistory extends Thread
{
    private String FuturesLink = ConfigManager.instance().FuturesLink;
    private String Year = ConfigManager.instance().Futures_End_Year;
    private String Month = ConfigManager.instance().Futures_End_Month;
    private String Day = ConfigManager.instance().Futures_End_Day;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void run()
    {
        super.run();

        Calendar Date = Calendar.getInstance();
        String date = sdf.format(Date.getTime());
        futuresDaoImpl fDao = new futuresDaoImpl();
        futures fs = new futures();

        while (!date.equals(Year +"-"+ Month +"-"+ Day))
        {
            try
            {
                String date2 = date.replaceAll("-","");
                String Link = FuturesLink.replace("********",date2);

                Document data = Jsoup.connect(Link).timeout(30000).validateTLSCertificates(false).get();
                sleep((int)(Math.random()*2000+3000));  //暫停，避免斷線

                Element tr = data.select("tr").get(6);
                Elements tds = tr.select("td");
                String[] fdata = new String[6];
                fdata[0] = date;

                switch (tds.size())
                {
                    case 1:
                        System.out.println(Arrays.toString(fdata) + " 無資料，跳過");
                        break;
                    case 5:
                        for (int i=1;i<=tds.size();i++)
                        {
                            fdata[i] = tds.get(i-1).text();
                        }
                        fs.setDate(fdata[0]);
                        fs.setOpening_price(fdata[1]);
                        fs.setHighest_price(fdata[2]);
                        fs.setLowest_price(fdata[3]);
                        fs.setClosing_price(fdata[4]);
                        fs.setNumber_of_transactions(fdata[5]);
                        System.out.println(Arrays.toString(fdata));
                        fDao.add(fs);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            Date.add(Calendar.DATE,-1);
            date = sdf.format(Date.getTime());
        }


    }
}

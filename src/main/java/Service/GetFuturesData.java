package Service;

import Manager.ConfigManager;
import dao.futuresDaoImpl;
import domain.futures;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class GetFuturesData extends Thread
{
    private String FuturesLink = ConfigManager.instance().FuturesLink;
    private String FinalDate = ConfigManager.instance().Futures_Data_Final_Date;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private futuresDaoImpl fDao = new futuresDaoImpl();

    @Override
    public void run()
    {
        super.run();

        ArrayList<futures> fl = fDao.findAll();
        futures EndData = fl.get(0);
        Date EndDate = EndData.getDate();

        Calendar DateStart = Calendar.getInstance();
        String dateStart = sdf.format(DateStart.getTime());
        Date Now = Date.valueOf(dateStart);

        while (!Now.equals(EndDate))
        {
            DateStart.add(Calendar.DATE,-1);
            dateStart = sdf.format(DateStart.getTime());
            Now = Date.valueOf(dateStart);
        }
        System.out.println(dateStart+"日前的資料已有，從"+dateStart+"日繼續下載");


        futures fs = new futures();
        String Final = FinalDate.replaceAll("'","");

        while (!dateStart.equals(Final))
        {
            try
            {
                String date2 = dateStart.replaceAll("-","");
                String Link = FuturesLink.replace("********",date2);

                Document data = Jsoup.connect(Link).timeout(30000).validateTLSCertificates(false).get();
                sleep((int)(Math.random()*2000+3000));  //暫停，避免斷線

                Element tr = data.select("tr").get(6);
                Elements tds = tr.select("td");
                String[] fdata = new String[6];
                fdata[0] = dateStart;

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

            DateStart.add(Calendar.DATE,-1);
            dateStart = sdf.format(DateStart.getTime());
        }
    }
}

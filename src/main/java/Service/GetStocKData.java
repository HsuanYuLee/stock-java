package Service;

import Manager.ConfigManager;
import dao.stockDaoImpl;
import domain.stock;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class GetStocKData extends Thread
{
    private String StockLink = ConfigManager.instance().StockLink;
    private String FinalDate = ConfigManager.instance().Stock_Data_Final_Date;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private stockDaoImpl sDao = new stockDaoImpl();

    @Override
    public void run()
    {
        super.run();

        Calendar DateStart = Calendar.getInstance();
        String dateStart = sdf.format(DateStart.getTime());
        String dateStartMom = dateStart.substring(0,7);
        Date Now = Date.valueOf(dateStart);

        /*
        while (!Now.equals(EndDate))
        {
            DateStart.add(Calendar.DATE,-1);
            dateStart = sdf.format(DateStart.getTime());
            Now = Date.valueOf(dateStart);
        }
        System.out.println(dateStart+"日前的資料已有，從"+dateStart+"日繼續下載");
        */

        stock st = new stock();
        String Final = FinalDate.replaceAll("'","");
        String FinalMom = Final.substring(0,7);

        while (!dateStartMom.equals(FinalMom))
        {
            try
            {
                String date = dateStart.replaceAll("-", "");
                String Link = StockLink.replace("********", date);

                Document data = Jsoup.connect(Link).timeout(30000).validateTLSCertificates(false).get();
                sleep((int) (Math.random() * 2000 + 3000));  //暫停，避免斷線

                String[] sdata = new String[9];
                Elements trs = data.select("tr");
                for (int i=2;i<trs.size();i++)
                {
                    Elements tds = trs.get(i).select("td");
                    String Firsttd = dateStartMom +
                            tds.get(0).text()
                                    .replaceAll("/","-")
                                    .substring(6,9);
                    sdata[0] = Firsttd;

                    for (int j=1; j<tds.size();j++)
                    {
                        String td = tds.get(j).text();

                        String Td = td.replaceAll("/","-");

                        sdata[j] = Td;
                    }

                    st.setDate(sdata[0]);
                    st.setTrading_shares(sdata[1]);
                    st.setTurnover(sdata[2]);
                    st.setOpening_price(sdata[3]);
                    st.setHighest_price(sdata[4]);
                    st.setLowest_price(sdata[5]);
                    st.setClosing_price(sdata[6]);
                    st.setGross_Spread(sdata[7]);
                    st.setNumber_of_transactions(sdata[8]);
                    sDao.add(st);
                    System.out.println(Arrays.toString(sdata));
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            DateStart.add(Calendar.MONTH, -1);
            dateStart = sdf.format(DateStart.getTime());
            dateStartMom = sdf.format(DateStart.getTime()).substring(0, 7);
        }
    }
}

package getdata;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class runfutureshistorydata extends Thread
{
    private Calendar startDate;
    private String Futureslink1,Futureslink2;

    public void setStartDate(Calendar startDate) { this.startDate = startDate; }

    /*
    設定期貨資料源
     */
    public void setFutureslink1(String futureslink) { this.Futureslink1 = futureslink; }
    public void setFutureslink2(String futureslink) { this.Futureslink2 = futureslink; }

    @Override
    public void run()
    {
        String URL1 = Futureslink1.replace("********","20180314");
        System.out.println(URL1);

        super.run();
        /*
        取得現在時間
        */
        SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
        Calendar NowDate = Calendar.getInstance();
        NowDate.getTime();

        try
        {
            String Start = SDF.format(startDate.getTime());
            String Now = SDF.format(NowDate.getTime());

            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/futures-history", "root", "12345");
            Statement statement = conn.createStatement();
            statement.executeUpdate
                    (
                            "CREATE TABLE if not exists futures_MTX1" +
                                    "(Date DATE not null," +
                                    "Opening_price TEXT," +
                                    "Highest_price TEXT," +
                                    "Lowest_price TEXT," +
                                    "Closing_price TEXT," +
                                    "Number_of_transactions TEXT," +
                                    "primary key(Date));"
                    );

            while (!Start.equals(Now))
            {
                String URL2 = Futureslink2.replace("********",Start.replaceAll("-",""));

                Document MTX1data = Jsoup.parse(new URL(URL2),5000);
                sleep((int)(Math.random()*2000+3000));  //暫停，避免斷線

                Element tr = MTX1data.select("tr").get(6);
                Elements tds = tr.select("td");

                String[] TD = new String[6];
                TD[0] = Start;

                switch (tds.size())
                {
                    case 1:
                        statement.executeUpdate
                                ("insert ignore into futures_MTX1 values" +
                                        "('"+TD[0]+"','" +TD[1]+"','" +TD[2]+"','" +TD[3]+"','" +TD[4]+"','"
                                        +TD[5]+"')");

                        String A = Arrays.toString(TD);
                        System.out.println(A);
                        break;
                    case 5:
                        for (int k = 0; k < tds.size(); k++)
                        {
                            String Td = tds.get(k).text();
                            TD[k+1] = Td;
                        }
                        statement.executeUpdate
                                ("insert ignore into futures_MTX1 values" +
                                        "('"+TD[0]+"','" +TD[1]+"','" +TD[2]+"','" +TD[3]+"','" +TD[4]+"','"
                                        +TD[5]+"')");

                        String B = Arrays.toString(TD);
                        System.out.println(B);
                        break;
                }
                startDate.add(Calendar.DATE,1);
                Start = SDF.format(startDate.getTime());
            }
            statement.close();
            conn.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}



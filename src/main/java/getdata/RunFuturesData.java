package getdata;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class RunFuturesData extends Thread
{
    private Calendar StartDate;
    private String Futureslink;
    private Connection conn;

    public void setStartDate(Calendar StartDate) { this.StartDate = StartDate; }
    /*
    設定期貨資料源
    */
    public void setFutureslink(String futureslink) { this.Futureslink = futureslink; }
    /*
    設定MySQL資料庫
    */
    public void setMySQL(Connection conn) { this.conn = conn; }

    @Override
    public void run()
    {
        super.run();
        /*
        取得現在時間
        */
        SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
        Calendar NowDate = Calendar.getInstance();
        NowDate.getTime();

        try
        {
            String Start = SDF.format(StartDate.getTime());

            NowDate.add(Calendar.DATE,1);
            String NextDay = String.valueOf
                    (
                            NowDate.get(Calendar.YEAR)
                                    + "-" + String.format("%02d",NowDate.get(Calendar.MONTH)+1)
                                    + "-" + String.format("%02d",NowDate.get(Calendar.DATE))
                    );

            String FuturesName = Futureslink.substring(63,67);

            Statement statement = conn.createStatement();
            statement.executeUpdate
                    (
                            "CREATE TABLE if not exists " + FuturesName +
                                    "(Date DATE not null," +
                                    "Opening_price TEXT," +
                                    "Highest_price TEXT," +
                                    "Lowest_price TEXT," +
                                    "Closing_price TEXT," +
                                    "Number_of_transactions TEXT," +
                                    "primary key(Date));"
                    );

            while (!Start.equals(NextDay))
            {
                String URL2 = Futureslink.replace("********",Start.replaceAll("-",""));

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
                                ("insert ignore into "+ FuturesName +" values" +
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
                                ("insert ignore into "+ FuturesName +" values" +
                                        "('"+TD[0]+"','" +TD[1]+"','" +TD[2]+"','" +TD[3]+"','" +TD[4]+"','"
                                        +TD[5]+"')");

                        String B = Arrays.toString(TD);
                        System.out.println(B);
                        break;
                }
                StartDate.add(Calendar.DATE,1);
                Start = SDF.format(StartDate.getTime());
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



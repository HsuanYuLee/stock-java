package getdata;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class RunStockData extends Thread
{
    private Calendar Start,Next;
    private String Stocklink,StockName;
    private Connection conn;


    public void setTime(int Year, int Month)
    {
        Calendar A = Calendar.getInstance();
        A.set(Calendar.YEAR,Year);
        A.set(Calendar.MONTH,Month-1);
        Start = A;

        Calendar B = Calendar.getInstance();
        B.set(Calendar.YEAR,Year);
        B.set(Calendar.MONTH,Month);
        Next = B;
    }
    /*
    設定股票資料源
    */
    public void setStocklink(String Stocklink) { this.Stocklink = Stocklink; }
    public void setStockName(String StockName) { this.StockName = StockName; }
    /*
    設定MySQL資料庫
    */
    public void setMySQL(Connection conn) { this.conn = conn; }

    @Override
    public void run()
    {
        /*
        取得現在時間
        */
        SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
        Calendar NowDate = Calendar.getInstance();

        super.run();
        try
        {
            String Sta = SDF.format(Start.getTime());
            String StartYear = String.valueOf(Start.get(Calendar.YEAR));

            String Nex = SDF.format(Next.getTime());

            String Now = SDF.format(NowDate.getTime());

            Statement statement = conn.createStatement();
            statement.executeUpdate
                    (
                            "CREATE TABLE if not exists "+ StockName +
                                    "(Date DATE not null," +
                                    "Trading_shares TEXT," +
                                    "Turnover TEXT," +
                                    "Opening_price TEXT," +
                                    "Highest_price TEXT," +
                                    "Lowest_price TEXT," +
                                    "Closing_price TEXT," +
                                    "Gross_Spread TEXT," +
                                    "Number_of_transactions TEXT," +
                                    "primary key(Date));"
                    );
            /*
            下載歷史資料
            */
            while (!Sta.equals(Now))
            {
                String URL = Stocklink.replace("********",Sta.replaceAll("-",""));

                ResultSet RS= statement.executeQuery
                       ("SELECT * FROM " + StockName +
                                " where date >'" +Sta.substring(0,8)+"01' " +
                                "AND date <'" +Nex.substring(0,8)+"01' ");
                int A = 0;
                while(RS.next()){ A++;}
                RS.close();

                if(A>0)
                {
                    System.out.println(Sta.substring(0,7) +" ~ "+Nex.substring(0,7)+" 已有資料，筆數為"+A+"，跳過");
                }
                else
                    {
                        Document Stock2330data = Jsoup.parse(new URL(URL),5000);
                        sleep((int)(Math.random()*2000+3000));  //暫停，避免斷線

                        String[] TD = new String[9];
                        Elements trs = Stock2330data.select("tr");

                        for (int i=2;i<trs.size();i++)
                        {
                            Elements tds = trs.get(i).select("td");
                            String Firsttd = StartYear +
                                    tds.get(0).text()
                                            .replaceAll("/","-")
                                            .substring(3,9);
                            TD[0] = Firsttd;

                            for (int j=1; j<tds.size();j++)
                            {
                                String td = tds.get(j).text();

                                String Td = td.replaceAll("/","-");

                                TD[j] = Td;
                            }
                            statement.executeUpdate
                                    ("insert ignore into Stock_2330 values" +
                                            "('"+TD[0]+"','" +TD[1]+"','" +TD[2]+"','" +TD[3]+"','" +TD[4]+"','"
                                            +TD[5]+"','" +TD[6]+"','" +TD[7]+"','" +TD[8]+"')");
                            System.out.println(Arrays.toString(TD));
                        }
                    }
                Start.add(Calendar.MONTH,1);
                Sta = SDF.format(Start.getTime());
                Next.add(Calendar.MONTH,1);
                Nex = SDF.format(Next.getTime());
            }
            /*
            下載當月資料
            */
            String URL = Stocklink.replace("********",Now.replaceAll("-",""));
            Document Stock2330data = Jsoup.parse(new URL(URL),5000);
            sleep((int)(Math.random()*2000+3000));  //暫停，避免斷線

            String[] TD = new String[9];
            Elements trs = Stock2330data.select("tr");
            for (int i=2;i<trs.size();i++)
            {
                Elements tds = trs.get(i).select("td");
                String Firsttd = StartYear +
                        tds.get(0).text()
                                .replaceAll("/","-")
                                .substring(3,9);
                TD[0] = Firsttd;

                for (int j=1; j<tds.size();j++)
                {
                    String td = tds.get(j).text();

                    String Td = td.replaceAll("/","-");

                    TD[j] = Td;
                }
                statement.executeUpdate
                        ("insert ignore into Stock_2330 values" +
                                "('"+TD[0]+"','" +TD[1]+"','" +TD[2]+"','" +TD[3]+"','" +TD[4]+"','"
                                +TD[5]+"','" +TD[6]+"','" +TD[7]+"','" +TD[8]+"')");
                System.out.println(Arrays.toString(TD));
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

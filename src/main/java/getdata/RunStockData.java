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
    private Calendar startDate,NextMonthDate;
    private String Stocklink,StockName;
    private Connection conn;

    public void setStartDate(Calendar startDate) { this.startDate = startDate; }
    public void setNextMonthDate(Calendar NextMonthDate) { this.NextMonthDate = NextMonthDate; }
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
        NowDate.getTime();

        super.run();
        try
        {
            String Start = SDF.format(startDate.getTime());
            String StartYear =
                    String.valueOf(startDate.get(Calendar.YEAR));
            String StartYearMonth =
                    String.valueOf(startDate.get(Calendar.YEAR)+String.format("%02d",startDate.get(Calendar.MONTH)+1));

            //String Next = SDF.format(NextMonthDate.getTime());
            String NextYear =
                    String.valueOf(NextMonthDate.get(Calendar.YEAR));
            String NextYearMonth =
                    String.valueOf(NextMonthDate.get(Calendar.YEAR)+String.format("%02d",NextMonthDate.get(Calendar.MONTH)+1));

            NowDate.add(Calendar.MONTH,1);
            String NowYearNextMonth =
                    String.valueOf(NowDate.get(Calendar.YEAR)+String.format("%02d",NowDate.get(Calendar.MONTH)+1));

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

            while (!StartYearMonth.equals(NowYearNextMonth))
            {
                String URL = Stocklink.replace("********",Start.replaceAll("-",""));


                ResultSet RS= statement.executeQuery
                        ("SELECT * FROM " + StockName +
                                " where date >'" +StartYear+"-"+StartYearMonth.substring(4,6)+"-01' " +
                                "AND date <'" +NextYear+"-"+NextYearMonth.substring(4,6)+"-01' ");
                RS.relative(0);

                int A = 0;

                while(RS.next()){ A++;}
                /*

                 */
                if(A>0)
                {
                    System.out.println(StartYearMonth + "月份已有資料，筆數為"+A+"，跳過");
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
                startDate.add(Calendar.MONTH,1);
                StartYear =
                        String.valueOf(startDate.get(Calendar.YEAR));
                StartYearMonth =
                        String.valueOf(startDate.get(Calendar.YEAR)+String.format("%02d",startDate.get(Calendar.MONTH)+1));

                NextMonthDate.add(Calendar.MONTH,1);
                NextYear =
                        String.valueOf(NextMonthDate.get(Calendar.YEAR));
                NextYearMonth =
                        String.valueOf(NextMonthDate.get(Calendar.YEAR)+String.format("%02d",NextMonthDate.get(Calendar.MONTH)+1));
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

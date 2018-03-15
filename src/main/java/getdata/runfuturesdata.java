package getdata;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Calendar;

public class runfuturesdata extends Thread
{
    private int startYear;
    private String Futureslink1,Futureslink2;

    /*
    取得現在時間
    */
    private Calendar rightNow = Calendar.getInstance();
    private int NowYear = rightNow.get(Calendar.YEAR);
    private int NowMonth = rightNow.get(Calendar.MONTH) + 1;

    /*
    設定開始時間
    */
    public void setStartYear(int startYear) { this.startYear = startYear; }

    /*
    設定期貨資料源
     */
    public void setFutureslink1(String futureslink) { this.Futureslink1 = futureslink; }
    public void setFutureslink2(String futureslink) { this.Futureslink2 = futureslink; }

    @Override
    public void run()
    {
        super.run();
        String URL1 = Futureslink1.replace("********","20180314");
        System.out.println(URL1);

        try
        {
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/futures-history", "root", "12345");
            Statement statement = conn.createStatement();
            getstockdata.setStatement(statement);
            statement.executeUpdate
                    (
                            "CREATE TABLE if not exists futures_MTX1" +
                                    "(Date CHAR(40) not null," +
                                    "Opening_price TEXT," +
                                    "Highest_price TEXT," +
                                    "Lowest_price TEXT," +
                                    "Closing_price TEXT," +
                                    "Number_of_transactions TEXT," +
                                    "primary key(Date));"
                    );
            for(;startYear<NowYear;startYear++)
            {
                for (int i=0;i<12;i++)
                {
                    Calendar Day = Calendar.getInstance();
                    Day.set(Calendar.YEAR,startYear);
                    Day.set(Calendar.MONTH,i);
                    int dateofmonth = Day.getActualMaximum(Calendar.DATE);

                    for (int j=0;j<dateofmonth;j++)
                    {
                        String year = String.valueOf(startYear);
                        String month = String.format("%02d",i+1);
                        String day = String.format("%02d",j+1);
                        String URL2 = Futureslink2.replace("********",year+month+day);

                        Document MTX1data = Jsoup.parse(new URL(URL2),5000);
                        Element tr = MTX1data.select("tr").get(6);
                        Elements tds = tr.select("td");

                        switch (tds.size())
                        {
                            case 1:
                                String[] TD = new String[6];
                                TD[0] = year+"/"+month+"/"+day;
                                statement.executeUpdate
                                        ("insert ignore into futures_MTX1 values" +
                                                "('"+TD[0]+"','" +TD[1]+"','" +TD[2]+"','" +TD[3]+"','" +TD[4]+"','"
                                                +TD[5]+"')");

                                sleep((int)(Math.random()*2000+3000));

                                String A = Arrays.toString(TD);
                                System.out.println(A);
                                break;
                            case 5:
                                String[] TD2 = new String[tds.size()+1];
                                TD2[0] = year+"/"+month+"/"+day;

                                for (int k = 0; k < tds.size(); k++)
                                {
                                    String Td = tds.get(k).text();
                                    TD2[k+1] = Td;
                                }
                                statement.executeUpdate
                                        ("insert ignore into futures_MTX1 values" +
                                                "('"+TD2[0]+"','" +TD2[1]+"','" +TD2[2]+"','" +TD2[3]+"','" +TD2[4]+"','"
                                                +TD2[5]+"')");
                                sleep((int)(Math.random()*2000+3000));

                                String B = Arrays.toString(TD2);
                                System.out.println(B);
                                break;
                        }
                    }
                }
            }

            for (int i=0;i<NowMonth;i++)
            {
                Calendar Day = Calendar.getInstance();
                Day.set(Calendar.YEAR,startYear);
                Day.set(Calendar.MONTH,i);
                int dateofmonth = Day.getActualMaximum(Calendar.DATE);

                for (int j=0;j<dateofmonth;j++)
                {
                    String year = String.valueOf(startYear);
                    String month = String.format("%02d",i+1);
                    String day = String.format("%02d",j+1);
                    String URL2 = Futureslink2.replace("********",year+month+day);

                    Document MTX1data = Jsoup.parse(new URL(URL2),5000);
                    Element tr = MTX1data.select("tr").get(6);
                    Elements tds = tr.select("td");

                    switch (tds.size())
                    {
                        case 1:
                            String[] TD = new String[6];
                            TD[0] = year+"/"+month+"/"+day;
                            statement.executeUpdate
                                    ("insert ignore into futures_MTX1 values" +
                                            "('"+TD[0]+"','" +TD[1]+"','" +TD[2]+"','" +TD[3]+"','" +TD[4]+"','"
                                            +TD[5]+"')");
                            sleep((int)(Math.random()*2000+3000));

                            String A = Arrays.toString(TD);
                            System.out.println(A);
                            break;
                        case 5:
                            String[] TD2 = new String[tds.size()+1];
                            TD2[0] = year+"/"+month+"/"+day;

                            for (int k = 0; k < tds.size(); k++)
                            {
                                String Td = tds.get(k).text();
                                TD2[k+1] = Td;
                            }
                            statement.executeUpdate
                                    ("insert ignore into futures_MTX1 values" +
                                            "('"+TD2[0]+"','" +TD2[1]+"','" +TD2[2]+"','" +TD2[3]+"','" +TD2[4]+"','"
                                            +TD2[5]+"')");
                            sleep((int)(Math.random()*2000+3000));

                            String B = Arrays.toString(TD2);
                            System.out.println(B);
                            break;
                    }
                }
            }
            statement.close();
            conn.close();
        }
        catch (SQLException | InterruptedException | IOException e)
        {
            e.printStackTrace();
        }
    }
}



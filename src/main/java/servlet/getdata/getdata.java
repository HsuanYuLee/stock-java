package servlet.getdata;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.*;

public class getdata
{
    private URL url;

    private Connection conn;
    private Statement statement;


    private FileWriter fileWriter;



    public void setUrl(URL url)
    {
        this.url = url;
        System.out.println("存入資料連結");
    }
    public void setFileWriter(FileWriter fileWriter)
    {
        this.fileWriter = fileWriter;
        System.out.println("設定檔案位置");
    }
    public Connection connectMySQL()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/stock-history", "root", "12345");
            System.out.println("連接成功MySQLToJava");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return conn;
    }


    /*
        public getdata() throws SQLException, IOException {
        conn = connectMySQL();

        ResultSet resultSet = null;
        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT name FROM world.country");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while (resultSet.next())
        {
            String name = resultSet.getString("name");
            System.out.println(name);
        }

        resultSet.close();
        statement.close();
        conn.close();

    }

     */

    public void download() throws IOException, SQLException
    {
        Document data = Jsoup.parse(url, 5000);
        ResultSet resultSet = null;

        //建立表格
        /*


        try
        {
            statement = conn.createStatement();
            statement.executeUpdate
                    (
                    "CREATE TABLE if not exists Stock_2330" +
                            "(Date date," +
                            "Trading_shares int," +
                            "Turnover double," +
                            "Opening_price double," +
                            "Highest_price double," +
                            "Lowest_price double," +
                            "Closing_price double," +
                            "Gross_Spread double," +
                            "Number_of_transactions int);"
                    );
            System.out.println("表格建立成功");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
          */


        Elements trs = data.select("tr");
        int i = 1;
        for (Element tr : trs)
        {
            Elements tds = tr.select("td");

            for (Element td : tds)
            {
                String Td = td.text();
                System.out.println(Td + "\t");
                fileWriter.write(Td + "\t");
            }

            System.out.println("\n");
            fileWriter.write("\n");
            i++;
        }

        fileWriter.flush();
        fileWriter.close();

        statement.close();
        conn.close();



        /*
        while (resultSet.next())
        {
            String name = resultSet.getString("name");
            System.out.println(name);
        }

        resultSet.close();
        statement.close();
        conn.close();









        */

    }



}


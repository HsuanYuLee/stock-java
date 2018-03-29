package Manager;

import java.io.InputStream;
import java.util.Properties;

public class ConfigManager
{
    private static ConfigManager config;
    public String Timer_Start_time,Timer_Period_time;
    public String dbstockurl,dbfuturesurl,dbusername,dbpassword;

    public String FuturesLink,Futures_Data_Final_Date;
    public String Futures_Search_Start_Date,Futures_Search_End_Date;

    public String StockLink,Stock_Data_Final_Date;
    public String Stock_Search_Start_Date,Stock_Search_End_Date;


    public static ConfigManager instance()
    {
        if (config == null)
        {
            config = new ConfigManager();
        }
        return config;
    }


    private ConfigManager()
    {
        try
        {
            Properties properties = new Properties();
            InputStream is = ConfigManager.class.getResourceAsStream("/config");
            properties.load(is);

            Timer_Start_time = properties.getProperty("Timer_Start_time");
            Timer_Period_time = properties.getProperty("Timer_Period_time");

            dbstockurl = properties.getProperty("jdbc.stockurl");
            dbfuturesurl = properties.getProperty("jdbc.futuresurl");
            dbusername = properties.getProperty("jdbc.username");
            dbpassword = properties.getProperty("jdbc.password");

            FuturesLink = properties.getProperty("FuturesLink");
            Futures_Data_Final_Date = properties.getProperty("Futures_Data_Final_Date");
            Futures_Search_Start_Date = properties.getProperty("Futures_Search_Start_Date");
            Futures_Search_End_Date = properties.getProperty("Futures_Search_End_Date");

            StockLink = properties.getProperty("StockLink");
            Stock_Data_Final_Date = properties.getProperty("Stock_Data_Final_Date");
            Stock_Search_Start_Date = properties.getProperty("Stock_Search_Start_Date");
            Stock_Search_End_Date = properties.getProperty("Stock_Search_End_Date");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }




}

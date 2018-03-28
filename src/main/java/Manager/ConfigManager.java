package Manager;

import java.io.InputStream;
import java.util.Properties;

public class ConfigManager
{
    private static ConfigManager config;
    public String dbstockurl,dbfuturesurl,dbusername,dbpassword,dbdriver;
    public String FuturesLink,Futures_End_Year,Futures_End_Month,Futures_End_Day;


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

            dbstockurl = properties.getProperty("jdbc.stockurl");
            dbfuturesurl = properties.getProperty("jdbc.futuresurl");
            dbusername = properties.getProperty("jdbc.username");
            dbpassword = properties.getProperty("jdbc.password");
            dbdriver = properties.getProperty("jdbc.driver");

            FuturesLink = properties.getProperty("FuturesLink");
            Futures_End_Year = properties.getProperty("Futures_End_Year");
            Futures_End_Month = properties.getProperty("Futures_End_Month");
            Futures_End_Day = properties.getProperty("Futures_End_Day");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }




}

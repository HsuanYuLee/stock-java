import Service.getFuturesHistory;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            getFuturesHistory getFuturesHistory = new getFuturesHistory();
            getFuturesHistory.start();


        }catch (Exception e){ e.printStackTrace(); }



    }


}

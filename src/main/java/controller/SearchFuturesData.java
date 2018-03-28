package controller;

import dao.futuresDaoImpl;
import domain.futures;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchFuturesData extends HttpServlet
{
    futuresDaoImpl fDao = new futuresDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        futures title = new futures();
        title.setDate("Date");
        title.setOpening_price("Opening_price");
        title.setHighest_price("Highest_price");
        title.setLowest_price("Lowest_price");
        title.setClosing_price("Closing_price");
        title.setNumber_of_transactions("Number_of_transactions");

        resp.setContentType("text/html");
        resp.setStatus(HttpServletResponse.SC_OK);

        resp.getWriter().println("<h1>2000-01-01號之後的資料</h1>");
        resp.getWriter().println("<h4>"+"Date" +" "
                + "Opening_price" +" "
                + "Highest_price" +" "
                + "Lowest_price" +" "
                + "Closing_price" +" "
                + "Number_of_transactions"+"</h4>");
        try
        {
            HashMap<String,futures> flist = fDao.findByDate("'2000-01-01'");
            for (int i=0;i<flist.size();i++)
            {
                String Date = String.valueOf(flist.get(""+i+"").getDate());
                String Opening_price = flist.get(""+i+"").getOpening_price();
                String Highest_price = flist.get(""+i+"").getHighest_price();
                String Lowest_price = flist.get(""+i+"").getLowest_price();
                String Closing_price = flist.get(""+i+"").getClosing_price();
                String Number_of_transactions = flist.get(""+i+"").getNumber_of_transactions();

                resp.getWriter().println
                        ("<p>"
                                +Date +" "
                                + Opening_price +" "
                                + Highest_price +" "
                                + Lowest_price +" "
                                + Closing_price +" "
                                + Number_of_transactions
                                +"</p>");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }



    }
}

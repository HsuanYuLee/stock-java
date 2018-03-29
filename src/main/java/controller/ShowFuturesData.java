package controller;

import Manager.ConfigManager;
import dao.futuresDaoImpl;
import domain.futures;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class ShowFuturesData extends HttpServlet
{
    private futuresDaoImpl fDao = new futuresDaoImpl();
    private String StartDate = ConfigManager.instance().Futures_Search_Start_Date;
    private String EndDate = ConfigManager.instance().Futures_Search_End_Date;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    {
        resp.setContentType("text/html");
        resp.setStatus(HttpServletResponse.SC_OK);

        try
        {
            if(StartDate.equals(EndDate))
            {
                resp.getWriter().println("<h1 align=\"center\">"+StartDate+" 號的資料</h1>");
            }
            else
            {
                resp.getWriter().println("<h1 align=\"center\">"+StartDate+" 號到"+EndDate+" 號的資料</h1>");
            }

            resp.getWriter().println("<table BORDER align=\"center\">");
            resp.getWriter().println
                    ("<tr>"
                            + "<th>Date</th>"
                            + "<th>Opening_price</th>"
                            + "<th>Highest_price</th>"
                            + "<th>Lowest_price</th>"
                            + "<th>Closing_price</th>"
                            + "<th>Number_of_transactions</th>"
                            +"</tr>");
            ArrayList<futures> fl = fDao.findByDate(StartDate,EndDate);
            for (futures fdata:fl)
            {
                String Date = String.valueOf(fdata.getDate());
                String Open = fdata.getOpening_price();
                String High = fdata.getHighest_price();
                String Low = fdata.getLowest_price();
                String Close = fdata.getClosing_price();
                String Num = fdata.getNumber_of_transactions();

                resp.getWriter().println
                        ("<tr>"
                                + "<th>"+ Date +"</th>"
                                + "<th>"+Open +"</th>"
                                + "<th>"+High +"</th>"
                                + "<th>"+Low +"</th>"
                                + "<th>"+Close +"</th>"
                                + "<th>"+Num +"</th>"
                                + "</tr>");
            }
            resp.getWriter().println("</table>");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}

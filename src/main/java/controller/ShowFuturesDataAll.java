package controller;

import dao.futuresDaoImpl;
import domain.futures;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class ShowFuturesDataAll extends HttpServlet
{
    private futuresDaoImpl fDao = new futuresDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    {
        resp.setContentType("text/html");
        resp.setStatus(HttpServletResponse.SC_OK);

        try
        {
            resp.getWriter().println("<h1 align=\"center\">"+"到目前為止的所有小台指資料"+"</h1>");

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
            ArrayList<futures> fl = fDao.findAll();
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


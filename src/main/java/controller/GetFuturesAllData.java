package controller;

import Service.GetFuturesData;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetFuturesAllData extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    {
        GetFuturesData getFuturesData = new GetFuturesData();
        getFuturesData.start();
    }
}

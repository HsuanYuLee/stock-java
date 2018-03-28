package domain;

public class futures
{
    private String Date,Opening_price,Highest_price,Lowest_price,Closing_price,Number_of_transactions;

    public void setDate(String date) { Date = date; }

    public java.sql.Date getDate() { return java.sql.Date.valueOf(Date); }

    public void setOpening_price(String opening_price) { Opening_price = opening_price; }

    public String getOpening_price() { return Opening_price; }

    public void setHighest_price(String highest_price) { Highest_price = highest_price; }

    public String getHighest_price() { return Highest_price; }

    public void setLowest_price(String lowest_price) { Lowest_price = lowest_price; }

    public String getLowest_price() { return Lowest_price; }

    public void setClosing_price(String closing_price) { Closing_price = closing_price; }

    public String getClosing_price() { return Closing_price; }

    public void setNumber_of_transactions(String number_of_transactions)
    { Number_of_transactions = number_of_transactions; }

    public String getNumber_of_transactions() { return Number_of_transactions; }
}

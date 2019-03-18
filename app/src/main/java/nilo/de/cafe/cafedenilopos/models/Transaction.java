package nilo.de.cafe.cafedenilopos.models;

import java.util.Date;

public class Transaction {
    private int id;
    private String email;
    private String date;
    private  double total_price, cash;

    public Transaction( String date, double cash,double total_price, String email)
    {

        this.date = date;
        this.cash = cash;
        this.total_price = total_price;
        this.email = email;
    }

    public Transaction( String date, double cash , double total_price)
    {
        this.cash = cash;
        this.date = date;
        this.total_price = total_price;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }
}

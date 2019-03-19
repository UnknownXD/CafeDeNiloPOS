package nilo.de.cafe.cafedenilopos.models;

public class Transaction {
    private double cash;
    private String date;
    private double discount;
    private String email;
    private int id;
    private double total_price;
    private double vat;

    public Transaction(String date, double cash, double total_price, String email, double vat, double discount) {
        this.date = date;
        this.cash = cash;
        this.total_price = total_price;
        this.vat = vat;
        this.discount = discount;
        this.email = email;
    }

    public Transaction(String date, double cash, double total_price, double vat, double discount) {
        this.cash = cash;
        this.date = date;
        this.total_price = total_price;
        this.vat = vat;
        this.discount = discount;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }

    public double getTotal_price() {
        return this.total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public double getCash() {
        return this.cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getDiscount() {
        return this.discount;
    }

    public double getVat() {
        return this.vat;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }
}

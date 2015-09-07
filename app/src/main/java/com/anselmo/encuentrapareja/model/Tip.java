package com.anselmo.encuentrapareja.model;

/**
 * Created by naranya on 9/7/15.
 */
public class Tip {
    private int id;
    private String tip;
    private String date;

    public Tip() {}

    public Tip(int id, String tip, String date) {
        this.id = id;
        this.tip = tip;
        this.date = date;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTip() { return tip; }
    public void setTip(String tip) { this.tip = tip; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}

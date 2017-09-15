package com.example.tanmayvakare.bustrackingsystem;

import static android.R.string.no;

/**
 * Created by tanmayvakare on 12/06/17.
 */

public class BusDetails {

    private String no;
    private double locLat;
    private double locLong;
    private String source;
    private String dest;

    public BusDetails(String no, double locLat, double locLong, String source, String dest) {
        this.no = no;
        this.locLat = locLat;
        this.locLong = locLong;
        this.source = source;
        this.dest = dest;
    }

    public String getNo() {return no;}

    public void setNo(String no) {
        this.no = no;
    }

    public double getLocLat() {
        return locLat;
    }

    public void setLocLat(double locLat) {
        this.locLat = locLat;
    }

    public double getLocLong() {
        return locLong;
    }

    public void setLocLong(double locLong) {
        this.locLong = locLong;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

}

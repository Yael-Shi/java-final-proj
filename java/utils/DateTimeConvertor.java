package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateTimeConvertor {
    private java.sql.Date date;
    private java.sql.Time time;
    private String dateTimeStr;

    public DateTimeConvertor(java.sql.Date date, java.sql.Time time) {
        this.date = date;
        this.time = time;
        dateTimeToString();
    }

    public DateTimeConvertor(String dateTimeStr) {
        this.dateTimeStr = dateTimeStr;
        try {
            stringToDateTimeSQL(this.dateTimeStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public java.sql.Date getDate() {
        return this.date;
    }

    public void setDate(java.sql.Date date){
        this.date = date;
    }

    public java.sql.Time getTime() {
        return this.time;
    }

    public void setTime(java.sql.Time time){
        this.time = time;
    }

    public String getDateTimeSrt() {
        return this.dateTimeStr;
    }

    public void setDateTimeStr(String dateTimeStr){
        this.dateTimeStr = dateTimeStr;
    }

    public void stringToDateTimeSQL(String formattedDateTime) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date parsedDateTime = formatter.parse(formattedDateTime);
        java.sql.Date date = new java.sql.Date(parsedDateTime.getTime());
        setDate(date);
        java.sql.Time time = new java.sql.Time(parsedDateTime.getTime());
        setTime(time);
    }

    public void dateTimeToString() {
        DateFormat formatterD = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat formatterT = new SimpleDateFormat("HH:mm:ss");
        setDateTimeStr(formatterD.format(this.date) + " " + formatterT.format(this.time));
    }
}



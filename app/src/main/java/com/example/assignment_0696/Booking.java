package com.example.assignment_0696;

public class Booking {
    private String bookingId;
    private String movieName;
    private int seats;
    private double totalPrice;
    private String date;
    private String time;
    private String dateTime;
    private long timestamp;

    public Booking() {} // required for Firebase

    public Booking(String bookingId, String movieName, int seats,
                   double totalPrice, String date, String time,
                   String dateTime, long timestamp) {
        this.bookingId  = bookingId;
        this.movieName  = movieName;
        this.seats      = seats;
        this.totalPrice = totalPrice;
        this.date       = date;
        this.time       = time;
        this.dateTime   = dateTime;
        this.timestamp  = timestamp;
    }

    public String getBookingId()  { return bookingId; }
    public String getMovieName()  { return movieName; }
    public int getSeats()         { return seats; }
    public double getTotalPrice() { return totalPrice; }
    public String getDate()       { return date; }
    public String getTime()       { return time; }
    public String getDateTime()   { return dateTime; }
    public long getTimestamp()    { return timestamp; }

    public void setBookingId(String bookingId) { this.bookingId = bookingId; }
}
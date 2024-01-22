package org.example;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.*;
import java.util.ArrayList;
import java.util.Objects;


public class Ticket implements ticketMaker {
    Duration durationOfFlight;
    String origin;
    String origin_name;
    String destination;
    String destination_name;
    String departure_date;
    String  departure_time;
    String arrival_date;
    String arrival_time;
    String carrier;
    int stops;
    double price;
    public Ticket(String origin, String origin_name, String destination, String destination_name, String departure_date, String departure_time, String arrival_date, String arrival_time, String carrier, int stops, double price) {
        this.origin = origin;
        this.origin_name = origin_name;
        this.destination = destination;
        this.destination_name = destination_name;
        this.departure_date = departure_date;
        this.departure_time = departure_time;
        this.arrival_date = arrival_date;
        this.arrival_time = arrival_time;
        this.carrier = carrier;
        this.stops = stops;
        this.price = price;
        this.durationOfFlight = lostTime();
    }

    public String getOrigin() {
        return origin;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    public String getOrigin_name() {
        return origin_name;
    }
    public void setOrigin_name(String origin_name) {
        this.origin_name = origin_name;
    }
    public String getDestination() {
        return destination;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }
    public String getDestination_name() {
        return destination_name;
    }
    public void setDestination_name(String destination_name) {
        this.destination_name = destination_name;
    }
    public String getDeparture_date() {
        return departure_date;
    }
    public void setDeparture_date(String departure_date) {
        this.departure_date = departure_date;
    }
    public String getDeparture_time() {
        return departure_time;
    }
    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }
    public String getArrival_date() {
        return arrival_date;
    }
    public void setArrival_date(String arrival_date) {
        this.arrival_date = arrival_date;
    }
    public String getArrival_time() {
        return arrival_time;
    }
    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }
    public String getCarrier() {
        return carrier;
    }
    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }
    public int getStops() {
        return stops;
    }
    public void setStops(int stops) {
        this.stops = stops;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    @Override
    public ZonedDateTime getZonedDateTimeArr() {
        String[] dats = getArrival_date().split("\\.");
        String[] times = getArrival_time().split(":");
        LocalDate date = LocalDate.of(Integer.parseInt(dats[2]+2000),
                Integer.parseInt(dats[1]),
                Integer.parseInt(dats[0]));
        LocalTime time = LocalTime.of(Integer.parseInt(times[0]),
                Integer.parseInt(times[1]), 0);
        ZonedDateTime zonedDateTimeARR = ZonedDateTime.of(date,
                time,
                ZoneId.of("Asia/Tel_Aviv"));
        return zonedDateTimeARR.withZoneSameInstant(ZoneId.of("Asia/Vladivostok"));
    }
    @Override
    public ZonedDateTime getZonedDateTimeDep(){
        String[] dats = getDeparture_date().split("\\.");
        String[] times = getDeparture_time().split(":");
        LocalDate date = LocalDate.of(Integer.parseInt(dats[2]+2000),
                Integer.parseInt(dats[1]),
                Integer.parseInt(dats[0]));
        LocalTime time = LocalTime.of(Integer.parseInt(times[0]),
                Integer.parseInt(times[1]), 0);
        return ZonedDateTime.of(date,
                time,
                ZoneId.of("Asia/Vladivostok"));

    }
    @Override
    public Duration  lostTime() {
        return Duration.between(getZonedDateTimeDep(), getZonedDateTimeArr());

    }

    public Ticket() {
    }

    @Override
    public ArrayList<Ticket> ticketPool(String dep, String arrival) throws IOException {
        ArrayList<Ticket> ticketList = new ArrayList<>();
        String dir = System.getProperty("user.dir");
        String content = new String(Files.readAllBytes(Path.of(dir+"/tickets.json")));
        JsonObject jsonObject = JsonParser.parseString(content).getAsJsonObject();
        JsonArray arr = jsonObject.getAsJsonArray("tickets");
        for (var ticket:arr){
            JsonObject job = ticket.getAsJsonObject();
            if (Objects.equals(job.get("origin_name").getAsString(), dep) && Objects.equals(job.get("destination_name").getAsString(), arrival)){
                Ticket newTicket = new Ticket(job.get("origin").getAsString(),
                        job.get("origin_name").getAsString(),
                        job.get("destination").getAsString(),
                        job.get("destination_name").getAsString(),
                        job.get("departure_date").getAsString(),
                        job.get("departure_time").getAsString(),
                        job.get("arrival_date").getAsString(),
                        job.get("arrival_time").getAsString(),
                        job.get("carrier").getAsString(),
                        job.get("stops").getAsInt(),
                        job.get("price").getAsDouble());
                ticketList.add(newTicket);
            }
        }
        return ticketList;
    }


}

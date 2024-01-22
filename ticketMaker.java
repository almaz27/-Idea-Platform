package org.example;
import java.io.IOException;
import java.sql.Time;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface ticketMaker {

    public ZonedDateTime getZonedDateTimeDep();
    public ZonedDateTime getZonedDateTimeArr();
    public Duration  lostTime();
    public ArrayList<Ticket> ticketPool(String dep, String arrival) throws IOException;



}

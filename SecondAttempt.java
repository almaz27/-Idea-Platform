package org.example;

import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class SecondAttempt {
    public static void main(String[] args) throws IOException {
        Ticket ticket = new Ticket();
        ArrayList<Ticket> ticketList = ticket.ticketPool("Владивосток","Тель-Авив");

        Set<String> carriers = ticketList.stream().map(Ticket::getCarrier).collect(Collectors.toSet());

        HashMap<String, Duration> carrier_min_duration = new HashMap<>();

        for (String carrier : carriers) {
            carrier_min_duration.put(carrier, ticketList.stream().
                    filter(ticket1 -> ticket1.getCarrier().equals(carrier))
                    .min(Comparator.comparing(ticket1 -> ticket1.durationOfFlight))
                    .get().durationOfFlight);
        }

        for (Map.Entry<String, Duration> entry : carrier_min_duration.entrySet()) {
            System.out.println("Перевозчик: "+entry.getKey() + ": " + " минимальное время полета между городами Владивосток и Тель-Авив "+printTime(entry.getValue()));
        }

    }
    public static String printTime(Duration d){
        if(d.toDays() == 0){
            return d.toHours()+":"+d.minusHours(d.toHours()).toMinutes();
        }
        return d.toDays()+"days"+d.minusDays(d.toDays()).toHours()+":"+d.minusHours(d.minusDays(d.toDays()).toHours()).toMinutes();
    }
}

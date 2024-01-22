package org.example;

import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            System.out.println("Перевозчик: "+entry.getKey() + ":" + " минимальное время полета между городами Владивосток и Тель-Авив "+printTime(entry.getValue()));
        }

        List<Double> sorted = ticketList.stream().map(Ticket::getPrice).sorted().toList();
        double result = getSubtractOfAvgMed(sorted);
        System.out.println("Разницу между средней ценой  и медианой для полета между городами Владивосток и Тель-Авив "+result);



    }
    public static String printTime(Duration d){
        if(d.toDays() == 0){
            return d.toHours()+":"+d.minusHours(d.toHours()).toMinutes();
        }
        return d.toDays()+"days"+d.minusDays(d.toDays()).toHours()+":"+d.minusHours(d.minusDays(d.toDays()).toHours()).toMinutes();
    }
    public static double getSubtractOfAvgMed(List<Double> sorted){
        double avgPrice = sorted.stream().reduce((double) 0,(subtotal, element)->subtotal+element)/sorted.size();
        double medianPrice;

        if(sorted.size() % 2 == 0){
            medianPrice = (double) (sorted.get(sorted.size()/2)+sorted.get(sorted.size()/2-1))/2;
        } else {
            medianPrice = sorted.get((sorted.size()-1)/2);
        }
        return avgPrice-medianPrice;
    }
}

package org.example;

import java.time.Duration;
import java.util.*;


public class MainClass {
    public static void main(String[] args) throws Exception {
        Ticket ticket = new Ticket();
        ArrayList<Ticket> ticketList = ticket.ticketPool("Владивосток","Тель-Авив");
        getDemandedInfo(ticketList);
    }
    public static void getDemandedInfo(ArrayList<Ticket> ticketList){
        double[] prices = new double[ticketList.size()];
        Duration mini = ticketList.get(0).durationOfFlight;
        prices[0]=ticketList.get(0).getPrice();
        int index = 0;
        // собираем цены в массив и ищем минимальную время полета
        for(int i =1; i<ticketList.size();i++){
            if(mini.compareTo(ticketList.get(i).durationOfFlight)>0){
                mini = ticketList.get(i).durationOfFlight;
                index = i;
            }
            prices[i] = ticketList.get(i).getPrice();
        }
        double difference =  getSubtractOfAvgMed(prices);
        printResults(ticketList.get(index),difference);


    }
    public static double getSubtractOfAvgMed(double[]prices){
        double median = 0;
        double sum = 0;
        Arrays.sort(prices);
        if (prices.length % 2 == 0)
            median = ((double)prices[prices.length/2] + (double)prices[prices.length/2 - 1])/2;
        else
            median = (double) prices[prices.length/2];
        for(double price: prices){
            sum += price;
        }
        return (sum/prices.length)-median;
    }

    public static String printTime(Duration d){
        if(d.toDays() == 0){
            return d.toHours()+":"+d.minusHours(d.toHours()).toMinutes();
        }
        return d.toDays()+"days"+d.minusDays(d.toDays()).toHours()+":"+d.minusHours(d.minusDays(d.toDays()).toHours()).toMinutes();
    }
    public static void printResults(Ticket ticket, double difference){
        System.out.println("Минимальное время полета между городами Владивосток и Тель-Авив для каждого авиаперевозчика составляет:" + printTime(ticket.durationOfFlight));
        System.out.println("Перевозчик: "+ticket.getCarrier());
        System.out.println("Время вылета: "+ticket.getDeparture_time());
        System.out.println("Разница между средней ценой  и медианой для полета между городами Владивосток и Тель-Авив: "+difference);
    }

}

    

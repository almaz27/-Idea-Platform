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
        return median - (sum/prices.length);
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

    /*public static ArrayList<Ticket> ticketPool(String dep, String arrival) throws IOException {
        List<Ticket> ticketList = new ArrayList<>();
        String content = new String(Files.readAllBytes(Paths.get("/Users/almaz1991/Documents/SPRING PROJECTS/Olga/src/main/java/org/example/tickets.json")));
        JsonObject jsonObject = new JsonParser().parseString(content).getAsJsonObject();
        JsonArray arr = jsonObject.getAsJsonArray("tickets");
        //Собираем из жсон обьекта нужных нам направления List<Ticket> ticketList
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
        return (ArrayList<Ticket>) ticketList;
    }*/





        /* Теперь давайте рассчитаем минимальное время полета и разницу между средней ценой и медианой.*/




   /* В этом коде мы сначала формируем две карты: одну для минимального времени полета для каждого авиаперевозчика и вторую для цен билетов для каждого авиаперевозчика. Затем мы вычисляем медиану и среднее значение цен билетов для каждого авиаперевозчика и выводим разницу между ними.

        Этот код обрабатывает только билеты между Владивостоком и Тель-Авивом. Если вам нужно обрабатывать билеты между другими городами, вы можете легко изменить этот код, заменив названия городов.

        Обратите внимание: Это решение предполагает, что в файле JSON содержатся все необходимые данные и они корректны. В реальном проекте вам, вер*/
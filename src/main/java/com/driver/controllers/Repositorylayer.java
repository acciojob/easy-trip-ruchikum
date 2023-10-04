package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public class Repositorylayer {
    HashMap<String,Airport> airportMap=new HashMap<>();
    HashMap<City,String> cityAirportidMap=new HashMap<>();
    HashMap<Integer,Flight> flightMap=new HashMap<>();
    HashMap<Integer, Passenger> passengerMap=new HashMap<>();

    HashMap<Integer,List<Integer>> PassengerFlightMap=new HashMap<>();
    HashMap<Integer, List<Integer>> FightPassengerMap=new HashMap<>();
   public String addAirport(Airport airport){
       airportMap.put(airport.getAirportName(),airport);
       cityAirportidMap.put(airport.getCity(),airport.getAirportName());
       return "SUCCESS";
   }
   public  String addFlight(Flight flight){
       flightMap.put(flight.getFlightId(),flight);

       return "SUCCESS";
   }
   public String addPassenger(Passenger passenger){
       passengerMap.put(passenger.getPassengerId(),passenger);
       return "SUCCESS";
   }
   public double getShortestDurationOfPossibleBetweenTwoCities(City from,City to){
       double min=Double.MAX_VALUE;
       for(Flight flight:flightMap.values()){
           if(flight.getFromCity()==from && flight.getToCity()==to){
               min=Math.min(min,flight.getDuration());
           }
       }
       min=min==Double.MAX_VALUE?-1.0:min;
       return min;
   }
   public int getNumberofflightbooking(Integer flightID){
       return FightPassengerMap.getOrDefault(flightID,new ArrayList<>()).size();
   }
   public  int countOfBookingsDoneByPassengerAllCombined(Integer passengerId){
       List<Integer> Flist=PassengerFlightMap.getOrDefault(passengerId,new ArrayList<>());
       return Flist.size();
   }
   public int getNumberOfPeopleOn(Date date,String airportname){
       if(airportMap.containsKey(airportname)==false){
           return 0;
       }
       City city=airportMap.get(airportname).getCity();
       int total=0;
       for(Integer flight:FightPassengerMap.keySet()){
          Flight fObj= flightMap.get(flight);
          if(fObj.getFlightDate().equals(date) && (fObj.getToCity().equals(city) ||fObj.getFromCity().equals(city))){
              total+=FightPassengerMap.get(flight).size();
          }
       }
       return total;

   }
   public String getAirportNameFromFlightId(Integer flightId){
       String ans=null;
       if(flightMap.containsKey(flightId)){
           City city=flightMap.get(flightId).getFromCity();
           if(cityAirportidMap.containsKey(city)){
               ans=cityAirportidMap.get(city);
           }
       }
       return ans;

   }
   public String cancelATicket(Integer flightId,Integer passengerId){
       if(FightPassengerMap.containsKey(flightId)){
           List<Integer> pList=FightPassengerMap.get(flightId);
           for(int i=0;i<pList.size();i++){
               if(pList.get(i)==passengerId){
                   pList.remove(i);
                   List<Integer> flist=PassengerFlightMap.get(passengerId);
                   for(int j=0;j<flist.size();j++){
                       if(flist.get(j)==flightId)flist.remove(j);
                   }
                   return "SUCCESS";
               }
           }
       }
       return "FAILURE";
   }
   public String bookATicket(Integer flightId,Integer PassengerId){
       List<Integer> pList=FightPassengerMap.getOrDefault(flightId,new ArrayList<>());
       List<Integer> FList=PassengerFlightMap.getOrDefault(PassengerId,new ArrayList<>());
       Flight flight=flightMap.get(flightId);
       if(pList.size()==flight.getMaxCapacity() || pList.contains(PassengerId)){
           return "FAILURE";
       }
       pList.add(PassengerId);
       FList.add(flightId);
       PassengerFlightMap.put(PassengerId,FList);
       FightPassengerMap.put(flightId,pList);
       return "SUCCESS";
   }
   public int calculateFlightFare(Integer flightId){
       int passengerBooked=FightPassengerMap.get(flightId).size();
       return 3000+(passengerBooked*50);
   }
   public String getLargestAirportName(){
       int maxterminal=0;
       for(Airport airport:airportMap.values()){
           if(airport.getNoOfTerminals()>maxterminal) maxterminal=airport.getNoOfTerminals();
       }
       ArrayList<String> list=new ArrayList<>();
       for(Airport airport:airportMap.values()){
           if(airport.getNoOfTerminals()==maxterminal) list.add(airport.getAirportName());
       }
       Collections.sort(list);
       return list.get(0);
   }

}

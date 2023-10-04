package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import java.util.Date;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Servicelayer {

    Repositorylayer repoObj=new Repositorylayer();

    public String addAirport(Airport airport){
        return repoObj.addAirport(airport);
    }
    public String getLargestAirportName(){
        return repoObj.getLargestAirportName();
    }
    public String addFlight(Flight flight){
        return repoObj.addFlight(flight);
    }
    public String addPassenger(Passenger passenger){
        return repoObj.addPassenger(passenger);
    }
    public String bookATicket(Integer flightId,Integer passengerId){
        return repoObj.bookATicket(flightId,passengerId);
    }
    public int calculateFlightFare(Integer flightId){
        return repoObj.calculateFlightFare(flightId);
    }
    public  double getShortestDurationOfPossibleBetweenTwoCities(City from , City to){
        return repoObj.getShortestDurationOfPossibleBetweenTwoCities(from,to);
    }
    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId){
        return repoObj.countOfBookingsDoneByPassengerAllCombined(passengerId);
    }
    public int getNumberOfPeopleOn(Date date,String airportname){
        return repoObj.getNumberOfPeopleOn(date,airportname);
    }
    public String cancelATicket(Integer flightId ,Integer passengerId){
        return repoObj.cancelATicket(flightId,passengerId);
    }
    public String getAirportNameFromFlightId(Integer flightId){
        return repoObj.getAirportNameFromFlightId(flightId);
    }
    public int  calculateRevenueOfAFlight(Integer fligthID){
        int currbooking=repoObj.getNumberofflightbooking(fligthID);
        int rev=0;
        for(int i=0;i<currbooking;i++){
            rev+=3000+(i*50);
        }
        return rev;
    }
}

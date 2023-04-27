package com.example.bloodbank.example;

import java.util.HashMap;
import java.util.Map;

// Client class
public class PrototypeDemo {
    public static void main(String[] args) {

        Map<String, Prototype> carCache = new HashMap<>();

        // Create and cache prototype cars
        Car honda = new Car("Honda", "Civic", 2018);
        Car toyota = new Car("Toyota", "Corolla", 2019);
        carCache.put("Honda Civic", honda);
        carCache.put("Toyota Corolla", toyota);

        // Clone cars from cache and modify them
        Car hondaClone = (Car) carCache.get("Honda Civic").clone();
        hondaClone.setYear(2020);

        Car toyotaClone = (Car) carCache.get("Toyota Corolla").clone();
        toyotaClone.setMake("Lexus");

        System.out.println(hondaClone);
        System.out.println(toyotaClone);
    }
}

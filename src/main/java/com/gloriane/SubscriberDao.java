package com.gloriane;

import java.util.ArrayList;
import java.util.List;

// Implementation to save subscriber to database
public class SubscriberDao {

    // This list acts like a fake database
    private List<Subscriber> subscribers = new ArrayList<>();

   public void save(Subscriber subscriber) {
        subscribers.add(subscriber);
       System.out.println("Subscriber saved: " + subscriber);
    }

    // Implementation to find all subscribers from database
   public List<Subscriber> findByAll() {
       List<Subscriber> findAll = subscribers;
       return subscribers;
    }

    // Implementation to find subscriber by ID from database
     public void findById(int id) {
        for (Subscriber subscriber : subscribers) {
            if (subscriber.getId() == id) {
                System.out.println("Subscriber found: " + subscriber);
                return;
            }
        }
        System.out.println("Subscriber with ID " + id + " not found.");
    }
}

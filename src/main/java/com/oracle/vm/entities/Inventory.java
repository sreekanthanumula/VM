package com.oracle.vm.entities;

import com.oracle.vm.exception.ProductNotFoundException;

import java.text.NumberFormat;
import java.util.*;

public class Inventory {
    public static final NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.UK);
    private List<Double> prices = List.of(1.27, 3.71, 2.94, 2.83, 1.50, 0.75);
    private List<String> products = List.of("Snickers", "Cadbury Dairy Milk", "Maltesers", "Cadbury Wispa", "Galaxy", "Munch");

    public Inventory() {
    }

    public void display() {
        for (int i = 0; i < products.size(); i++)
            System.out.println(i+ ") " + products.get(i) + " : " + formatter.format(prices.get(i)));
    }

    public void remove(int id) throws ProductNotFoundException {
        if (products.size() <= id)
            throw new ProductNotFoundException(Integer.toString(id));
        products.remove(id);
        prices.remove(id);

    }

    public double displayOptions() {
        display();
        Integer choice = null;
        System.out.println("Please choose the item id to purchase.");
        while(choice == null) {
            Scanner in = new Scanner(System.in);
            try {
                choice = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid input.");
            }

            if (choice < 0 || choice >= products.size()) {
                int c = choice;
                choice = null;
                throw new ProductNotFoundException(String.valueOf(c));
            }

        }

        return prices.get(choice);
    }
}

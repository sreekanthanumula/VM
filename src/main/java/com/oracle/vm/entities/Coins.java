package com.oracle.vm.entities;

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.IntStream;

public class Coins {
    public static final NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.UK);
    private List<Integer> coins = List.of(5, 8, 10, 14, 20, 20, 20);
    private List<Double> denominations = List.of(2.00, 1.00, 0.50, 0.20, 0.10, 0.02, 0.0100);

    public Coins() {
    }

    public void display() {
        displayDenominationsCoins(this.coins);
    }

    public void display(List<Integer> coins) {
        displayDenominationsCoins(coins);
    }

    public void setCoins(List<Integer> coins) {
        this.coins = coins;
    }

    public List<Double> getDenominations() {
        return denominations;
    }

    public List<Integer> getCoins() {
        return coins;
    }

    private void displayDenominationsCoins(List<Integer> coins) {
        System.out.print("[");
        for (int i = 0; i < denominations.size(); i++)
           System.out.print(formatter.format(denominations.get(i)) + " ("+ coins.get(i) + ")," + " \t ");
        System.out.print("]");
        System.out.println("");
    }

}
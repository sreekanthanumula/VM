package com.oracle.vm.entities;

import com.oracle.vm.exception.NoEnoughCoinsException;

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

public class VendingMachine {
    public static final NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.UK);
    private Inventory inventory;
    private Coins coins;

    private List<Integer> userInput;

    public VendingMachine(Inventory inventory, Coins coins) {
        this.inventory = inventory;
        this.coins = coins;
    }

    public double takeUserInput() {
        userInput = getUserInputCoins();

        double total = 0.0;

        for (int i = 0; i < userInput.size(); i++) {
            total += coins.getDenominations().get(i) * userInput.get(i);
        }
        return total;
    }

    public List<Integer> returnChane(double change) {
        //easy to perform integer operations than double
        int c = (int) Math.round(change * 100);

        List<Integer> result = new ArrayList<>();

        // cloned coins = vm coins  + user deposited coins
        List<Integer> cloned_coins = new ArrayList<>(coins.getCoins());
        for (int i = 0; i < userInput.size(); i++) {
            cloned_coins.set(i, cloned_coins.get(i) + userInput.get(i));
        }



        //Always try to give minimum no of coins to user
        for (int i = 0; i < userInput.size(); i++) {
            {
                int d = (int) (coins.getDenominations().get(i) * 100);
                //Each denomination check to see if that denomination can be part of the change
                if (c >= d) {
                    // r is no of coins of that particular denomination
                    int r = (int) (c / d);
                    if (r > 0) {
                        // does vm have enough coins
                        if (cloned_coins.get(i) >= r) {
                            // Add those coins to change
                            result.add(r);
                            // update vm coins
                            cloned_coins.set(i, cloned_coins.get(i) - r);
                            //update change
                            c = c % d;
                        } else {
                            // if vm doesnt have enough coins, consider only the vm has
                            result.add(cloned_coins.get(i));
                            // update the remaining change accordingly
                            c = c - (cloned_coins.get(i) * d);
                            cloned_coins.set(i, 0);

                        }
                    } else if (r == 0) {
                        // In case of perfect change
                        int a = (int) (c / d);
                        // if vm has enogh coing
                        if (cloned_coins.get(i) >= a) {
                            result.add(a);
                            cloned_coins.set(i, cloned_coins.get(i) - a);
                            c = 0;
                        } else {
                            // if not conside only those coins vm has
                            result.add(cloned_coins.get(i));
                            c = c - (cloned_coins.get(i) * d);
                            cloned_coins.set(i, 0);

                        }
                    }

                } else {
                    //we can not give any change with this denomination
                    result.add(0);
                }
            }

        }
        if (c > 0) {
            //Vending Machine does n0t have enough coins. Do not update the user deposited coins
            System.out.println(c);
            throw new NoEnoughCoinsException(String.valueOf(change));
        }
        coins.setCoins(cloned_coins);
        return result;

    }

    public void updateCoins() {
        List<Integer> updatedCoins = getUserInputCoins();
        this.coins.setCoins(updatedCoins);

    }

    public void displayCoins(List<Integer> coins1) {
        coins.display(coins1);
    }

    public Coins getCoins() {
        return coins;
    }

    private List<Integer> getUserInputCoins() {
        List<Integer> updatedCoins = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the coins in a line, separated by space following the denomination above and press enter");
        System.out.print("[");
        for (double x : coins.getDenominations())
            System.out.print(formatter.format(x) + " ");
        System.out.print("]");
        System.out.println("");
        while (updatedCoins.size() != coins.getCoins().size()) {
            try {
                updatedCoins = Arrays.stream(in.nextLine().split("\\s+")).map(Integer::valueOf)
                        .collect(Collectors.toList());
                if (updatedCoins.size() != coins.getCoins().size())
                    System.out.println("Invalid Input. No of entries must be 7. Please reenter");
                if (updatedCoins.stream().anyMatch(x -> x < 0)) {
                    updatedCoins = new ArrayList<>();
                    System.out.println("Invalid Input. Please enter positive numbers");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter numbers.");
            }
        }
        return updatedCoins;
    }
}

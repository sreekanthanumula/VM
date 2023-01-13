package com.oracle.vm.controller;

import com.oracle.vm.entities.Coins;
import com.oracle.vm.entities.Inventory;
import com.oracle.vm.entities.VendingMachine;
import com.oracle.vm.exception.NoEnoughCoinsException;
import com.oracle.vm.exception.ProductNotFoundException;

import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class VMController {

    //Controls the Vending Machine by providing CMD line interface
    public static void main(String[] args) {
        //Initialize VM items
        Inventory inventory = new Inventory();

        //Initialize vm coins
        Coins coins = new Coins();
        System.out.println("######## Vending Machine Coins are initialized with default coins. ######");
        coins.display();
        System.out.println("############################################################");
        System.out.println("");

        VendingMachine vm = new VendingMachine(inventory, coins);

        int choice = -1;

        while (choice != 0) {


            System.out.println("Please enter 1 or 2 and press ENTER to perform respective operations and 0 to exit");
            System.out.println("1) Vending Machine Coin Update");
            System.out.println("2) Make Purchase");

            Scanner in = new Scanner(System.in);
            try {
                choice = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid input.");
            }

            if (choice == 2) {
                double price = 0.0;
                while (price < 0.01) {
                    try {
                        price = inventory.displayOptions();
                    } catch (ProductNotFoundException e) {
                        price = 0.0;
                        System.out.println(e.getMessage());

                    }
                }
                //
                double change = -0.1;
                while (change < 0) {

                    System.out.println("Product Price :: " + price);
                    double given = vm.takeUserInput();
                    change = Double.parseDouble(new DecimalFormat("0.00").format((given - price)));
                    System.out.println("Change :: " + (change));

                    if (change < 0)
                        System.out.println("You did not provide enough coins. Amount should be greater or equal to product price");
                }

                System.out.println("################# Vending Machine Coins #################");
                vm.getCoins().display();
                System.out.println("");
                try {

                    List<Integer> result = vm.returnChane(change);

                    System.out.println("################# Change  Coins given to user #################");

                    vm.displayCoins(result);
                    System.out.println("");
                    System.out.println("################# After depositing user coins and updated  Coins with Machine #################");

                    vm.getCoins().display();
                    System.out.println("");
                } catch (NoEnoughCoinsException e) {
                    System.out.println(e.getMessage());
                }
            } else if (choice == 1) {
                vm.updateCoins();
                vm.getCoins().display();

            }


        }

    }
}

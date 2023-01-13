package com.oracle.vm.exception;

public class NoEnoughCoinsException extends RuntimeException{
    public NoEnoughCoinsException(String change) {
        super("Vending Machine has no enough coins to provide the change :: " + change);
    }
}

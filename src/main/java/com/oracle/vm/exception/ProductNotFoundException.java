package com.oracle.vm.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String id) {
        super("Product with id :: " + id + " is not found in inventory");
    }
}

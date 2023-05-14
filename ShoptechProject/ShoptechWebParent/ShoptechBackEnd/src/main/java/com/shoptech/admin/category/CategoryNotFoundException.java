package com.shoptech.admin.category;

public class CategoryNotFoundException extends Exception{
    public CategoryNotFoundException(String message){
        super((message));
    }
}

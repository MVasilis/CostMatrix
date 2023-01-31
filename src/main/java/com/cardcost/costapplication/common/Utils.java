package com.cardcost.costapplication.common;

public abstract class Utils {

    public static String retrieveDesiredNumber(String number, int start, int end){
       return number.replaceAll("\\s", "").substring(start,end);
    }
}

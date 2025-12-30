package com.akko888.calculatorCore;

public class ExpressionFormatter{

    public static String formatting(double result){
        double epsilon = 1e-9;

        if(Math.abs(result - Math.round(result)) < epsilon){
            return String.valueOf((long) Math.round(result));
        }else{
            return String.valueOf(result);
        }
    }
    
}

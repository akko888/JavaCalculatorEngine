package com.akko888.calculatorCore;

import java.util.ArrayList;
import java.util.List;

public class ExpressionParser{
    
    public static double Parse(String expression){
        List<String> tokens = tokenizer(expression);
        tokens = solveParentheses(tokens);
        tokens = solveMulDiv(tokens);
        return solveAddSub(tokens);
    }

    public static List<String> tokenizer(String input){
        List<String> tokens = new ArrayList<>();
        StringBuilder number = new StringBuilder();

        for(int i = 0; i < input.length(); i++){
            char c = input.charAt(i);

            if(Character.isDigit(c) || c == '.'){
                number.append(c);
            }else{
                if(number.length() > 0){
                    tokens.add(number.toString());
                    number.setLength(0);
                }
                tokens.add(String.valueOf(c));
            }
        }
        
        if(number.length() > 0){
            tokens.add(number.toString());
        }

        return tokens;
    }

    public static List<String> solveParentheses(List<String> tokens){
        List<String> result = new ArrayList<>(tokens);

        while(result.contains("(")){
            
            int close = result.indexOf(")");
            if(close == -1)
                throw new IllegalArgumentException("Invalid Syntax");

            int open = -1;
            for(int i = close - 1; i >= 0; i--){
                if(result.get(i).equals("(")){
                    open = i;
                    break;
                }
            }

            if(open == -1)
                throw new IllegalArgumentException("Invalid Syntax");
            
            List<String> subList = new ArrayList<>(result.subList(open + 1, close));

            subList = solveMulDiv(subList);
            double subListSolve = solveAddSub(subList);

            for(int i = close; i >= open; i--){
                result.remove(i);
            }

            result.add(open, String.valueOf(subListSolve));
        }
        
        return result;
    }

    public static List<String> solveMulDiv(List<String> tokens){
        List<String> result = new ArrayList<>();

        for(int i = 0; i < tokens.size(); i++){
            String token = tokens.get(i);

            if(token.equals("*") || token.equals("/") || token.equals("%")){
                if(result.isEmpty() || i + 1 >= tokens.size()){
                    throw new IllegalArgumentException("Invalid syntax");
                }

                double left = Double.parseDouble(result.remove(result.size() - 1));
                double right = Double.parseDouble(tokens.get(++i));
 
                if((token.equals("/") || token.equals("%")) && right == 0){
                    throw new ArithmeticException("Division by Zero");
                }

                double value = 0;
                switch(token){
                    case "*" -> {value = left * right;}
                    case "/" -> {value = left / right;}
                    case "%" -> {value = left % right;}
                }
                result.add(String.valueOf(value));
            }else{
                result.add(token);
            }
        }

        return result;
    }

    public static double solveAddSub(List<String> tokens){

        if(tokens.isEmpty()){
            throw new IllegalArgumentException("Invalid syntax");
        }

        double result = Double.parseDouble(tokens.get(0));

        for(int i = 1; i < tokens.size(); i += 2){
            if(i + 1 >= tokens.size()){
                throw new IllegalArgumentException("Invalid syntax");
            }

            String operator = tokens.get(i);
            double value = Double.parseDouble(tokens.get(i + 1));

            if(operator.equals("+")){
                result += value;
            }else{
                result -= value;
            }
        }

        return result;
    }
}

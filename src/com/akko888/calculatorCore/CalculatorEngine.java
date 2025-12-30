package com.akko888.calculatorCore;

public class CalculatorEngine{
    
    private String expression;

    public void setExpression(String expression){
        this.expression = expression; 
    }
    
    public String getExpression(){
        return expression;
    }

    public String calculate(){
        try{
            double result = ExpressionParser.Parse(expression);
            return ExpressionFormatter.formatting(result);
        }catch(ArithmeticException | IllegalArgumentException e){
            return e.getMessage();
        }
    }

}

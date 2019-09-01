package main.java.WordDocumentConcatenator.StaticClasses;

public class UserDefinedOrder {
    private String[] DefinedOrder;
    public  UserDefinedOrder(String[] args){
        this.DefinedOrder = args;

    }
    public String[] userOrderRequest(){
        return this.DefinedOrder;
    }
}

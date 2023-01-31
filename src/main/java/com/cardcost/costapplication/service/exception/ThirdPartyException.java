package com.cardcost.costapplication.service.exception;

public class ThirdPartyException  extends RuntimeException{

    /**
     * Constructor to create Third Party Exception  with only one message
     * @param message
     */
    public ThirdPartyException(String message){
        super(message);
    }

    /**
     * Constructor to create Third Party exception with a message and the original exception
     * @param throwable
     * @param message
     */
    public ThirdPartyException(Throwable throwable, String message){
        super(message, throwable);
    }
}

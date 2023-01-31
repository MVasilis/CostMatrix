package com.cardcost.costapplication.service.exception;

/**
 * Service exception returned by the application in case any error occurs
 */
public class ServiceException extends RuntimeException{

    /**
     * Constructor to create service exception with only one message
     * @param message
     */
    public ServiceException(String message){
        super(message);
    }

    /**
     * Constructor to create service exception with a message and the original exception
     * @param throwable
     * @param message
     */
    public ServiceException(Throwable throwable, String message){
        super(message, throwable);
    }
}

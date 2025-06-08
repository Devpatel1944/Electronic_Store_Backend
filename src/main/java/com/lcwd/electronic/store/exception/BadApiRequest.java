package com.lcwd.electronic.store.exception;

public class BadApiRequest extends ResourceNotFoundException{

    public BadApiRequest(String msg){
        super(msg);
    }

    BadApiRequest(){
        super("This is Invalid Request");
    }
}

package com.example.jjlee3_feelsbook;

public class ExtendFeel extends Feeling {
    ExtendFeel(){
        super();
    }
    ExtendFeel(String message){
        super(message);
    }
    ExtendFeel(String message, String feel){
        super(message,feel);
    }
    @Override
    public Boolean isImportant(){
        return false;
    }
}

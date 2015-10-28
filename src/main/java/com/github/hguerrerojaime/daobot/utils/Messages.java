package com.github.hguerrerojaime.daobot.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

public final class Messages {
    
    private static final Properties props;
    
    static{
        
    
        InputStream propsFile = Messages.class
                .getClassLoader()
                .getResourceAsStream("messages.properties");
        
        props = new Properties();
        
        try {
            props.load(propsFile);
        }
        catch (IOException e) {}
        
    }
    
    public static String read(String code){
        return read(code,code);
    }
    
    public static String read(String code,Object[] args){
        return read(code,args,code);
    }
    
    public static String read(String code,String defaultMessage){
        return read(code,new Object[0],defaultMessage);
    }
    
    public static String read(String code,Object[] args,String defaultMessage){
        String prop = props.getProperty(code, defaultMessage);
        return MessageFormat.format(prop, args);
    }

}

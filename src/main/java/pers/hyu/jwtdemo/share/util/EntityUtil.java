package pers.hyu.jwtdemo.share.util;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Random;

@Component
public class EntityUtil {
    private final String STRINGS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * Generate an unique id
     * @param strLength the length of the id, the length must be greater than 13
     * @return the id
     */
    public String generateId(int strLength){
        Date date = new Date();
        String dateStr = String.valueOf(date.getTime());
        if(strLength - dateStr.length()<0){
            return dateStr;
        }
        return randomString(strLength - dateStr.length()) + dateStr;

    }

    
    public boolean isAnyFieldUnset(Object object) throws IllegalAccessException {
        for (Field f: object.getClass().getDeclaredFields()
             ) {
            f.setAccessible(true);
            if(StringUtils.isEmpty(f.get(object))){
                return true;
            }
        }
        return false;
    }

    private String randomString(int stringLength){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < stringLength; i++){
            stringBuilder.append(STRINGS.charAt(new Random().nextInt(STRINGS.length())));
        }
        return stringBuilder.toString();
    }

}

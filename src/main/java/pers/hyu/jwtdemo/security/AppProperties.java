package pers.hyu.jwtdemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * get the value from the property file
 */
//@Component
public class AppProperties {
    @Autowired
    private Environment environment;

    public String getTokenSecret(){
        return environment.getProperty("token.secret");
    }

}

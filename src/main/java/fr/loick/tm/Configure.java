/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author q13000412
 */
public class Configure {
    final static public boolean DEBUG = true;
    
    static public Twitter getTwitter(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        
        cb.setDebugEnabled(DEBUG)
                .setOAuthConsumerKey("p3eLju1lpSuNSutkZ47MmGNvo")
                .setOAuthConsumerSecret("84djqZ65G6ZWSHn6VZoSp0WwQFk5ee2bAIBvCAlxVzrU1PXMXz")
                .setOAuthAccessToken("569889211-umq6MLSDYlRE5TuiTw0LDRQxpS3pEBL5BI2StXRI")
                .setOAuthAccessTokenSecret("8i4akvPnVQqT4nSitWXNa4G6XtH3F6DykPLVqHkA62NO0");
        
        TwitterFactory factory = new TwitterFactory(cb.build());
        return factory.getInstance();
    }
}

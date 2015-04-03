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
 * @author q13000412
 */
public class Configure {
    final static public boolean DEBUG = true;

    static public Twitter getTwitter() {
        ConfigurationBuilder cb = new ConfigurationBuilder();

        cb.setDebugEnabled(DEBUG)
                .setOAuthConsumerKey("1mdI9BOPsBSE1lVv0PD0xMcRK")
                .setOAuthConsumerSecret("XLKIcggPXRRHdWafJTybRJ4WkC1ZE133TenxmxosoIhbBaKslt")
                .setOAuthAccessToken("3131062186-Ug6JlzUywgXiIzjzttjuEer14UuqjqsUnT6D2bF")
                .setOAuthAccessTokenSecret("ptSkKdqUfyu23s9zs0DRS3w3fTm2c8xSKcMZ1p9auVHxG");


        TwitterFactory factory = new TwitterFactory(cb.build());
        return factory.getInstance();
    }
}

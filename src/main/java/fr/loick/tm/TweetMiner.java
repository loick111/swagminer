package fr.loick.tm;

import fr.loick.tm.export.ConsoleExporter;
import fr.loick.tm.util.Strings;
import twitter4j.TwitterException;

/**
 * Created by loick on 01/04/15.
 */
public class TweetMiner {
    public static void main(String[] args) throws TwitterException {
        System.out.println("Hello world !");
        
        TweetFetcher tf = new TweetFetcher(Configure.getTwitter());
        tf.addExporter(new ConsoleExporter());
        tf.export();
    }
}

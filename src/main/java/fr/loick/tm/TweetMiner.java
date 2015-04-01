package fr.loick.tm;

import fr.loick.tm.export.CSVExporter;
import fr.loick.tm.fetch.HomeTimelineImporter;
import fr.loick.tm.fetch.TweetFetcher;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import twitter4j.TwitterException;

/**
 * Created by loick on 01/04/15.
 */
public class TweetMiner {
    public static void main(String[] args) throws TwitterException, InterruptedException, IOException {
        System.out.println("Hello world !");
        
        TweetFetcher tf = new TweetFetcher(Configure.getTwitter());
        //tf.addExporter(new ConsoleExporter());
        tf.addExporter(new CSVExporter(new File("tweets_" + new Date() + ".csv")));
        
        int nbTweets = 0;
        for(int i = 1; nbTweets < 20000; ++i) {
            try{
                nbTweets += tf.export(new HomeTimelineImporter(i));
                System.out.println("Nombre de tweets : " + nbTweets);
                Thread.sleep(3000);
            }catch(TwitterException e){
                System.out.println("Error : " + e.getErrorMessage());
                int time = e.getRateLimitStatus().getSecondsUntilReset() * 1000;
                System.out.println("Retry in " + time + "s");
                Thread.sleep(time);
            }
        }
    }
}

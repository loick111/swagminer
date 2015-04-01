package fr.loick.tm;

import fr.loick.tm.export.CSVExporter;
import fr.loick.tm.fetch.HomeTimelineImporter;
import fr.loick.tm.fetch.TweetFetcher;
import fr.loick.tm.export.ConsoleExporter;
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
        tf.addExporter(new ConsoleExporter());
        tf.addExporter(new CSVExporter(new File("tweets_" + new Date() + ".csv")));
        for(int i=1; i<10; ++i) {
            tf.export(new HomeTimelineImporter(i));
            Thread.sleep(500);
        }
    }
}

package fr.loick.tm;

import fr.loick.tm.export.CSVExporter;
import fr.loick.tm.fetch.QueryImporter;
import fr.loick.tm.fetch.TweetFetcher;
import twitter4j.TwitterException;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by loick on 01/04/15.
 */
public class TweetMiner {
    public static void main(String[] args) throws TwitterException, InterruptedException, IOException {
        TweetFetcher tf = new TweetFetcher(Configure.getTwitter());
        //tf.addExporter(new ConsoleExporter());
        tf.addExporter(new CSVExporter(new File("tweets_" + new Date() + ".csv")));

        QueryImporter importer = new QueryImporter(new String[]{"#DIY", "#4chan", "#couscous", "#tajine", "#jesuischarlie", "#hollande", "#swag", "#wtf", "#valls", "#dsk", "#ps", "#fn", "#ump", "#syrie", "#yolo"});
        int nbTweets = 0;
        while (nbTweets < 20000) {
            try {
                nbTweets += tf.export(importer);
                System.out.println("Nombre de tweets : " + nbTweets);
                Thread.sleep(3000);
            } catch (TwitterException e) {
                System.out.println("Error : " + e.getErrorMessage());
                int time = e.getRateLimitStatus().getSecondsUntilReset();
                System.out.println("Retry in " + time + "s");
                Thread.sleep(time * 1000);
            }
        }
    }
}

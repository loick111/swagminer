/**
 * Created by loic on 01/04/15.
 */

import fr.loick.tm.Configure;
import fr.loick.tm.export.TransExporter;
import fr.loick.tm.fetch.QueryImporter;
import fr.loick.tm.fetch.TweetFetcher;
import org.junit.Test;
import twitter4j.TwitterException;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class TransExporterTest {
    @Test
    public void testTrans() {
        TweetFetcher tf = new TweetFetcher(Configure.getTwitter());
        //tf.addExporter(new ConsoleExporter());
        try {
            tf.addExporter(new TransExporter(new File("tweets_" + new Date() + ".trans")));
            QueryImporter importer = new QueryImporter(new String[]{"#DIY", "#4chan", "#couscous", "#tajine", "#jesuischarlie", "#hollande", "#swag", "#wtf", "#valls", "#dsk", "#ps", "#fn", "#ump", "#syrie", "#yolo"});
            int nbTweets = 0;
            while (nbTweets < 100) {
                try {
                    nbTweets += tf.export(importer);
                    System.out.println("Nombre de tweets : " + nbTweets);
                    Thread.sleep(3000);
                } catch (TwitterException e) {
                    if (e.getRateLimitStatus().getSecondsUntilReset() > 0) {
                        System.out.println("Error : " + e.getErrorMessage());
                        int time = e.getRateLimitStatus().getSecondsUntilReset();
                        System.out.println("Retry in " + time + "s");
                        Thread.sleep(time * 1000);
                    }
                }
            }
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}
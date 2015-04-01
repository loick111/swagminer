package converter;

import fr.loick.tm.Configure;
import fr.loick.tm.converter.MapConverter;
import fr.loick.tm.converter.TransExporter;
import fr.loick.tm.export.Exporter;
import fr.loick.tm.fetch.QueryImporter;
import fr.loick.tm.fetch.TweetFetcher;
import org.junit.Test;
import twitter4j.TwitterException;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by loic on 01/04/15.
 */

public class MapConverterTest {
    @Test
    public void testMapConvert() {
        TweetFetcher tf = new TweetFetcher(Configure.getTwitter());
        //tf.addExporter(new ConsoleExporter());
        try {
            Exporter exporter = new TransExporter(new File("tweets_" + new Date() + ".trans"));
            tf.addExporter(exporter);

            QueryImporter importer = new QueryImporter(new String[]{"#DIY", "#4chan", "#couscous", "#tajine", "#jesuischarlie", "#hollande", "#swag", "#wtf", "#valls", "#dsk", "#ps", "#fn", "#ump", "#syrie", "#yolo"});
            int nbTweets = 0;
            while (nbTweets < 200) {
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
            MapConverter a = new MapConverter(((TransExporter) exporter).getAssociation(), new File("test.txt"));
            a.convert();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}

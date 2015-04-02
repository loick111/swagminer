package java_converter;

import fr.loick.tm.Configure;
import fr.loick.tm.converter.DicoToMapConverter;
import fr.loick.tm.converter.MapToDicoConverter;
import fr.loick.tm.converter.TransToCSVConverter;
import fr.loick.tm.export.Exporter;
import fr.loick.tm.export.TransExporter;
import fr.loick.tm.fetch.QueryImporter;
import fr.loick.tm.fetch.TweetFetcher;
import org.junit.Test;
import twitter4j.TwitterException;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by loic on 02/04/15.
 */
public class TransToCSVTest {
    @Test
    public void testTransToCSV(){

        TweetFetcher tf = new TweetFetcher(Configure.getTwitter());
        //tf.addExporter(new ConsoleExporter());
        try {
            File trans = new File("tweets_" + new Date() + ".trans");
            Exporter exporter = new TransExporter(trans);
            tf.addExporter(exporter);

            QueryImporter importer = new QueryImporter(new String[]{" #TPMP"});
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
            File tmp = new File("tweets_" + new Date() + ".trans.dico");
            MapToDicoConverter a = new MapToDicoConverter(((TransExporter) exporter).getAssociation(),tmp );
            a.convert();
            DicoToMapConverter b = new DicoToMapConverter(tmp);
            b.convert();
            TransToCSVConverter c = new TransToCSVConverter(trans,tmp);
            c.convert();

        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}

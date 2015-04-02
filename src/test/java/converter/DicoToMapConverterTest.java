package converter;

import fr.loick.tm.Configure;
import fr.loick.tm.converter.DicoToMapConverter;
import fr.loick.tm.converter.MapToDicoConverter;
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
 * Created by Ploic on 02/04/15.
 */
public class DicoToMapConverterTest {

    @Test
    public void testDicoToMap(){

        TweetFetcher tf = new TweetFetcher(Configure.getTwitter());
        //tf.addExporter(new ConsoleExporter());
        try {
            Exporter exporter = new TransExporter(new File("tweets_" + new Date() + ".trans"));
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
            MapToDicoConverter a = new MapToDicoConverter(((TransExporter) exporter).getAssociation(), new File("tweets_" + new Date() + ".trans.dico"));
            a.convert();
            DicoToMapConverter b = new DicoToMapConverter(new File("tweets_" + new Date() + ".trans.dico"));
            b.convert();
            System.out.println(b.getMap());

        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}

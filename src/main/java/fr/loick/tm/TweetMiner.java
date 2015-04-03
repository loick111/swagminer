package fr.loick.tm;

import fr.loick.tm.apriori.APriori;
import fr.loick.tm.apriori.FileExporter;
import fr.loick.tm.apriori.TransDB;
import twitter4j.TwitterException;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * Created by loick on 01/04/15.
 */
public class TweetMiner {
    public static void main(String[] args) throws TwitterException, InterruptedException, IOException {
        /*TweetFetcher tf = new TweetFetcher(Configure.getTwitter());
        //tf.addExporter(new ConsoleExporter());
        tf.addExporter(new CSVExporter(new File("tweets_" + new Date() + ".csv")));
        tf.addExporter(new TransExporter(new File("tweets_" + new Date() + ".trans")));

        QueryImporter importer = new QueryImporter(new String[]{"#4chan", "#couscous", "#tajine", "#jesuischarlie", "#hollande", "#swag", "#wtf", "#valls", "#dsk", "#ps", "#fn", "#ump", "#syrie", "#yolo"});
        int nbTweets = 0;
        while (nbTweets < 1000000) {
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
        }*/
        
        FileExporter<Integer> exporter = new FileExporter<>(new File("apriori"));
        
        TransDB db = new TransDB(new File("tweets_Thu Apr 02 11:09:42 CEST 2015.trans"));
        APriori<Integer> algo = new APriori<>(db, 300, (Integer o1, Integer o2) -> {
            if(o1 == null)
                return -1;
            
            if(o2 == null)
                return 1;
            
            return o1 - o2;
        });
        algo.addExporter(exporter);
        algo.perform();
        exporter.close();
    }
}

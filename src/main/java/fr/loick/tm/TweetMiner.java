package fr.loick.tm;

import fr.loick.tm.apriori.APriori;
import fr.loick.tm.apriori.FileExporter;
import fr.loick.tm.apriori.TransDB;
import fr.loick.tm.assoc.AssocBuilder;
import fr.loick.tm.assoc.CSVFileExporter;
import fr.loick.tm.converter.AssocTranslatorConverter;
import fr.loick.tm.converter.DicoToMapConverter;
import fr.loick.tm.converter.MapToDicoConverter;
import fr.loick.tm.export.CSVExporter;
import fr.loick.tm.export.TransExporter;
import fr.loick.tm.fetch.QueryImporter;
import fr.loick.tm.fetch.TweetFetcher;
import fr.loick.tm.util.Strings;
import java.io.BufferedReader;
import twitter4j.TwitterException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by loick on 01/04/15.
 */
public class TweetMiner {
    public static void main(String[] args) throws TwitterException, InterruptedException, IOException {
//        System.out.println("==== loading tweets ====");
//        
//        TweetFetcher tf = new TweetFetcher(Configure.getTwitter());
//        String baseName = "tweets_" + new Date();
//        tf.addExporter(new CSVExporter(new File(baseName + ".csv")));
//        
//        TransExporter transExporter = new TransExporter(new File(baseName + ".trans"));
//        tf.addExporter(transExporter);
//
//        QueryImporter importer = new QueryImporter(new String[]{"#couscous", "#tajine", "#hollande", "#swag", "#valls", "#dsk", "#ps", "#fn", "#ump", "#syrie"});
//        int nbTweets = 0;
//        while (nbTweets < 10000) {
//            try {
//                nbTweets += tf.export(importer);
//                System.out.println("Nombre de tweets : " + nbTweets);
//                Thread.sleep(1000);
//            } catch (TwitterException e) {
//                if (e.getRateLimitStatus().getSecondsUntilReset() > 0) {
//                    System.out.println("Error : " + e.getErrorMessage());
//                    int time = e.getRateLimitStatus().getSecondsUntilReset();
//                    System.out.println("Retry in " + time + "s");
//                    Thread.sleep(time * 1000);
//                }
//            }
//        }
//        
//        System.out.println("==== creating dico ====");
//        MapToDicoConverter converter = new MapToDicoConverter(transExporter.getAssociation(), new File(baseName + ".dico"));
//        converter.convert();
//        
//        System.out.println("==== reversing dico ====");
//        Map<Integer, String> reverseDico = new HashMap<>();
//        
//        for(Map.Entry<String, Integer> entry : transExporter.getAssociation().entrySet()){
//            reverseDico.put(entry.getValue(), entry.getKey());
//        }
//        
//        System.out.println("==== APriori ====");
//        FileExporter<Integer> exporter = new FileExporter<>(new File(baseName + ".ap"));
//        
//        TransDB db = new TransDB(new File(baseName + ".trans"));
//        APriori<Integer> algo = new APriori<>(db, .05, (Integer o1, Integer o2) -> {
//            if(o1 == null)
//                return -1;
//            
//            if(o2 == null)
//                return 1;
//            
//            return o1 - o2;
//        });
//        algo.addExporter(exporter);
//        
//        
//        final Map<Set<Integer>, Double> frequencies = new HashMap<>();
//        algo.addExporter((s, f) -> {
//            frequencies.put(s, f);
//        });
//        algo.perform();
//        exporter.close();
        
        System.out.println("==== Loading frequencies ====");
        String baseName = "tweets_Fri Apr 03 16:23:24 CEST 2015";
        
        Map<Set<Integer>, Double> frequencies = new HashMap<>();
        
        try(BufferedReader br = new BufferedReader(new FileReader(baseName + ".ap"))){
            for(String line = br.readLine(); line != null; line = br.readLine()){
                String[] data = Strings.split(line, ";", 2);
                Set<Integer> row = new HashSet<>();
                for(String i : Strings.split(data[0], ",")){
                    row.add(Integer.parseInt(i));
                }
                frequencies.put(row, Double.parseDouble(data[1]));
            }
        }
        
        System.out.println("==== Loading dico ====");
        DicoToMapConverter converter = new DicoToMapConverter(new File(baseName + ".dico"));
        converter.convert();
        Map<Integer, String> reverseDico = converter.getMap();
        
        System.out.println("==== Searching assoc ====");
        CSVFileExporter<Integer> assocExporter = new CSVFileExporter<>(new File(baseName + ".assoc"));
        AssocBuilder<Integer> assocBuilder = new AssocBuilder<>(frequencies);
        assocBuilder.addExporter(assocExporter);
        assocBuilder.buildAssoc(.055, 1);
        assocExporter.close();
        
        System.out.println("==== Translating assoc ====");
        AssocTranslatorConverter atc = new AssocTranslatorConverter(new File(baseName + ".assoc"), new File(baseName + ".words"), reverseDico);
        atc.convert();
    }
}

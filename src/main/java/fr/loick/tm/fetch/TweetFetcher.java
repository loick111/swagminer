/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.fetch;

import fr.loick.tm.export.Exporter;
import java.util.ArrayList;
import java.util.Collection;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 *
 * @author q13000412
 */
public class TweetFetcher {
    final private Twitter twitter;
    final private Collection<Exporter> exporters = new ArrayList<>();

    public TweetFetcher(Twitter twitter) {
        this.twitter = twitter;
    }
    
    public void addExporter(Exporter exporter){
        exporters.add(exporter);
    }
    
    public void removeExporter(Exporter exporter){
        exporters.remove(exporter);
    }
    
    public void export(TwitterImporter importer) throws TwitterException{
        for(Status status : importer.importStatus(twitter)){
            for (Exporter exporter : exporters) {
                exporter.export(status);
            }
        }
    }
}

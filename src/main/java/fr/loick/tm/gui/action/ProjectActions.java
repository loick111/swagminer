/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.gui.action;

import fr.loick.tm.Configure;
import fr.loick.tm.fetch.QueryImporter;
import fr.loick.tm.fetch.TweetFetcher;
import fr.loick.tm.fetch.export.CSVExporter;
import fr.loick.tm.gui.MainFrame;
import fr.loick.tm.gui.NewProjet;
import fr.loick.tm.gui.ProjectGui;
import fr.loick.tm.gui.TweetsLoader;
import fr.loick.tm.gui.model.Project;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import twitter4j.TwitterException;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ProjectActions {
    static public void newProject(ActionEvent e){
        final NewProjet np = new NewProjet(MainFrame.getInstance());
        
        if(!np.isOk())
            return;
        
        final Project project = np.getProject();
        
        project.getFolder().delete();
        project.getFolder().mkdir();
        
        File csv = new File(project.getName() + "/tweets.csv");
        csv.delete();
        
        final TweetFetcher fecther = new TweetFetcher(Configure.getTwitter());
        final QueryImporter importer = new QueryImporter(np.getQuery());
        
        try {
            fecther.addExporter(new CSVExporter(csv));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(MainFrame.getInstance(), ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        
        final TweetsLoader loader = new TweetsLoader(MainFrame.getInstance(), np.getNbTweets());
        
        final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        Runnable task = new Runnable(){
            private int nbTweets = 0;
            @Override
            public void run() {
                try {
                    nbTweets += fecther.export(importer);
                    loader.setProgress(nbTweets);
                    
                    if(nbTweets < np.getNbTweets()){
                        service.schedule(this, 1500, TimeUnit.MILLISECONDS);
                    }else{
                        loader.dispose();
                        ProjectGui gui = new ProjectGui(project);
                        MainFrame.getInstance().addProject(gui);
                    }
                } catch (TwitterException ex) {
                    int time = ex.getRateLimitStatus().getSecondsUntilReset();
                    JOptionPane.showMessageDialog(MainFrame.getInstance(), "Ban pour " + time + "sec", "Twitter", JOptionPane.WARNING_MESSAGE);
                    service.schedule(this, time, TimeUnit.SECONDS);
                }
            }
        };
        task.run();
    }
}

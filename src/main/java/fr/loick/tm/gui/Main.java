/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.gui;

import fr.loick.tm.*;
import fr.loick.tm.apriori.APriori;
import fr.loick.tm.apriori.FileExporter;
import fr.loick.tm.apriori.TransDB;
import fr.loick.tm.assoc.AssocBuilder;
import fr.loick.tm.assoc.CSVFileExporter;
import fr.loick.tm.assoc.TranslateFileExporter;
import fr.loick.tm.fetch.Dico;
import fr.loick.tm.fetch.QueryImporter;
import fr.loick.tm.fetch.TweetFetcher;
import fr.loick.tm.fetch.export.CSVExporter;
import fr.loick.tm.fetch.export.TransExporter;
import fr.loick.tm.util.Strings;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.JFrame;
import twitter4j.TwitterException;


/**
 *
 * @author loic
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates Main
     */
    public Main() {
        initComponents();
    }

   
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelAction = new javax.swing.JPanel();
        actionAvailable = new javax.swing.JLabel();
        find_rule = new javax.swing.JButton();
        sort_conf = new javax.swing.JButton();
        sort_lift = new javax.swing.JButton();
        sort_freq = new javax.swing.JButton();
        action = new javax.swing.JLabel();
        scrollArea = new javax.swing.JScrollPane();
        showArea = new javax.swing.JTextArea();
        menu_bar = new javax.swing.JMenuBar();
        Search = new javax.swing.JMenu();
        NewSearch = new javax.swing.JMenuItem();
        other = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SwagMiner v1");
        setResizable(false);

        actionAvailable.setText("Action disponnible");

        find_rule.setText("Rcehercher un règle");

        sort_conf.setText("Trier par confiance");

        sort_lift.setText("Trier par lift");

        sort_freq.setText("Trier par fréquence");

        javax.swing.GroupLayout PanelActionLayout = new javax.swing.GroupLayout(PanelAction);
        PanelAction.setLayout(PanelActionLayout);
        PanelActionLayout.setHorizontalGroup(
            PanelActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelActionLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(actionAvailable)
                .addGap(224, 224, 224))
            .addGroup(PanelActionLayout.createSequentialGroup()
                .addComponent(sort_lift, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sort_conf, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(PanelActionLayout.createSequentialGroup()
                .addComponent(find_rule, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sort_freq, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE))
        );
        PanelActionLayout.setVerticalGroup(
            PanelActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelActionLayout.createSequentialGroup()
                .addComponent(actionAvailable, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(find_rule)
                    .addComponent(sort_freq))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sort_lift)
                    .addComponent(sort_conf))
                .addGap(0, 14, Short.MAX_VALUE))
        );

        getContentPane().add(PanelAction, java.awt.BorderLayout.PAGE_END);
        PanelAction.setVisible(false);

        action.setText("Action en cours :");
        getContentPane().add(action, java.awt.BorderLayout.PAGE_START);

        scrollArea.setPreferredSize(new java.awt.Dimension(562, 300));

        showArea.setEditable(false);
        showArea.setColumns(20);
        showArea.setRows(5);
        showArea.setText("== Aucune action lancer ==");
        showArea.setMinimumSize(new java.awt.Dimension(562, 562));
        showArea.setPreferredSize(new java.awt.Dimension(558, 200));
        scrollArea.setViewportView(showArea);

        getContentPane().add(scrollArea, java.awt.BorderLayout.LINE_END);

        Search.setText("Recherche");

        NewSearch.setText("Nouvelle Recherche");
        NewSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewSearchActionPerformed(evt);
            }
        });
        Search.add(NewSearch);

        menu_bar.add(Search);

        other.setText("Autre");
        menu_bar.add(other);

        setJMenuBar(menu_bar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NewSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewSearchActionPerformed
        Nouvelle_recherche nouv = new Nouvelle_recherche();
        nouv.setVisible(true);
        
        JFrame tmp = this;
        
        nouv.addWindowListener(new WindowAdapter(){
            
            @Override
            public void windowClosed(WindowEvent e){
                try{
                    
                    showArea.append("\n==== Creating folder ====");
                    showArea.update(showArea.getGraphics());
                    DateFormat df = new SimpleDateFormat("MM_dd_yyyy_HH:mm:ss");
                    Date today = Calendar.getInstance().getTime();     
                    String basename = df.format(today);
                    new File(basename).mkdir();

                    showArea.append("\n==== Fectching Data ===");
                    showArea.update(showArea.getGraphics());
                    TweetFetcher tf = new TweetFetcher(Configure.getTwitter());
                    String base_tweet = "tweets_" + basename;
                    tf.addExporter(new CSVExporter(new File(basename + '/' + base_tweet + ".csv")));
                    
                    Dico dico = new Dico(new File(basename + '/' + base_tweet + ".dico"));
                    TransExporter transExporter = new TransExporter(new File(basename + '/' + base_tweet+ ".trans"), dico);
                    tf.addExporter(transExporter);
            
                    QueryImporter importer = new QueryImporter(nouv.getHash());
                    int nbTweets = 0;
                    while (nbTweets < nouv.getNbTweet()) {
                        try {
                            nbTweets += tf.export(importer);
                            showArea.append("\n==== Nombre de tweets : " + nbTweets + " ====");
                            showArea.update(showArea.getGraphics());
                            Thread.sleep(1500);
                        } catch (TwitterException exp) {
                            if (exp.getRateLimitStatus().getSecondsUntilReset() > 0) {
                                showArea.append("\n==!= Error : " + exp.getErrorMessage());
                                showArea.update(showArea.getGraphics());
                                int time = exp.getRateLimitStatus().getSecondsUntilReset();
                                showArea.append("\n==!= Retry in " + time + "s");
                                showArea.update(showArea.getGraphics());
                                Thread.sleep(time * 1000);
                            }
                        }
                    }
                    
                    showArea.append("\n==== Saving dico ====");
                    showArea.update(showArea.getGraphics());
                    dico.save();

                    showArea.append("\n==== loading dico ====");
                    showArea.update(showArea.getGraphics());
                    dico.load();

                    showArea.append("\n==== APriori ====");
                    showArea.update(showArea.getGraphics());
                    FileExporter<Integer> exporter = new FileExporter<>(new File(basename + '/' + base_tweet + ".ap"));

                    TransDB db = new TransDB(new File(basename + '/' + base_tweet + ".trans"));
                    APriori<Integer> algo = new APriori<>(db, .035, (Integer o1, Integer o2) -> {
                        if(o1 == null)
                            return -1;

                        if(o2 == null)
                            return 1;

                        return o1 - o2;
                    });
                    algo.addExporter(exporter);


                    final Map<Set<Integer>, Double> frequencies = new HashMap<>();
                    algo.addExporter((s, f) -> {
                        frequencies.put(s, f);
                    });
                    algo.perform();
                    exporter.close();

                    showArea.append("\n==== Loading frequencies ====");
                    showArea.update(showArea.getGraphics());
                  
                    
                    try(BufferedReader br = new BufferedReader(new FileReader(basename + '/' + base_tweet + ".ap"))){
                        for(String line = br.readLine(); line != null; line = br.readLine()){
                            String[] data = Strings.split(line, ";", 2);
                            Set<Integer> row = new HashSet<>();
                            for(String i : Strings.split(data[0], ",")){
                                row.add(Integer.parseInt(i));
                            }
                            frequencies.put(row, Double.parseDouble(data[1]));
                        }
                    }
                   

                    showArea.append("\n==== Searching assoc ====");
                    showArea.update(showArea.getGraphics());
                    TranslateFileExporter tfe = new TranslateFileExporter(new File(basename + '/' + base_tweet + ".assoc"), dico);
                    AssocBuilder<Integer> assocBuilder = new AssocBuilder<>(frequencies);
                    assocBuilder.addExporter(tfe);
                    assocBuilder.buildAssoc(0, 1);
                    tfe.close();
                    
                    PanelAction.setVisible(true);
                    tmp.pack();
                    tmp.update(tmp.getGraphics());
                    
                }catch(Exception exc){
                    exc.printStackTrace();
                }
                
            }
        });
    }//GEN-LAST:event_NewSearchActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem NewSearch;
    private javax.swing.JPanel PanelAction;
    private javax.swing.JMenu Search;
    private javax.swing.JLabel action;
    private javax.swing.JLabel actionAvailable;
    private javax.swing.JButton find_rule;
    private javax.swing.JMenuBar menu_bar;
    private javax.swing.JMenu other;
    private javax.swing.JScrollPane scrollArea;
    private javax.swing.JTextArea showArea;
    private javax.swing.JButton sort_conf;
    private javax.swing.JButton sort_freq;
    private javax.swing.JButton sort_lift;
    // End of variables declaration//GEN-END:variables
}

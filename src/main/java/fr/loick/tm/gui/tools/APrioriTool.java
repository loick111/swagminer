/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.gui.tools;

import fr.loick.tm.apriori.APriori;
import fr.loick.tm.apriori.FileExporter;
import fr.loick.tm.apriori.TransDB;
import fr.loick.tm.apriori.WordsExporter;
import fr.loick.tm.fetch.Cleaner;
import fr.loick.tm.gui.MainFrame;
import fr.loick.tm.gui.model.Project;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.*;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class APrioriTool extends ProjectTool{
    final private Project project;
    private Thread aprioriThread;
    private PrintStream consoleStream;
    private JTextArea console;
    private JButton startButton;

    public APrioriTool(Project project) {
        this.project = project;
        form();
        console();
    }
    
    private void console(){
        console = new JTextArea();
        console.setForeground(Color.GRAY);
        console.setFont(console.getFont().deriveFont(Font.BOLD));
        
        console.setEditable(false);
        add(console, BorderLayout.CENTER);
        
        consoleStream = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                console.append((char)(b) + "");
            }
        });
    }
    
    public void redirectOut(){
        console.setText("");
        System.setOut(consoleStream);
    }
    
    private void form(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        
        panel.add(new JLabel("MinFreq"));
        
        final JSpinner minFreq = new JSpinner(new SpinnerNumberModel(.01, 0, 1, .001));
        panel.add(minFreq);
        
        startButton = new JButton("Afficher");
        panel.add(startButton);
        startButton.addActionListener((e) -> {
            redirectOut();

            if(!loadAPriori((double) minFreq.getValue())){
                if (Cleaner.getINSTANCE().isCleaned()){
                    JDialog box = new JDialog(MainFrame.getInstance());

                    box.setTitle("Cleaned ?");
                    box.setModal(true);

                    JPanel form = new JPanel(new GridLayout(0, 2));
                    box.add(form, BorderLayout.CENTER);

                    form.add(new JLabel("Utilisé le fichier clean ?"));

                    JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                    box.add(buttons, BorderLayout.SOUTH);

                    JButton okButton = new JButton("Oui");
                    JButton noButton = new JButton("Non");
                    okButton.addActionListener(e1 -> {
                        launchAPriori((double) minFreq.getValue(),"cleaned_tweets.csv");
                        box.dispose();
                    });

                    noButton.addActionListener(e2 -> {
                        launchAPriori((double) minFreq.getValue(),"tweets.csv");
                        box.dispose();
                    });

                    buttons.add(okButton);
                    buttons.add(noButton);

                    box.pack();
                    box.setLocationRelativeTo(MainFrame.getInstance());
                    box.setVisible(true);
                }else
                    launchAPriori((double) minFreq.getValue(),"tweets.csv");

            }
        });
        
        add(panel, BorderLayout.NORTH);
    }
    
    private void launchAPriori(double minFreq,String file){
        startButton.setEnabled(false);
        aprioriThread = new Thread(() -> {
            APriori<Integer> apriori = new APriori<>(new TransDB(new File(project.getName() + "/"+file)),  minFreq, (a, b) -> {
                if(a == null)
                    return -1;

                if(b == null)
                    return 1;

                return a - b;
            });

            try {
                WordsExporter we = new WordsExporter(new File(project.getName() + "/words_" + minFreq + ".ap"), project.getDico());
                FileExporter<Integer> fe = new FileExporter<>(new File(project.getName() + "/int_" + minFreq + ".ap"));
                
                apriori.addExporter(fe);
                apriori.addExporter(we);

                System.out.println("==== Lancement d'APriori ====");
                apriori.perform();
                System.out.println("==== Fin APriori ====");

                we.close();
                fe.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(MainFrame.getInstance(), ex, "Erreur", JOptionPane.ERROR_MESSAGE);
            }finally{
                startButton.setEnabled(true);
            }
        });
        
        aprioriThread.start();
    }
    
    private boolean loadAPriori(double minFreq){
        File file = new File(project.getName() + "/words_" + minFreq + ".ap");
        
        if(!file.exists())
            return false;
        
        return true;
    }

    @Override
    public String getTitle() {
        return "APriori";
    }
    
}

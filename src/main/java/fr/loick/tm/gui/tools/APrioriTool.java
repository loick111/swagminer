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
import fr.loick.tm.gui.MainFrame;
import fr.loick.tm.gui.model.Project;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class APrioriTool extends ProjectTool{
    final private Project project;
    private Thread aprioriThread;
    private PrintStream consoleStream;
    private JTextArea console;

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
        
        JButton button = new JButton("Afficher");
        panel.add(button);
        button.addActionListener((e) -> {
            redirectOut();
            if(!loadAPriori((double) minFreq.getValue())){
                launchAPriori((double) minFreq.getValue());
            }
        });
        
        JButton stop = new JButton("Stop");
        stop.addActionListener((e) -> {
            if(aprioriThread != null && !aprioriThread.isInterrupted()){
                aprioriThread.interrupt();
                System.out.println("\n/!\\ APriori arrete ! /!\\");
            }
            
            aprioriThread = null;
        });
        panel.add(stop);
        
        add(panel, BorderLayout.NORTH);
    }
    
    private void launchAPriori(double minFreq){
        aprioriThread = new Thread(() -> {
            APriori<Integer> apriori = new APriori<>(new TransDB(new File(project.getName() + "/tweets.trans")),  minFreq, (a, b) -> {
                if(a == null)
                    return -1;

                if(b == null)
                    return 1;

                return a - b;
            });

            try {
                WordsExporter we = new WordsExporter(new File(project.getName() + "/words_" + minFreq + ".ap"), project.getDico());
                FileExporter<Integer> fe = new FileExporter<>(new File(project.getName() + "/int_" + minFreq + ".ap"));

                System.out.println("==== Lancement d'APriori ====");
                apriori.perform();
                System.out.println("==== Fin APriori ====");

                we.close();
                fe.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(MainFrame.getInstance(), ex, "Erreur", JOptionPane.ERROR_MESSAGE);
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

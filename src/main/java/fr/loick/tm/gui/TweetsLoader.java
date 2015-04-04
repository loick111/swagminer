/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.gui;

import java.awt.BorderLayout;
import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class TweetsLoader extends JDialog{
    final private JProgressBar bar;
    final private JLabel label = new JLabel();
    final private int nbTweets;

    public TweetsLoader(Frame owner, int nbTweets) {
        super(owner);
        setTitle("Chargement des tweets...");
        setAlwaysOnTop(true);
        setUndecorated(true);
        setModal(false);
        this.nbTweets = nbTweets;
        
        add(new JLabel("Chargement des tweets..."), BorderLayout.NORTH);
        
        bar = new JProgressBar(0, 100);
        add(bar, BorderLayout.CENTER);
        
        add(label, BorderLayout.SOUTH);
        setProgress(0);
        
        pack();
        setLocationRelativeTo(owner);
        setVisible(true);
    }
    
    public void setProgress(int nb){
        int percent = nb * 100 / nbTweets;
        if(percent > 100)
            percent = 100;
        
        bar.setValue(percent);
        label.setText(nb + " / " + nbTweets);
    }
}

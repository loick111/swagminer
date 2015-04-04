/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.gui;

import fr.loick.tm.gui.model.Project;
import fr.loick.tm.util.Strings;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.io.File;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class NewProjet extends JDialog{
    final private JTextField name = new JTextField(new Date().toString());
    final private JTextField query = new JTextField();
    final private JSpinner nbTweets = new JSpinner(new SpinnerNumberModel(10000, 100, 500000, 100));
    
    private boolean ok = false;

    public NewProjet(Frame owner) {
        super(owner);
        setTitle("Nouveau projet");
        setModal(true);
        
        JPanel form = new JPanel(new GridLayout(0, 2));
        add(form, BorderLayout.CENTER);
        
        form.add(new JLabel("Nom"));
        form.add(name);
        
        form.add(new JLabel("requêtes (séparés par des espaces)"));
        form.add(query);
        
        form.add(new JLabel("Nombre de tweets"));
        form.add(nbTweets);
        
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        add(buttons, BorderLayout.SOUTH);
        
        JButton okButton = new JButton("Ok");
        okButton.addActionListener((e) -> {
            ok = true;
            dispose();
        });
        buttons.add(okButton);
        
        pack();
        setLocationRelativeTo(owner);
        setVisible(true);
    }
    
    public Project getProject(){
        return new Project(name.getText(), new File(name.getText()));
    }
    
    public String[] getQuery(){
        return Strings.split(query.getText(), " ");
    }
    
    public int getNbTweets(){
        return (int) nbTweets.getValue();
    }

    public boolean isOk() {
        return ok;
    }
}

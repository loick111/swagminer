/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.gui;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class MainFrame extends JFrame{
    final private static MainFrame instance = new MainFrame();
    final private JTabbedPane tabbedPane;

    private MainFrame() throws HeadlessException {
        super("TweetMiner");
        
        setJMenuBar(new MainMenu());
        setExtendedState(getExtendedState() | MAXIMIZED_BOTH);
        
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        add(tabbedPane);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setMinimumSize(new Dimension(500,500));

        setLocationRelativeTo(null);
    }
    
    public void addProject(ProjectGui gui){
        tabbedPane.add(gui.getProject().getName(), gui);
    }

    public static MainFrame getInstance() {
        return instance;
    }
    
    public static void main(String[] args) {
        MainFrame.getInstance().setVisible(true);
    }
    
}

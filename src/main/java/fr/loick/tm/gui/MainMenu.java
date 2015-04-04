/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.gui;

import fr.loick.tm.gui.action.ProjectActions;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class MainMenu extends JMenuBar{

    public MainMenu() {
        add(file());
    }
    
    private JMenu file(){
        JMenu menu = new JMenu("Fichier");
        menu.setMnemonic('f');
        
        JMenuItem newProject = new JMenuItem("Nouveau projet");
        newProject.setMnemonic('n');
        newProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        newProject.addActionListener(ProjectActions::newProject);
        menu.add(newProject);
        
        return menu;
    }
}

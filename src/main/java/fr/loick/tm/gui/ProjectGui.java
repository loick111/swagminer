/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.gui;

import fr.loick.tm.gui.model.Project;
import javax.swing.JPanel;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ProjectGui extends JPanel{
    final private Project project;

    public ProjectGui(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }
    
}

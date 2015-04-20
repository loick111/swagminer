/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.gui;

import fr.loick.tm.gui.model.Project;
import fr.loick.tm.gui.tools.APrioriTool;
import fr.loick.tm.gui.tools.AssocTool;
import fr.loick.tm.gui.tools.CleanerTool;
import fr.loick.tm.gui.tools.ProjectTool;
import fr.loick.tm.gui.tools.TweetList;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ProjectGui extends JPanel{
    final private Project project;
    final private JTabbedPane tabbedPane;

    public ProjectGui(Project project) {
        super(new BorderLayout());
        this.project = project;
        tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        add(tabbedPane);
        
        addTool(new TweetList(project));
        addTool(new APrioriTool(project));
        addTool(new CleanerTool(project));
        addTool(new AssocTool(project));
    }

    public Project getProject() {
        return project;
    }
    
    final public void addTool(ProjectTool tool){
        tabbedPane.add(tool.getTitle(), tool);
    }
}

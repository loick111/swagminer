/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.gui.tools;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import javax.swing.JPanel;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract public class ProjectTool extends JPanel {

    public ProjectTool(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public ProjectTool(LayoutManager layout) {
        super(layout);
    }

    public ProjectTool(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public ProjectTool() {
        super(new BorderLayout());
    }
    
    abstract public String getTitle();
}

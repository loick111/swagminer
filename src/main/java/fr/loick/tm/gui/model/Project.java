/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.gui.model;

import java.io.File;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class Project {
    final private String name;
    final private File folder;

    public Project(String name, File folder) {
        this.name = name;
        this.folder = folder;
    }

    public String getName() {
        return name;
    }

    public File getFolder() {
        return folder;
    }
    
}

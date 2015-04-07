/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.gui.tools;

import fr.loick.tm.gui.MainFrame;
import fr.loick.tm.gui.model.Project;
import fr.loick.tm.util.CSV;
import fr.loick.tm.util.Strings;
import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class TweetList extends ProjectTool{
    final private Project project;

    public TweetList(Project project) {
        this.project = project;
        loadTable();
    }
    
    private void loadTable(){
        final List<CSV.TweetData> tweetDatas = new ArrayList<>();
        
        try(BufferedReader br = new BufferedReader(new FileReader(new File(project.getName() + "/tweets.csv")))){
            for(String line = br.readLine(); line != null; line = br.readLine()){
                tweetDatas.add(CSV.parse(line));
            }
        }catch(IOException e){
            JOptionPane.showMessageDialog(MainFrame.getInstance(), e, "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        
        JTable table = new JTable(new AbstractTableModel() {
            @Override
            public int getRowCount() {
                return tweetDatas.size();
            }

            @Override
            public int getColumnCount() {
                return 4;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                CSV.TweetData td = tweetDatas.get(rowIndex);
                switch(columnIndex){
                    case 0:
                        return td.getDate();
                    case 1:
                        return td.getUser();
                    case 2:
                        return td.getLocation();
                    case 3:
                        return Strings.join(td.getWords(), " ");
                    default:
                        return null;
                }
            }

            @Override
            public String getColumnName(int column) {
                switch(column){
                    case 0:
                        return "Date";
                    case 1:
                        return "Utilisateur";
                    case 2:
                        return "Lieu";
                    case 3:
                        return "Texte";
                    default:
                        return "";
                }
            }
        });
        
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    @Override
    public String getTitle() {
        return "Tweets";
    }
    
}

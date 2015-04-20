/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.gui.tools;

import fr.loick.tm.assoc.AssocBuilder;
import fr.loick.tm.assoc.CSVFileExporter;
import fr.loick.tm.assoc.TranslateFileExporter;
import fr.loick.tm.gui.MainFrame;
import fr.loick.tm.gui.model.Project;
import fr.loick.tm.util.Strings;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class AssocTool extends ProjectTool{
    final private Project project;
    final private JTable table;
    final private JSpinner minFreq = new JSpinner(new SpinnerNumberModel(.01, .0, 1, .001));
    final private JSpinner minConf = new JSpinner(new SpinnerNumberModel(1, 0, 1, .001));
    
    private class AssocTable extends AbstractTableModel{
        final private List<String[]> data;

        public AssocTable(List<String[]> data) {
            this.data = data;
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data.get(rowIndex)[columnIndex];
        }

        @Override
        public String getColumnName(int column) {
            switch(column){
                case 0:
                    return "Motif (G->D)";
                case 1:
                    return "Fréquence";
                case 2:
                    return "Confiance";
                default:
                    return "";
            }
        }
        
    }

    public AssocTool(Project project) {
        this.project = project;
        
        table = new JTable(new AssocTable(new ArrayList<>()));
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.add(new JLabel("MinConf : "));
        panel.add(minConf);
        panel.add(new JLabel("MinFreq : "));
        panel.add(minFreq);
        
        JButton button = new JButton("Calculer");
        button.addActionListener((ActionEvent e) -> {
            APFileSelector apfs = new APFileSelector();
            
            if(apfs.file == null){   
                JOptionPane.showMessageDialog(null, "Pas de fichier sélectionné");
                return;
            }
            
            File iount = new File(project.getFolder() + "/int_" + apfs.file.getName() + "_" + minFreq.getValue() + "_" + minConf.getValue() + ".assoc");
            File wout = new File(project.getFolder() + "/words_" + apfs.file.getName() + "_" + minFreq.getValue() + "_" + minConf.getValue() + ".assoc");
            
            if(!iount.isFile() || !wout.isFile()){
                
                Map<Set<Integer>, Double> frequencies = new HashMap<>();

                try(BufferedReader br = new BufferedReader(new FileReader(apfs.file))){
                        
                    for(String line = br.readLine(); line != null; line = br.readLine()){
                        String[] data = Strings.split(line, ";", 2);

                        String[] itemSetData = Strings.split(data[0], ",");
                        Set<Integer> set = new HashSet<>(itemSetData.length);

                        for(String i : itemSetData){
                            set.add(Integer.parseInt(i));
                        }

                        frequencies.put(set, Double.parseDouble(data[1]));
                    }
                    
                    project.getDico().load();
                    CSVFileExporter<Integer> e1 = new CSVFileExporter<>(iount);
                    TranslateFileExporter e2 = new TranslateFileExporter(wout, project.getDico());
                    AssocBuilder assocBuilder = new AssocBuilder(frequencies);
                    assocBuilder.addExporter(e1);
                    assocBuilder.addExporter(e2);
                    assocBuilder.buildAssoc((double)minFreq.getValue(), (double)minConf.getValue());
                    e1.close();
                    e2.close();
                }catch(IOException ex){
                    JOptionPane.showMessageDialog(null, ex, "Erreur IO", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            try(BufferedReader br = new BufferedReader(new FileReader(wout))){
                List<String[]> list = new ArrayList<>();
                
                for(String line = br.readLine(); line != null; line = br.readLine()){
                    list.add(Strings.split(line, ";"));
                }
                
                table.setModel(new AssocTable(list));
            }catch(IOException ex){
                JOptionPane.showMessageDialog(null, ex, "Erreur IO", JOptionPane.ERROR_MESSAGE);
                return;
            }
        });
        
        panel.add(button);
        add(panel, BorderLayout.NORTH);
    }
    
    private class APFileSelector extends JDialog{
        private File file = null;

        public APFileSelector() {
            super(MainFrame.getInstance());
            setModal(true);
            setLayout(new FlowLayout(FlowLayout.LEFT));
            
            File[] files = project.getFolder().listFiles((File dir, String name1) -> name1.startsWith("int_") && name1.endsWith(".ap"));
            JComboBox<File> comboBox = new JComboBox<>(files);
            add(comboBox);
            
            JButton button = new JButton("Calculer");
            button.addActionListener((e) -> {
                file = (File)comboBox.getSelectedItem(); 
                dispose();
            });
            add(button);
            
            pack();
            setLocationRelativeTo(MainFrame.getInstance());
            setVisible(true);
        }
    }
    
    @Override
    public String getTitle() {
        return "Associations";
    }
    
}

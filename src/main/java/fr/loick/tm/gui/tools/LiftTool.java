package fr.loick.tm.gui.tools;

import fr.loick.tm.assoc.AssocBuilder;
import fr.loick.tm.assoc.CSVFileExporter;
import fr.loick.tm.assoc.TranslateFileExporter;
import fr.loick.tm.gui.MainFrame;
import fr.loick.tm.gui.model.Project;
import fr.loick.tm.lift.ComputeLift;
import fr.loick.tm.util.Strings;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by loic on 21/04/15.
 */
public class LiftTool extends ProjectTool {
    final private Project project;
    final private JTable table;
    final private JSpinner minLift = new JSpinner(new SpinnerNumberModel(.01, .0, 2000, .001));


    private class AssocTable extends AbstractTableModel {
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
            return 2;
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
                    return "Lift";
                default:
                    return "";
            }
        }

    }

    public LiftTool(Project project) {
        this.project = project;

        table = new JTable(new AssocTable(new ArrayList<>()));
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.add(new JLabel("MinLift : "));
        panel.add(minLift);

        JButton button = new JButton("Calculer");
        button.addActionListener((ActionEvent e) -> {
            APFileSelector apfs = new APFileSelector();
            AssocFileSelector assocfs = new AssocFileSelector();

            if(apfs.file == null){
                JOptionPane.showMessageDialog(null, "Pas de fichier sélectionné");
                return;
            }

            File iount = new File(project.getFolder() + "/" + apfs.file.getName() + "_" + minLift.getValue() + ".lift");

            if(!iount.isFile() ){

                ComputeLift computeLift = new ComputeLift(assocfs.file,apfs.file,iount,(Double)minLift.getValue());
                computeLift.comupute();
            }
            try(BufferedReader br = new BufferedReader(new FileReader(iount))){
                List<String[]> list = new ArrayList<>();

                for(String line = br.readLine(); line != null; line = br.readLine()){
                    list.add(Strings.split(line, ";"));
                }

                table.setModel(new AssocTable(list));
                TableRowSorter<AssocTable> sorter = new TableRowSorter<AssocTable>(new AssocTable(list));
                table.setRowSorter(sorter);

                final JTextField filterText = new JTextField("A");
                JButton buttonfilter = new JButton("Filtrer");
                JPanel panel2 = new JPanel();
                panel2.setLayout(new BoxLayout(panel2, BoxLayout.LINE_AXIS));
                panel2.add(filterText);
                panel2.add(buttonfilter);
                add(panel2, BorderLayout.SOUTH);

                buttonfilter.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String text = filterText.getText();
                        if (text.length() == 0) {
                            sorter.setRowFilter(null);
                        } else {
                            sorter.setRowFilter(RowFilter.regexFilter(text));
                        }
                    }
                });

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

            File[] files = project.getFolder().listFiles((File dir, String name1) -> name1.startsWith("words_") && name1.endsWith(".ap"));
            JComboBox<File> comboBox = new JComboBox<>(files);
            add(comboBox);

            JButton button = new JButton("Choisir");
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

    private class AssocFileSelector extends JDialog{
        private File file = null;

        public AssocFileSelector() {
            super(MainFrame.getInstance());
            setModal(true);
            setLayout(new FlowLayout(FlowLayout.LEFT));

            File[] files = project.getFolder().listFiles((File dir, String name1) -> name1.startsWith("words_") && name1.endsWith(".assoc"));
            JComboBox<File> comboBox = new JComboBox<>(files);
            add(comboBox);

            JButton button = new JButton("Choisir");
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
        return "Lift";
    }
}

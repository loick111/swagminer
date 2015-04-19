package fr.loick.tm.gui.tools;

import fr.loick.tm.fetch.Cleaner;
import fr.loick.tm.fetch.export.CVSToTransClean;
import fr.loick.tm.gui.MainFrame;
import fr.loick.tm.gui.model.Project;
import twitter4j.Status;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Date;

/**
 * Created by loic on 19/04/15.
 */
public class CleanerTool extends ProjectTool {
    final private Project project;
    private PrintStream consoleStream;
    private JTextArea console;

    public CleanerTool(Project project) {
        this.project = project;
        console();
        init();
    }

    private void console(){
        console = new JTextArea();
        console.setForeground(Color.GRAY);
        console.setFont(console.getFont().deriveFont(Font.BOLD));

        console.setEditable(false);
        add(console, BorderLayout.CENTER);
    }

    private void init(){
        JButton startCl = new JButton("Cleaner le fichier");
        JButton modifyW = new JButton("Afficher/Modificer les mots");
        JPanel buttonGrp = new JPanel();
        buttonGrp.add(startCl);
        buttonGrp.add(modifyW);
        add(BorderLayout.NORTH,buttonGrp);
        console.append("Aucune action");
        Cleaner cl = Cleaner.getINSTANCE();

        startCl.addActionListener(e -> {
            try {
                if (cl.clean(project.getName())) {
                    CVSToTransClean cvsToTransClean = new CVSToTransClean(project.getDico(),project.getName());
                    cvsToTransClean.convert();
                    console.setText("Clean fini");
                }
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        });

        modifyW.addActionListener(e -> {
                JDialog box = new JDialog(MainFrame.getInstance());
                JTextField name = new JTextField(cl.getWords());

                box.setTitle("Afficher/Modificer les mots");
                box.setModal(true);

                JPanel form = new JPanel(new GridLayout(0, 2));
                box.add(form, BorderLayout.CENTER);

                form.add(new JLabel("Mots (separé par des espaces)"));
                form.add(name);

                JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                box.add(buttons, BorderLayout.SOUTH);

                JButton okButton = new JButton("Enregistrer");
                okButton.addActionListener(e1 -> {
                    cl.modifyWords(name.getText());
                    box.dispose();
                });
                buttons.add(okButton);

                box.pack();
                box.setLocationRelativeTo(MainFrame.getInstance());
                box.setVisible(true);
            });
    }

    @Override
    public String getTitle() {
        return "Cleaner";
    }
}

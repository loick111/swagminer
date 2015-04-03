package fr.loick.tm.fetch.export;

import fr.loick.tm.fetch.Dico;
import fr.loick.tm.util.Strings;
import twitter4j.Status;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Ploic on 01/04/15.
 */
public class TransExporter implements Exporter {

    final private File file;
    final private BufferedWriter bw;
    final private Dico dico;

    public TransExporter(File file, Dico dico) throws IOException {
        this.file = file;
        bw = new BufferedWriter(new FileWriter(file));
        this.dico = dico;
    }

    @Override
    public void export(Status status) {
        try {
            boolean first = true;
            for (String s : Strings.getWords(status.getText())) {
                int id = dico.getId(s);
                
                if(!first)
                    bw.write(' ');
                else
                    first = false;
                
                bw.write(id + "");
            }

            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            Logger.getLogger(TransExporter.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public Dico getDico() {
        return dico;
    }

    @Override

    public void endExport() {
        try {
            bw.close();
        } catch (IOException e) {
            Logger.getLogger(TransExporter.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}


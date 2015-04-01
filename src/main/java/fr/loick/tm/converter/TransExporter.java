package fr.loick.tm.converter;

import fr.loick.tm.export.Exporter;
import fr.loick.tm.util.CSV;
import twitter4j.Status;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by p13005682 on 01/04/15.
 */
public class TransExporter implements Exporter {

    final private File file;
    final private BufferedWriter bw;
    final private Map<String,Integer> association;
    private Integer id = 0;

    public TransExporter(File file) throws IOException {
        this.file = file;
        bw = new BufferedWriter(new FileWriter(file));
        association  = new HashMap<>();
    }

    @Override
    public void export(Status status) {
        Integer id_curr;
        try {
            for (String s : (CSV.parse(CSV.stringify(status))).getWords()){
                if ((id_curr = association.get(s)) == null){
                    association.put(s,id);
                    bw.write(id+" ");
                    id++;
                }else{
                    bw.write(id_curr+" ");
                }
            }

            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            Logger.getLogger(TransExporter.class.getName()).log(Level.SEVERE, null, e);
        }

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

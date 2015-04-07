/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.fetch.export;

import fr.loick.tm.util.CSV;
import twitter4j.Status;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author q13000412
 */
public class CSVExporter implements Exporter {
    final private File file;
    final private BufferedWriter bw;

    public CSVExporter(File file) throws IOException {
        this.file = file;
        file.delete();
        bw = new BufferedWriter(new FileWriter(file));
    }

    @Override
    public void export(Status status) {
        try {
            bw.write(CSV.stringify(status));
            bw.newLine();
            bw.flush();
        } catch (IOException ex) {
            Logger.getLogger(CSVExporter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void endExport() {
        try {
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(CSVExporter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

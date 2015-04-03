/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.assoc;

import fr.loick.tm.util.CSV;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class CSVFileExporter<T> implements AssocExporter<T>{
    final private PrintWriter pw;

    public CSVFileExporter(File file) throws FileNotFoundException {
        file.delete();
        pw = new PrintWriter(file);
    }

    @Override
    public void export(Assoc<T> assoc) {
        String out = CSV.stringify(assoc.getLeft()) + "->" + CSV.stringify(assoc.getRigth()) + ';' +
                    assoc.getFreq() + ';' +
                    assoc.getTrust();
        pw.println(out);
    }
    
    public void close(){
        pw.flush();
        pw.close();
    }
}

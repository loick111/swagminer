/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.apriori;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Set;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class FileExporter<T> implements APrioriExporter<T>{
    final private PrintWriter pw;

    public FileExporter(File file) throws FileNotFoundException {
        file.delete();
        pw = new PrintWriter(file);
    }

    @Override
    public void export(Set<T> row, int effective) {
        StringBuilder out = new StringBuilder(row.size() * 5);
        
        boolean first = true;
        
        for (T r : row) {
            if(!first){
                out.append(',');
            }else{
                first = false;
            }
            
            out.append(r);
        }
        
        out.append(';').append(effective);
        pw.println(out);
    }
    
    public void close(){
        pw.flush();
        pw.close();
    }
    
}

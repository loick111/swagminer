/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.assoc;

import fr.loick.tm.fetch.Dico;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Set;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class TranslateFileExporter implements AssocExporter<Integer>{
    final private File out;
    final private Dico dico;
    final private PrintWriter pw;

    public TranslateFileExporter(File out, Dico dico) throws FileNotFoundException {
        this.out = out;
        this.dico = dico;
        pw = new PrintWriter(out);
    }

    @Override
    public void export(Assoc<Integer> assoc) {
        pw.println(
            stringifySet(assoc.getLeft()) + "->" +
            stringifySet(assoc.getRigth()) + ";" +
            assoc.getFreq() + ";" + assoc.getTrust()
        );
    }
    
    private String stringifySet(Set<Integer> set){
        StringBuilder sb = new StringBuilder(set.size() * 6);
        
        boolean first = true;
        for (Integer i : set) {
            if(!first){
                sb.append(',');
            }else{
                first = false;
            }
            
            sb.append(dico.getWord(i));
        }
        
        return sb.toString();
    }
    
    public void close(){
        pw.flush();
        pw.close();
    }
}

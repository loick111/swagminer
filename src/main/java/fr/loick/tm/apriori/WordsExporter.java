/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.apriori;

import fr.loick.tm.fetch.Dico;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class WordsExporter implements APrioriExporter<Integer>{
    final private Dico dico;
    final private PrintWriter pw;

    public WordsExporter(File out, Dico dico) throws IOException {
        this.dico = dico;
        dico.load();
        out.delete();
        pw = new PrintWriter(out);
    }
    
    @Override
    public void export(Set<Integer> row, double freq) {
        pw.println(stringifySet(row) + ";" + freq);
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.converter;

import fr.loick.tm.util.Strings;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class AssocTranslatorConverter implements Converter{
    final private BufferedReader br;
    final private PrintWriter pw;
    final private Map<Integer, String> dico;

    public AssocTranslatorConverter(File in, File out, Map<Integer, String> dico) throws FileNotFoundException {
        this.dico = dico;
        br = new BufferedReader(new FileReader(in));
        pw = new PrintWriter(out);
    }

    @Override
    public void convert() {
        try {
            for(String line = br.readLine(); line != null; line = br.readLine()){
                String[] data = Strings.split(line, ";", 2);
                String[] sets = Strings.split(data[0], "->", 2);
                
                pw.println(
                    translateSet(Strings.split(sets[0], ",")) + "->" +
                    translateSet(Strings.split(sets[1], ",")) + ";" +
                    data[1]
                );
            }
            pw.flush();
            pw.close();
        } catch (IOException ex) {
            Logger.getLogger(AssocTranslatorConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String translateSet(String[] set){
        StringBuilder out = new StringBuilder(set.length * 6);
        
        boolean first = true;
        
        for(String i : set){
            if(!first){
                out.append(',');
            }else{
                first = false;
            }
            out.append(dico.get(Integer.parseInt(i)));
        }
        
        return out.toString();
    }
}

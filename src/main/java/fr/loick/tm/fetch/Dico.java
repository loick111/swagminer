/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.fetch;

import fr.loick.tm.util.Strings;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class Dico {
    private int lastId = 0;
    
    final private Map<Integer, String> translate = new HashMap<>();
    final private Map<String, Integer> compile = new HashMap<>();
    final private File file;

    public Dico(File file) {
        this.file = file;
    }
    
    public void save() throws IOException{
        file.delete();
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
            for(Map.Entry<Integer, String> entry : translate.entrySet()){
                bw.write(entry.getKey() + ";" + entry.getValue());
                bw.newLine();
            }
            bw.flush();
        }
    }
    
    public void load() throws FileNotFoundException, IOException{
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            for(String line = br.readLine(); line != null; line = br.readLine()){
                String[] data = Strings.split(line, ";", 2);
                
                int id = Integer.parseInt(data[0]);
                String word = data[1];
                
                if(id > lastId)
                    lastId = id;
                
                translate.put(id, word);
                compile.put(word, id);
            }
        }
    }
    
    public int getId(String word){
        if(!compile.containsKey(word)){
            int id = ++lastId;
            translate.put(id, word);
            compile.put(word, id);
            return id;
        }
        
        return compile.get(word);
    }
    
    public String getWord(int id){
        return translate.get(id);
    }
}

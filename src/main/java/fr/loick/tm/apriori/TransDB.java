/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.apriori;

import fr.loick.tm.util.Strings;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author q13000412
 */
public class TransDB implements Database<Integer>{
    final private File file;

    public TransDB(File file){
        this.file = file;
    }

    @Override
    public DBCursor<Integer> getCursor() {
        final BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TransDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return new DBCursor<Integer>() {
            private String line = null;
            
            @Override
            public boolean next() {
                try {
                    line = br.readLine();
                    return line != null;
                } catch (IOException ex) {
                    Logger.getLogger(TransDB.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }

            @Override
            public Set<Integer> getRow() {
                String[] set = Strings.split(line, " ");
                Set<Integer> row = new HashSet<>(set.length);

                for(String s : set){
                    s = s.trim();

                    if(s.isEmpty())
                        continue;

                    row.add(Integer.parseInt(s));
                }

                return row;
            }

            @Override
            public void close() {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(TransDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
    }
}

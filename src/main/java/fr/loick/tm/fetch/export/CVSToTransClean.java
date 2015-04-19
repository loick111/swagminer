package fr.loick.tm.fetch.export;

import fr.loick.tm.fetch.Dico;
import fr.loick.tm.util.Strings;

import java.io.*;

/**
 * Created by loic on 19/04/15.
 */
public class CVSToTransClean {
    Dico dico = null;
    File csv = null;
    String path;

    public CVSToTransClean(Dico dico, String csv){
        this.csv = new File(csv+"/clean_tweets.csv");
        path = csv;
        this.dico = dico;
    }

    public void convert(){
        try {

            BufferedReader br = new BufferedReader(new FileReader(csv));
            BufferedWriter bw = new BufferedWriter(new FileWriter(path+"/clean_tweets.trans"));

            for (String line; (line = br.readLine()) != null;){
                String[] words = Strings.split(line,";");
                words = Strings.split(words[(words.length-1)],",");
                for (String m : words){
                    bw.write(dico.getId(m)+" ");
                }
                bw.newLine();
                bw.flush();
            }

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}

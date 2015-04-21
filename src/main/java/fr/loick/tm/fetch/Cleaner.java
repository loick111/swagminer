package fr.loick.tm.fetch;


import fr.loick.tm.fetch.export.CSVExporter;
import fr.loick.tm.fetch.export.Exporter;
import fr.loick.tm.util.CSV;
import fr.loick.tm.util.Strings;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by PLoic on 07/04/15.
 */
public class Cleaner {

    final private File blackList;
    private boolean cleaned = false;
    File cvsProject = null;


    public Cleaner(String name) {
        this.blackList = new File("motinutile.txt");
        cvsProject = new File(name+"/clean_tweets.csv");
    }

    public boolean clean(String name) throws FileNotFoundException {

        Set<String> mots = new HashSet<>();


        try(BufferedReader bufferedReaderCsv = new BufferedReader(new FileReader(name+"/tweets.csv"));
            BufferedReader bufferedReaderBlack = new BufferedReader(new FileReader(blackList));
            BufferedWriter bufferedWriterClean = new BufferedWriter(new FileWriter(cvsProject = new File(name+"/clean_tweets.csv")));
        ) {
            for (String line; (line = bufferedReaderBlack.readLine()) != null;){
                mots.add(line);
            }

            for (String line; (line = bufferedReaderCsv.readLine()) != null;){

                CSV.TweetData data = CSV.parse(line);

                Collection<String> cleaned = new ArrayList<>(data.getWords().length);
                for(String s : data.getWords()){
                    if (!mots.contains(s)){
                        cleaned.add(s);
                    }
                }



                CSV.TweetData dataCleaned = new CSV.TweetData(
                        data.getDate(),
                        data.getUser(),
                        data.getLocation(),
                        cleaned.toArray(new String[]{})
                );

                bufferedWriterClean.write(CSV.stringify(dataCleaned));
                bufferedWriterClean.newLine();
                bufferedWriterClean.flush();
            }
            cleaned = true;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;

    }

    public void modifyWords(String words){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(blackList));
            String[] word = Strings.split(words, " ");
            for (String l:word){
                bw.write(l);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Cleaner{" +
                "blackList=" + blackList +
                ", cleaned=" + cleaned +
                ", cvsProject=" + cvsProject +
                '}';
    }

    public String getWords(){
        BufferedReader br = null;
        String word = null;
        try {
            br = new BufferedReader(new FileReader(blackList));
            for (String line; (line = br.readLine()) != null;){
                word += line+" ";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return word;
    }

    public boolean isCleaned(){
        System.out.println(cleaned);
        return cleaned || cvsProject.exists();
    }
}

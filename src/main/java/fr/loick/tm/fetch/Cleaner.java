package fr.loick.tm.fetch;


import fr.loick.tm.util.CSV;

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

    public Cleaner(File blackList) {
        this.blackList = blackList;
    }

    public void clean(File cvs) throws FileNotFoundException {

        Set<String> mots = new HashSet<>();


        try(BufferedReader bufferedReaderCsv = new BufferedReader(new FileReader(cvs));
            BufferedReader bufferedReaderBlack = new BufferedReader(new FileReader(blackList));
            BufferedWriter bufferedWriterClean = new BufferedWriter(new FileWriter(new File(cvs.getName())));
        ) {
            for (String line; (line = bufferedReaderBlack.readLine()) != null;)
                mots.add(line);

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

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

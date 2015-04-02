package fr.loick.tm.converter;

import fr.loick.tm.util.Strings;

import java.io.*;
import java.util.Map;

/**
 * Created by Ploic on 02/04/15.
 */
public class TransToCSVConverter implements Converter {

    private DicoToMapConverter dicoToMapConverter;
    private Map<Integer, String> association;
    private File file;
    BufferedWriter bw;
    BufferedReader br;

    public TransToCSVConverter(File file) {
        try {

            this.file = file;
            dicoToMapConverter = new DicoToMapConverter(file);
            dicoToMapConverter.convert();
            association = dicoToMapConverter.getMap();
            br = new BufferedReader(new FileReader(file));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void convert() {
        try {

            File new_csv = new File(file.getName()+".csv");
            String[] codes;
            bw = new BufferedWriter(new FileWriter(new_csv));

            if ((Strings.split(file.getName(),"."))[1].equals(".out")){
                String line;

                while ((line = br.readLine()) != null) {
                    codes = Strings.split(line," ");

                    for (int i = 0; i < codes.length - 2; ++i) {
                        bw.write(association.get(codes[i]) + " ");
                    }

                    bw.write(codes[codes.length - 1]);
                    bw.newLine();
                    bw.flush();
                }

                br.close();
                bw.close();

            }else if ((Strings.split(file.getName(),"."))[1].equals(".trans")){
                String line;

                while ((line = br.readLine()) != null) {
                    codes = Strings.split(line," ");

                    for (int i = 0; i < codes.length - 1; ++i) {
                        bw.write(association.get(codes[i]) + " ");
                    }
                    bw.newLine();
                    bw.flush();
                }

                br.close();
                bw.close();

            }else {
                System.out.println("Mauvais type de fichier");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

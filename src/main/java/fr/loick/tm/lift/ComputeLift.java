package fr.loick.tm.lift;

import fr.loick.tm.util.Strings;

import java.io.*;

/**
 * Created by loic on 04/04/15.
 */
public class ComputeLift {

    private File apFile;

    private BufferedReader brAssoc;
    private BufferedReader brAp;
    private BufferedWriter bwOut;


    public ComputeLift(File assocFile, File apFile, File outFile) {
        try {
            this.apFile = apFile;
            brAssoc = new BufferedReader(new FileReader(assocFile));
            bwOut = new BufferedWriter(new FileWriter(outFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void comupute(){

        String line;
        String line2;
        int count = 0;

        try {

            while ((line = brAssoc.readLine()) != null){
                if (++count % 100 == 0) System.out.println(count);

                brAp = new BufferedReader(new FileReader(apFile));
                String[] content = Strings.split(line,"->");
                String[] cont = Strings.split(content[1],";");
                String motif = content[0]+","+cont[0];

                double conf = Double.parseDouble(cont[(cont.length)-1]);

                while((line2 = brAp.readLine()) !=  null){
                    String[] tmp;
                    tmp = Strings.split(line2,";");

                    if (tmp[0].equals(motif)){
                        double freq = Double.parseDouble(tmp[1]);
                        double lift;
                        lift = conf / freq;
                        if (lift > 1){
                            bwOut.write(content[0]+"->" + cont[0] + ";" + lift);
                            bwOut.newLine();
                            bwOut.flush();
                        }
                    }
                }
            }
            bwOut.close();
            brAssoc.close();
            brAp.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

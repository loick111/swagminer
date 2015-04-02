package fr.loick.tm.converter;

import java.io.*;
import java.util.Map;

/**
 * Created by Ploic on 01/04/15.
 */
public class MapToDicoConverter implements Serializable,Converter {
    BufferedWriter bw;
    private Map<String, Integer> map;

    public MapToDicoConverter(Map<String, Integer> map, File file) {

        try {
            this.map = map;
            bw = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void convert() {
        try {

            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                bw.write(entry.getValue() + ";" + entry.getKey());
                bw.newLine();
                bw.flush();
            }

            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

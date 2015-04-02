package fr.loick.tm.converter;

import fr.loick.tm.util.Strings;

import javax.print.DocFlavor;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ploic on 02/04/15.
 */
public class DicoToMapConverter {

    BufferedReader br;
    private Map<Integer, String> map;

    public DicoToMapConverter(File file) {

        try {
            this.map = new HashMap<>();
            br = new BufferedReader(new FileReader(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, String> getMap() {
        return map;
    }

    public void convert() {
        try {
            String line;
            while ((line = br.readLine()) != null)
            {
                String[] strings = Strings.split(line,";");
                map.put(Integer.parseInt(strings[0]),strings[1]);
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

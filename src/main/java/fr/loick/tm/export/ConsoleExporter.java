/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.export;

import fr.loick.tm.util.CSV;
import fr.loick.tm.util.Strings;
import twitter4j.Status;

/**
 *
 * @author q13000412
 */
public class ConsoleExporter implements Exporter{

    @Override
    public void export(Status status) {
        System.out.println(CSV.stringify(status));
    }

    @Override
    public void endExport() {}
    
}

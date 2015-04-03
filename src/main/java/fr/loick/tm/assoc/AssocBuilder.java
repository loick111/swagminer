/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.assoc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class AssocBuilder<T> {
    final private Map<Set<T>, Double> frequencies;
    final private Collection<AssocExporter<T>> exporters = new ArrayList<>();

    public AssocBuilder(Map<Set<T>, Double> frequencies) {
        this.frequencies = frequencies;
    }
    
    public void addExporter(AssocExporter<T> exporter){
        exporters.add(exporter);
    }
    
    public void removeExporter(AssocExporter<T> exporter){
        exporters.remove(exporter);
    }
    
    public void buildAssoc(double minFreq, double minTrust){
        for(Map.Entry<Set<T>, Double> x : frequencies.entrySet()){
            for(Map.Entry<Set<T>, Double> y : frequencies.entrySet()){
                if(x.getKey() == y.getKey())
                    continue;
                
                if(x.getKey().size() >= y.getKey().size()) //x should be in y
                    continue;
                
                if(!y.getKey().containsAll(x.getKey()))
                    continue;
                
                Assoc<T> assoc = new Assoc<>(
                        new AssocPattern<>(x.getKey(), x.getValue()),
                        new AssocPattern<>(y.getKey(), y.getValue())
                );
                
                if(assoc.getFreq() < minFreq || assoc.getTrust() < minTrust)
                    continue;
                
                for (AssocExporter<T> exporter : exporters) {
                    exporter.export(assoc);
                }
            }
        }
    }
}

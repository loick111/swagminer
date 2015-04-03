/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.apriori;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author q13000412
 */
public class APriori<T> {
    final private Collection<Set<T>> data;
    final private int minEffective;
    final private Comparator<T> comparator;
    final private Collection<APrioriExporter> exporters = new ArrayList<>();

    public APriori(Collection<Set<T>> data, int minEffective, Comparator<T> comparator) {
        this.data = data;
        this.minEffective = minEffective;
        this.comparator = comparator;
    }
    
    public void addExporter(APrioriExporter<T> exporter){
        exporters.add(exporter);
    }
    
    public void removeExporter(APrioriExporter<T> exporter){
        exporters.remove(exporter);
    }

    public void perform(){
        Set<T> first = new HashSet<>();
        
        for(Set<T> s : data){
            for(T t : s){
                first.add(t);
            }
        }
        
        Collection<Set<T>> lData = new ArrayList<>(first.size());
        
        for(T t : first){
            Set<T> s = new HashSet<>(1);
            s.add(t);
            lData.add(s);
        }
        
        Level<T> level = new Level<>(lData, comparator);
        int i = 1;
        
        do{
            System.out.print("Performing level " + i + " : ");
            Map<Set<T>, Integer> effectives = level.getEffectives(data);
            Collection<Set<T>> levelData = new ArrayList<>(effectives.size());
            
            for(Map.Entry<Set<T>, Integer> entry : effectives.entrySet()){
                if(entry.getValue() >= minEffective){
                    for(APrioriExporter<T> exporter : exporters){
                        exporter.export(entry.getKey(), entry.getValue());
                    }
                    levelData.add(entry.getKey());
                }
            }
            
            System.out.println(levelData.size());
            
            level = new Level<>(levelData, comparator).getNextLevel();
            ++i;
        }while(!level.isEmpty());
    }
}

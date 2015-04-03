/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.apriori;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author q13000412
 */
public class Level<T> {
    final private Collection<Set<T>> data;
    final private Comparator<T> comparator;

    public Level(Collection<Set<T>> data, Comparator<T> comparator) {
        this.data = data;
        this.comparator = comparator;
    }
    
    public Level<T> getNextLevel(){
        Collection<Set<T>> nData = new ArrayList<>();
        
        for (Set<T> o1 : data) {
            for(Set<T> o2 : data){
                if(o1 == o2) //same collection => o1.equals(o2) <=> o1 == o2
                    continue;
                
                Set<T> r = Operators.halfUnion(o1, o2, comparator);
                
                if(r != null && !r.isEmpty())
                    nData.add(r);
            }
        }
        
        return new Level<>(nData, comparator);
    }
    
    public Map<Set<T>, Integer> getEffectives(Database<T> db){
        Map<Set<T>, Integer> effetives = new ConcurrentHashMap<>(data.size());
        
        for(Set<T> d : data){
            effetives.put(d, 0);
        }
        
        DBCursor<T> cursor = db.getCursor();
        while(cursor.next()){
            Set<T> row = cursor.getRow();
            data.parallelStream().forEach((d) -> {
                if(row.containsAll(d)){
                    effetives.put(d, effetives.get(d) + 1);
                }
            });
        }
        cursor.close();
        
        return effetives;
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.apriori;

import fr.loick.tm.util.Strings;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 *
 * @author q13000412
 */
public class TransDB implements Collection<Set<Integer>>{
    final private Collection<Set<Integer>> db = new ArrayList<>();

    public TransDB(File file) throws FileNotFoundException, IOException {
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            for(String line = br.readLine(); line != null; line = br.readLine()){
                String[] set = Strings.split(line, " ");
                Set<Integer> row = new HashSet<>(set.length);
                
                for(String s : set){
                    s = s.trim();
                    
                    if(s.isEmpty())
                        continue;
                    
                    row.add(Integer.parseInt(s));
                }
                
                db.add(row);
            }
        }
    }

    @Override
    public int size() {
        return db.size();
    }

    @Override
    public boolean isEmpty() {
        return db.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return db.contains(o);
    }

    @Override
    public Iterator<Set<Integer>> iterator() {
        return db.iterator();
    }

    @Override
    public Object[] toArray() {
        return db.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return db.toArray(a);
    }

    @Override
    public boolean add(Set<Integer> e) {
        return db.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return db.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return db.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Set<Integer>> c) {
        return db.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return db.removeAll(c);
    }

    @Override
    public boolean removeIf(Predicate<? super Set<Integer>> filter) {
        return db.removeIf(filter);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return db.retainAll(c);
    }

    @Override
    public void clear() {
        db.clear();
    }

    @Override
    public boolean equals(Object o) {
        return db.equals(o);
    }

    @Override
    public int hashCode() {
        return db.hashCode();
    }

    @Override
    public Spliterator<Set<Integer>> spliterator() {
        return db.spliterator();
    }

    @Override
    public Stream<Set<Integer>> stream() {
        return db.stream();
    }

    @Override
    public Stream<Set<Integer>> parallelStream() {
        return db.parallelStream();
    }

    @Override
    public void forEach(Consumer<? super Set<Integer>> action) {
        db.forEach(action);
    }
    
    
}

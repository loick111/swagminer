/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.apriori;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author q13000412
 */
final public class Operators {
    private Operators(){}
    
    static public<T> Set<T> union(Collection<T> x, Collection<T> y){
        Set<T> ret = new HashSet<>(x.size() + y.size());
        
        ret.addAll(x);
        ret.addAll(y);
        
        return ret;
    }
    
    static public<T> T max(Set<T> x, Comparator<T> comp){
        T max = null;
        
        for (T x1 : x) {
            if(comp.compare(x1, max) > 0){
                max = x1;
            }
        }
        
        return max;
    }
    
    static public<T> Set<T> halfUnion(Set<T> x, Set<T> y, Comparator<T> comp){
        T mx = max(x, comp);
        T my = max(y, comp);
        
        if(comp.compare(mx, my) >= 0){
            return null;
        }
        
        Set<T> xx = new HashSet<>(x);
        Set<T> yy = new HashSet<>(y);
        
        xx.remove(mx);
        yy.remove(my);
        
        if(xx.equals(yy)){
            return union(x, y);
        }
        
        return null;
    }
}

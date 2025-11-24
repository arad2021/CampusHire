package com.CampusHire.Model;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class StudentCacheConcrete<Long,Student> implements StudentCache<Long,Student> {

    private HashMap<Long,Student> cache;
    private LinkedList<Long> priorityList;

    public HashMap<Long, Student> getCache() {
        return cache;
    }

    public void setCache(HashMap<Long, Student> cache) {
        this.cache = cache;
    }

    public LinkedList<Long> getPriorityList() {
        return priorityList;
    }

    public void setPriorityList(LinkedList<Long> priorityList) {
        this.priorityList = priorityList;
    }

    public StudentCacheConcrete(){
        this.cache = new HashMap<>();
        this.priorityList = new LinkedList<>();
    }

   public void put(Long key, Student value){
       if (priorityList.size() > 2){
           Long q = priorityList.removeFirst();
           cache.remove(q);
       }
        cache.put(key, value);
        priorityList.addLast(key);
    }
        // kama ezel ahmed?
    public Student get(Long key){
           priorityList.remove(key);
           priorityList.addLast(key);
        return cache.get(key);
    }

    public boolean containsKey(Long key){
        return cache != null && cache.containsKey(key);
    }

   public int size(){
        return cache != null ? cache.size() : 0;
    }

}

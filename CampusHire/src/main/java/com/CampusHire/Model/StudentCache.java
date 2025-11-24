package com.CampusHire.Model;

public abstract interface StudentCache<Long,Student> {

    void put(Long key, Student value);
    Student get(Long key);
    boolean containsKey(Long key);
    int size();

}

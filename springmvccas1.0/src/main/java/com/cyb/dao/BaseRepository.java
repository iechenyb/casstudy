package com.cyb.dao;

public abstract class BaseRepository<M> {  
    public void save(M m) {  
        System.out.println("=====repository save:" + m);  
    }  
}  
  
package com.cyb.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.cyb.dao.BaseRepository;
/**
 * spring4注入增强
 * @author DHUser
 *
 * @param <M>
 */
public abstract class BaseService4<M> {  
    @Autowired  
    protected BaseRepository<M> repository;  
  
    public void save(M m) {  
        repository.save(m);  
    }  
}  
  

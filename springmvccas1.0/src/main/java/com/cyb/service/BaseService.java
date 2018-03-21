package com.cyb.service;

import com.cyb.dao.BaseRepository;

public abstract class BaseService<M> {  
    private BaseRepository<M> repository;  
    public void setRepository(BaseRepository<M> repository) {  
        this.repository = repository;  
    }  
    public void save(M m) {  
        repository.save(m);  
    }  
}  

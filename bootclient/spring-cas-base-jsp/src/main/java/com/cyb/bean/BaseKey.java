package com.cyb.bean;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年7月27日
 */

public class BaseKey {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	
}

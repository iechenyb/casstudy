package org.jasig.services.persondir.support;

import org.jasig.services.persondir.IPersonAttributes;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

/**
 * 扩展用户自定义属性
 * Created by Administrator on 2016/4/30 0030.
 */
public class MultipleAttributeUserDao extends StubPersonAttributeDao {
	
	 @NotNull
	 private JdbcTemplate jdbcTemplate;
	 
	 @NotNull
	 private String  sql;
	 
	 public final void setDataSource(final DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	 }
	 
	 
	public void setSql(String sql) {
		this.sql = sql;
	}



	/**
	 * 如何传递多个参数，获取所在系统或者组织机构的信息
	 */
    @Override
    public IPersonAttributes getPerson(String uid) {
    	System.out.println(this.jdbcTemplate+","+sql);
        Map<String, List<Object>> attributes = new HashMap<String, List<Object>>();
        attributes.put("userid", Collections.singletonList((Object) uid));
        attributes.put("username", Collections.singletonList((Object) "chenyuanbao"));
        attributes.put("email", Collections.singletonList((Object) "test@qq.com"));
        return new AttributeNamedPersonImpl(attributes);
    }
}

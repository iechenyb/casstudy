package com.cyb.condition;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年8月14日
 *
 * 容器中有ThreadPoolTaskExecutor类型的bean时才注入
@ConditionalOnBean(ThreadPoolTaskExecutor.class)
@ConditionalOnMissingBean(ThreadPoolTaskExecutor.class)
// 类路径中有ThreadPoolTaskExecutor类型的bean时才注入
@ConditionalOnClass(ThreadPoolTaskExecutor.class)
@ConditionalOnMissingClass
// 在配置文件中查找hello.name的值，如果能找到并且值等于yan，就注入，如果根本就没配，也注入，这就是matchIfMissing = true的含义
@ConditionalOnProperty(prefix = "hello", name = "name", havingValue = "yan", matchIfMissing = true)
//只在web环境下注入
@ConditionalOnWebApplication
// java8或以上环境才注入
@ConditionalOnJava(ConditionalOnJava.JavaVersion.EIGHT)
 */
@Service
@Conditional(BmsCondition.class)
public class AnyServiceImpl {
	Log log = LogFactory.getLog(AnyServiceImpl.class);
}

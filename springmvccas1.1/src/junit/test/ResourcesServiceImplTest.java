package junit.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ResourcesServiceImplTest {

	@Test
	public void test() {
		        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-*.xml");  		        
	}

}

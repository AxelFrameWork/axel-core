package axel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;
import org.xmlactions.web.PagerServlet;
import org.xmlactions.web.conceal.HttpPager;

@SpringBootApplication
@Configuration
@ImportResource({"classpath:spring-axel.xml"})
@Controller
public class AxelApplication {

	private static ApplicationContext applicationContext;
	
	public static void main(String[] args) {
		applicationContext  = SpringApplication.run(AxelApplication.class, args);
		
		if(args.length > 0) {
			HttpPager.setRealPath(args[0]);
		} else {
			HttpPager.setRealPath(".");
		}
	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	@Bean
	public ServletRegistrationBean<HttpServlet> axelServlet() {
		ServletRegistrationBean<HttpServlet> servRegBean = new ServletRegistrationBean<>();
		HttpPager httpPager = new HttpPager();
		servRegBean.setServlet(new PagerServlet(httpPager));
		servRegBean.addInitParameter("pager.namespace", "axel");
		servRegBean.addUrlMappings("*.html","*.json", "*.csv", "*.js", "*.css"); //  "*.png", "*.jpg");
		servRegBean.setLoadOnStartup(1);
		return servRegBean;
   }
	
	@RequestMapping("**")
	public void processAllOthers(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String pf = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		if (StringUtils.isNotEmpty(pf) && (pf.startsWith("/"))) {
			pf = pf.substring(1);
		}
		InputStream in = new FileInputStream(pf);
		IOUtils.copy(in, response.getOutputStream());
	}
	
	
	
}
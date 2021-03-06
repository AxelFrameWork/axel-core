package axel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.web.PagerServlet;
import org.xmlactions.web.conceal.HttpPager;

@SpringBootApplication
@Configuration
@ImportResource({"classpath:spring-axel.xml"})
@Controller
public class AxelApplication {

	private static final Logger log = LoggerFactory.getLogger(AxelApplication.class);

	private static ApplicationContext applicationContext;
	
	private static final String propsname = "axel.props";
	
	@Autowired
	@Qualifier("pager.execContext")
	static IExecContext execContext;
	
	public static void main(String[] args) {
		applicationContext  = SpringApplication.run(AxelApplication.class, args);
		
		String realPath;
		if (args.length > 0) {
			realPath = args[0];
		} else {
			realPath = ".";
		}
		HttpPager.setRealPath(realPath);
		// *** Handy mechanism for passing properties from start ***
		String axelProps = null;
		if (System.getProperty(propsname) != null) {
			axelProps = System.getProperty(propsname);
		} else if (System.getenv(propsname) != null) {
			axelProps = System.getenv(propsname);
		}
		if (log.isInfoEnabled()) {
			log.info("realPath:" + realPath);
			log.info(propsname + ":" + axelProps);
		}
		HttpPager.setAxelProps(axelProps);
	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * This services all browser page requests that match the Url Mapping "*.html","*.json", "*.csv", "*.js", "*.css".  Add
	 * or remove these mappings as you need.
	 * <p>
	 * It also tells the bean to process all axel action requests that are defined with "axel", as an example
	 * &lt;axel:echo&gt;Hello World!!!&lt;/axel:echo&gt;
	 * 
	 * @return A ServletRegistrationBean
	 */
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
	
	/**
	 * This services all browser page requests that do not match the axelServlet detailed above.
	 * <p>
	 * It basically loads and returns the content of file by inserting the content into the HttpResponse 
	 */
	@RequestMapping("**")
	public void processAllOthers(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String pf = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		if (StringUtils.isNotEmpty(pf) && (pf.startsWith("/"))) {
			pf = pf.substring(1);
		}
		if (StringUtils.isEmpty(pf)) {
			pf = "index.html";	// set default to "index.html"
		}
		InputStream in = new FileInputStream(pf);
		IOUtils.copy(in, response.getOutputStream());
	}
	
	
	
}
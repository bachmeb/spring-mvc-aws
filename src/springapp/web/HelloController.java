package springapp.web;

import org.springframework.web.servlet.mvc.Controller; // spring-webmvc.jar
import org.springframework.web.servlet.ModelAndView; // spring-webmvc.jar

import javax.servlet.ServletException; // servlet-api.jar
import javax.servlet.http.HttpServletRequest; // servlet-api.jar
import javax.servlet.http.HttpServletResponse; // servlet-api.jar

import org.apache.commons.logging.Log; // commons-logging.jar
import org.apache.commons.logging.LogFactory; // commons-logging.jar

import java.io.IOException;
import java.util.Date;

public class HelloController implements Controller {

    protected final Log logger = LogFactory.getLog(getClass());

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

	String now = (new Date()).toString();
        logger.info("Returning hello view with " + now);

        return new ModelAndView("hello", "now", now);
    }

}


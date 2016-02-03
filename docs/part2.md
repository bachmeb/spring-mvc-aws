# Spring MVC on AWS
[README](/README.md)
## Part 2

##### Find a copy of jstl.jar
    sudo find / |grep jstl.jar

##### Copy jstl.jar to the project library
    cp /opt/spring-framework/spring-framework-2.5/lib/j2ee/jstl.jar ~/git/spring-mvc/war/WEB-INF/lib/

##### Find a copy of standard.jar
    sudo find / |grep standard.jar

##### Copy standard.jar to the project library
    cp /opt/spring-framework/spring-framework-2.5/lib/jakarta-taglibs/standard.jar ~/git/spring-mvc/war/WEB-INF/lib/

##### Create a header file for inclusion in all JSPs
    pwd
    cd ~/git/spring-mvc
    mkdir war/WEB-INF/jsp
    vim war/WEB-INF/jsp/include.jsp
```
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
```

##### Update 'index.jsp' to use the include file
    vim war/index.jsp
```
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<%-- Redirected because we can't set the welcome page to a virtual URL. --%>
<c:redirect url="/hello.htm"/>
```
##### Move 'hello.jsp' to the 'WEB-INF/jsp' directory
    mv war/hello.jsp war/WEB-INF/jsp

##### Add the same include directive we added to index.jsp to hello.jsp
    vim war/WEB-INF/jsp/hello.jsp
```
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head><title>Hello :: Spring Application</title></head>
  <body>
    <h1>Hello - Spring Application</h1>
    <p>Greetings, it is now <c:out value="${now}"/></p>
  </body>
</html>
```
##### Update the unit test class
    vim test/springapp/web/HelloControllerTests.java
```
package springapp.web;

import org.springframework.web.servlet.ModelAndView;
import springapp.web.HelloController;
import junit.framework.TestCase;

public class HelloControllerTests extends TestCase {

    public void testHandleRequestView() throws Exception{
        HelloController controller = new HelloController();
        ModelAndView modelAndView = controller.handleRequest(null, null);
        assertEquals("WEB-INF/jsp/hello.jsp", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel());
        String nowValue = (String) modelAndView.getModel().get("now");
        assertNotNull(nowValue);
    }
}
```
##### Run the Ant 'tests' target. The test should fail.
    ant tests
```
tests:
    [junit] Running springapp.web.HelloControllerTests
    [junit] Testsuite: springapp.web.HelloControllerTests
    [junit] Feb 03, 2016 1:30:41 AM springapp.web.HelloController handleRequest
    [junit] INFO: Returning hello view
    [junit] Tests run: 1, Failures: 1, Errors: 0, Time elapsed: 0.031 sec
    [junit] Tests run: 1, Failures: 1, Errors: 0, Time elapsed: 0.031 sec
    [junit] 
    [junit] ------------- Standard Error -----------------
    [junit] Feb 03, 2016 1:30:41 AM springapp.web.HelloController handleRequest
    [junit] INFO: Returning hello view
    [junit] ------------- ---------------- ---------------
    [junit] Testcase: testHandleRequestView(springapp.web.HelloControllerTests):	FAILED
    [junit] expected:<[WEB-INF/jsp/]hello.jsp> but was:<[]hello.jsp>
    [junit] junit.framework.ComparisonFailure: expected:<[WEB-INF/jsp/]hello.jsp> but was:<[]hello.jsp>
    [junit] 	at springapp.web.HelloControllerTests.testHandleRequestView(HelloControllerTests.java:12)
    [junit] 
    [junit] 
    [junit] Test springapp.web.HelloControllerTests FAILED

BUILD FAILED
/home/brian/git/spring-mvc/build.xml:61: tests.failed=true
            ***********************************************************
            ***********************************************************
            ****  One or more tests failed!  Check the output ...  ****
            ***********************************************************
            ***********************************************************

Total time: 1 second
```
##### Update HelloController 
    vim src/springapp/web/HelloController.java
```java
package springapp.web;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.Date;

public class HelloController implements Controller {

    protected final Log logger = LogFactory.getLog(getClass());

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String now = (new Date()).toString();
        logger.info("Returning hello view with " + now);

        return new ModelAndView("WEB-INF/jsp/hello.jsp", "now", now);
    }

}
```
##### Rerun the unit tests
    ant tests
```
build:
    [javac] Compiling 1 source file to /home/brian/git/spring-mvc/war/WEB-INF/classes

buildtests:

tests:
    [junit] Running springapp.web.HelloControllerTests
    [junit] Testsuite: springapp.web.HelloControllerTests
    [junit] Feb 03, 2016 1:40:24 AM springapp.web.HelloController handleRequest
    [junit] INFO: Returning hello view with Wed Feb 03 01:40:24 UTC 2016
    [junit] Tests run: 1, Failures: 0, Errors: 0, Time elapsed: 0.039 sec
    [junit] Tests run: 1, Failures: 0, Errors: 0, Time elapsed: 0.039 sec
    [junit] 
    [junit] ------------- Standard Error -----------------
    [junit] Feb 03, 2016 1:40:24 AM springapp.web.HelloController handleRequest
    [junit] INFO: Returning hello view with Wed Feb 03 01:40:24 UTC 2016
    [junit] ------------- ---------------- ---------------

BUILD SUCCESSFUL
Total time: 1 second
```
##### Update springapp-servlet.xml
    vim war/WEB-INF/springapp-servlet.xml
```
<?xml version="1.0" encoding="UTF-8"?>

<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-2.5.xsd">
    
    <!-- the application context definition for the springapp DispatcherServlet -->
    
    <bean name="/hello.htm" class="springapp.web.HelloController"/>
    
    <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="viewClass" value="org.springframework.web.servlet.view.JstlView"></property>
        <property name="prefix" value="/WEB-INF/jsp/"></property>
        <property name="suffix" value=".jsp"></property>        
    </bean>
            
</beans>
```
    vim test/springapp/web/HelloControllerTests.java
```
package springapp.web;

import org.springframework.web.servlet.ModelAndView;
import springapp.web.HelloController;
import junit.framework.TestCase;

public class HelloControllerTests extends TestCase {

    public void testHandleRequestView() throws Exception{
        HelloController controller = new HelloController();
        ModelAndView modelAndView = controller.handleRequest(null, null);
        assertEquals("hello", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel());
        String nowValue = (String) modelAndView.getModel().get("now");
        assertNotNull(nowValue);
    }
}
```
##### Run the unit tests
    ant tests
```
buildtests:
    [javac] Compiling 1 source file to /home/brian/git/spring-mvc/war/WEB-INF/classes

tests:
    [junit] Running springapp.web.HelloControllerTests
    [junit] Testsuite: springapp.web.HelloControllerTests
    [junit] Feb 03, 2016 1:48:22 AM springapp.web.HelloController handleRequest
    [junit] INFO: Returning hello view with Wed Feb 03 01:48:22 UTC 2016
    [junit] Tests run: 1, Failures: 1, Errors: 0, Time elapsed: 0.036 sec
    [junit] Tests run: 1, Failures: 1, Errors: 0, Time elapsed: 0.036 sec
    [junit] 
    [junit] ------------- Standard Error -----------------
    [junit] Feb 03, 2016 1:48:22 AM springapp.web.HelloController handleRequest
    [junit] INFO: Returning hello view with Wed Feb 03 01:48:22 UTC 2016
    [junit] ------------- ---------------- ---------------
    [junit] Testcase: testHandleRequestView(springapp.web.HelloControllerTests):	FAILED
    [junit] expected:<[hello]> but was:<[WEB-INF/jsp/hello.jsp]>
    [junit] junit.framework.ComparisonFailure: expected:<[hello]> but was:<[WEB-INF/jsp/hello.jsp]>
    [junit] 	at springapp.web.HelloControllerTests.testHandleRequestView(HelloControllerTests.java:12)
    [junit] 
    [junit] 
    [junit] Test springapp.web.HelloControllerTests FAILED

BUILD FAILED
/home/brian/git/spring-mvc/build.xml:61: tests.failed=true
            ***********************************************************
            ***********************************************************
            ****  One or more tests failed!  Check the output ...  ****
            ***********************************************************
            ***********************************************************


```
##### Update HelloController.java
    vim src/springapp/web/HelloController.java
```
package springapp.web;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
```
##### Run the unit tests
    ant tests
```
buildtests:

tests:
    [junit] Running springapp.web.HelloControllerTests
    [junit] Testsuite: springapp.web.HelloControllerTests
    [junit] Feb 03, 2016 1:52:46 AM springapp.web.HelloController handleRequest
    [junit] INFO: Returning hello view with Wed Feb 03 01:52:46 UTC 2016
    [junit] Tests run: 1, Failures: 0, Errors: 0, Time elapsed: 0.043 sec
    [junit] Tests run: 1, Failures: 0, Errors: 0, Time elapsed: 0.043 sec
    [junit] 
    [junit] ------------- Standard Error -----------------
    [junit] Feb 03, 2016 1:52:46 AM springapp.web.HelloController handleRequest
    [junit] INFO: Returning hello view with Wed Feb 03 01:52:46 UTC 2016
    [junit] ------------- ---------------- ---------------

BUILD SUCCESSFUL
Total time: 0 seconds
```
##### Redeploy the project
    sudo ant deploy reload

##### Test the web page
    lynx localhost:8080/springapp
```
               Hello - Spring Application

   Greetings, it is now Wed Feb 03 01:54:55 UTC 2016

```
* * *
[README](/README.md)

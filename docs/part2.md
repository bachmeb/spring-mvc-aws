# Spring MVC on AWS
[README](/README.md)
## Part 2

### References
* http://docs.spring.io/docs/Spring-MVC-step-by-step/part2.html

##### Find a copy of jstl.jar
    sudo find / |grep jstl.jar

##### Copy jstl.jar to the project library
    cp /opt/spring-framework/spring-framework-2.5/lib/j2ee/jstl.jar $DEV/war/WEB-INF/lib/

##### Find a copy of standard.jar
    sudo find / |grep standard.jar

##### Copy standard.jar to the project library
    cp /opt/spring-framework/spring-framework-2.5/lib/jakarta-taglibs/standard.jar $DEV/war/WEB-INF/lib/

##### Create a header file for inclusion in all JSPs
    mkdir $DEV/war/WEB-INF/jsp
    vim $DEV/war/WEB-INF/jsp/include.jsp
```
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
```

##### Delete the contents of 'index.jsp' and replace with the following
    vim  $DEV/war/index.jsp
```
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<%-- Redirected because we can't set the welcome page to a virtual URL. --%>
<c:redirect url="/hello.htm"/>
```
##### Move 'hello.jsp' to the 'WEB-INF/jsp' directory
    mv $DEV/war/hello.jsp $DEV/war/WEB-INF/jsp

##### Add the same include directive we added to index.jsp to hello.jsp
    vim $DEV/war/WEB-INF/jsp/hello.jsp
```
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head><title>Hello :: Spring Application</title></head>
  <body>
    <h1>Hello - Spring Application</h1>
    <p>Greetings, you have reached hello.jsp</p>
    <p>The time is now <c:out value="${now}"/></p>
  </body>
</html>
```
##### Update the unit test class
    vim $DEV/test/springapp/web/HelloControllerTests.java
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
Buildfile: /home/bachmeb/git/spring-mvc/build.xml

build:

buildtests:
    [javac] Compiling 1 source file to /home/bachmeb/git/spring-mvc/war/WEB-INF/classes

tests:
    [junit] Running springapp.web.HelloControllerTests
    [junit] Testsuite: springapp.web.HelloControllerTests
    [junit] Feb 9, 2016 3:59:47 PM springapp.web.HelloController handleRequest
    [junit] INFO: Returning hello view
    [junit] Tests run: 1, Failures: 1, Errors: 0, Time elapsed: 0.041 sec
    [junit] Tests run: 1, Failures: 1, Errors: 0, Time elapsed: 0.041 sec
    [junit]
    [junit] ------------- Standard Error -----------------
    [junit] Feb 9, 2016 3:59:47 PM springapp.web.HelloController handleRequest
    [junit] INFO: Returning hello view
    [junit] ------------- ---------------- ---------------
    [junit] Testcase: testHandleRequestView(springapp.web.HelloControllerTests):        FAILED
    [junit] expected:<[WEB-INF/jsp/]hello.jsp> but was:<[]hello.jsp>
    [junit] junit.framework.ComparisonFailure: expected:<[WEB-INF/jsp/]hello.jsp> but was:<[]hello.jsp>
    [junit]     at springapp.web.HelloControllerTests.testHandleRequestView(HelloControllerTests.java:12)
    [junit]
    [junit]
    [junit] Test springapp.web.HelloControllerTests FAILED

BUILD FAILED
/home/bachmeb/git/spring-mvc/build.xml:71: tests.failed=true
            ***********************************************************
            ***********************************************************
            ****  One or more tests failed!  Check the output ...  ****
            ***********************************************************
            ***********************************************************

Total time: 1 second
```
##### Update HelloController 
    vim $DEV/src/springapp/web/HelloController.java
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
Buildfile: /home/bachmeb/git/spring-mvc/build.xml

build:
    [javac] Compiling 1 source file to /home/bachmeb/git/spring-mvc/war/WEB-INF/classes

buildtests:

tests:
    [junit] Running springapp.web.HelloControllerTests
    [junit] Testsuite: springapp.web.HelloControllerTests
    [junit] Feb 9, 2016 4:03:12 PM springapp.web.HelloController handleRequest
    [junit] INFO: Returning hello view with Tue Feb 09 16:03:12 EST 2016
    [junit] Tests run: 1, Failures: 0, Errors: 0, Time elapsed: 0.059 sec
    [junit] Tests run: 1, Failures: 0, Errors: 0, Time elapsed: 0.059 sec
    [junit]
    [junit] ------------- Standard Error -----------------
    [junit] Feb 9, 2016 4:03:12 PM springapp.web.HelloController handleRequest
    [junit] INFO: Returning hello view with Tue Feb 09 16:03:12 EST 2016
    [junit] ------------- ---------------- ---------------

BUILD SUCCESSFUL
Total time: 1 second
```
##### Update springapp-servlet.xml
    vim $DEV/war/WEB-INF/springapp-servlet.xml
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
    vim $DEV/test/springapp/web/HelloControllerTests.java
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
Buildfile: /home/bachmeb/git/spring-mvc/build.xml

build:

buildtests:
    [javac] Compiling 1 source file to /home/bachmeb/git/spring-mvc/war/WEB-INF/classes

tests:
    [junit] Running springapp.web.HelloControllerTests
    [junit] Testsuite: springapp.web.HelloControllerTests
    [junit] Feb 9, 2016 4:06:13 PM springapp.web.HelloController handleRequest
    [junit] INFO: Returning hello view with Tue Feb 09 16:06:13 EST 2016
    [junit] Tests run: 1, Failures: 1, Errors: 0, Time elapsed: 0.063 sec
    [junit] Tests run: 1, Failures: 1, Errors: 0, Time elapsed: 0.063 sec
    [junit]
    [junit] ------------- Standard Error -----------------
    [junit] Feb 9, 2016 4:06:13 PM springapp.web.HelloController handleRequest
    [junit] INFO: Returning hello view with Tue Feb 09 16:06:13 EST 2016
    [junit] ------------- ---------------- ---------------
    [junit] Testcase: testHandleRequestView(springapp.web.HelloControllerTests):        FAILED
    [junit] expected:<[hello]> but was:<[WEB-INF/jsp/hello.jsp]>
    [junit] junit.framework.ComparisonFailure: expected:<[hello]> but was:<[WEB-INF/jsp/hello.jsp]>
    [junit]     at springapp.web.HelloControllerTests.testHandleRequestView(HelloControllerTests.java:12)
    [junit]
    [junit]
    [junit] Test springapp.web.HelloControllerTests FAILED

BUILD FAILED
/home/bachmeb/git/spring-mvc/build.xml:71: tests.failed=true
            ***********************************************************
            ***********************************************************
            ****  One or more tests failed!  Check the output ...  ****
            ***********************************************************
            ***********************************************************

Total time: 1 second
```
##### Update HelloController.java
    vim $DEV/src/springapp/web/HelloController.java
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
Buildfile: /home/bachmeb/git/spring-mvc/build.xml

build:
    [javac] Compiling 1 source file to /home/bachmeb/git/spring-mvc/war/WEB-INF/classes

buildtests:

tests:
    [junit] Running springapp.web.HelloControllerTests
    [junit] Testsuite: springapp.web.HelloControllerTests
    [junit] Feb 9, 2016 4:07:42 PM springapp.web.HelloController handleRequest
    [junit] INFO: Returning hello view with Tue Feb 09 16:07:42 EST 2016
    [junit] Tests run: 1, Failures: 0, Errors: 0, Time elapsed: 0.031 sec
    [junit] Tests run: 1, Failures: 0, Errors: 0, Time elapsed: 0.031 sec
    [junit]
    [junit] ------------- Standard Error -----------------
    [junit] Feb 9, 2016 4:07:42 PM springapp.web.HelloController handleRequest
    [junit] INFO: Returning hello view with Tue Feb 09 16:07:42 EST 2016
    [junit] ------------- ---------------- ---------------

BUILD SUCCESSFUL
Total time: 1 second
```
##### Redeploy the project
    ant -v deploy reload
```

Apache Ant(TM) version 1.8.3 compiled on February 25 2015
Trying the default build file: build.xml
Buildfile: /home/bachmeb/git/spring-mvc/build.xml
Detected Java version: 1.6 in: /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.37.x86_64/jre
Detected OS: Linux
parsing buildfile /home/bachmeb/git/spring-mvc/build.xml with URI = file:/home/bachmeb/git/spring-mvc/build.xml
Project base dir set to: /home/bachmeb/git/spring-mvc
parsing buildfile jar:file:/usr/share/java/ant.jar!/org/apache/tools/ant/antlib.xml with URI = jar:file:/usr/share/java/ant.jar!/org/apache/tools/ant/antlib.xml from a zip file
 [property] Loading /home/bachmeb/build.properties
Build sequence for target(s) `deploy' is [build, deploy]
Complete build sequence is [build, deploy, reload, start, install, buildtests, tests, list, usage, stop, deploywar, ]

build:
    [mkdir] Skipping /home/bachmeb/git/spring-mvc/war/WEB-INF/classes because it already exists.
    [javac] springapp/web/HelloController.java omitted as /home/bachmeb/git/spring-mvc/war/WEB-INF/classes/springapp/web/HelloController.class is up to date.

deploy:
     [copy] WEB-INF/classes/springapp/web/HelloController.class added as WEB-INF/classes/springapp/web/HelloController.class is outdated.
     [copy] WEB-INF/classes/springapp/web/HelloControllerTests.class added as WEB-INF/classes/springapp/web/HelloControllerTests.class is outdated.
     [copy] WEB-INF/jsp/hello.jsp added as WEB-INF/jsp/hello.jsp doesn't exist.
     [copy] WEB-INF/jsp/include.jsp added as WEB-INF/jsp/include.jsp doesn't exist.
     [copy] WEB-INF/lib/commons-logging.jar omitted as /usr/share/tomcat6/webapps/springapp/WEB-INF/lib/commons-logging.jar is up to date.
     [copy] WEB-INF/lib/jstl.jar added as WEB-INF/lib/jstl.jar doesn't exist.
     [copy] WEB-INF/lib/junit-3.8.2.jar omitted as /usr/share/tomcat6/webapps/springapp/WEB-INF/lib/junit-3.8.2.jar is up to date.
     [copy] WEB-INF/lib/spring-webmvc.jar omitted as /usr/share/tomcat6/webapps/springapp/WEB-INF/lib/spring-webmvc.jar is up to date.
     [copy] WEB-INF/lib/spring.jar omitted as /usr/share/tomcat6/webapps/springapp/WEB-INF/lib/spring.jar is up to date.
     [copy] WEB-INF/lib/standard.jar added as WEB-INF/lib/standard.jar doesn't exist.
     [copy] WEB-INF/springapp-servlet.xml added as WEB-INF/springapp-servlet.xml is outdated.
     [copy] WEB-INF/web.xml omitted as /usr/share/tomcat6/webapps/springapp/WEB-INF/web.xml is up to date.
     [copy] index.jsp added as index.jsp is outdated.
     [copy] No sources found.
     [copy] Copying 8 files to /usr/share/tomcat6/webapps/springapp
     [copy] Copying /home/bachmeb/git/spring-mvc/war/WEB-INF/classes/springapp/web/HelloController.class to /usr/share/tomcat6/webapps/springapp/WEB-INF/classes/springapp/web/HelloController.class
     [copy] Copying /home/bachmeb/git/spring-mvc/war/WEB-INF/classes/springapp/web/HelloControllerTests.class to /usr/share/tomcat6/webapps/springapp/WEB-INF/classes/springapp/web/HelloControllerTests.class
     [copy] Copying /home/bachmeb/git/spring-mvc/war/WEB-INF/jsp/hello.jsp to /usr/share/tomcat6/webapps/springapp/WEB-INF/jsp/hello.jsp
     [copy] Copying /home/bachmeb/git/spring-mvc/war/WEB-INF/jsp/include.jsp to /usr/share/tomcat6/webapps/springapp/WEB-INF/jsp/include.jsp
     [copy] Copying /home/bachmeb/git/spring-mvc/war/WEB-INF/lib/jstl.jar to /usr/share/tomcat6/webapps/springapp/WEB-INF/lib/jstl.jar
     [copy] Copying /home/bachmeb/git/spring-mvc/war/WEB-INF/lib/standard.jar to /usr/share/tomcat6/webapps/springapp/WEB-INF/lib/standard.jar
     [copy] Copying /home/bachmeb/git/spring-mvc/war/WEB-INF/springapp-servlet.xml to /usr/share/tomcat6/webapps/springapp/WEB-INF/springapp-servlet.xml
     [copy] Copying /home/bachmeb/git/spring-mvc/war/index.jsp to /usr/share/tomcat6/webapps/springapp/index.jsp
Build sequence for target(s) `reload' is [reload]
Complete build sequence is [reload, start, install, build, buildtests, tests, deploy, list, usage, stop, deploywar, ]

reload:
   [reload] OK - Reloaded application at context path /springapp

BUILD SUCCESSFUL
Total time: 0 seconds
```
##### Test the web page
    lynx localhost:8080/springapp
```
        Hello - Spring Application

   Greetings, you have reached hello.jsp

   The time is now Tue Feb 09 16:10:38 EST 2016

```
* * *
[README](/README.md)

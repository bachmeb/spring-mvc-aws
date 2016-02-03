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

```

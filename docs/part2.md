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
      vim war/WEB-INF/jsp/include.jsp

##### Update 'index.jsp' to use the include file
        vim war/index.jsp
```
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<%-- Redirected because we can't set the welcome page to a virtual URL. --%>
<c:redirect url="/hello.htm"/>
```
##### Move 'hello.jsp' to the 'WEB-INF/jsp' directory

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

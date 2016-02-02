# Spring MVC on AWS
[README](/README.md)
## Part 2

##### Configure JSTL and add JSP header file

##### Find a copy of jstl.jar
        sudo find / |grep jstl.jar

##### Copy jstl.jar to the project library
        cp /opt/spring-framework/spring-framework-2.5/lib/j2ee/jstl.jar ~/git/spring-mvc/war/WEB-INF/lib/

##### Find a copy of standard.jar
        sudo find / |grep standard.jar

##### Copy standard.jar to the project library
        cp /opt/spring-framework/spring-framework-2.5/lib/jakarta-taglibs/standard.jar ~/git/spring-mvc/war/WEB-INF/lib/


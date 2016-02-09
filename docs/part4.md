# Spring MVC on AWS
## Part 4
[README](/README.md)

### References
* http://docs.spring.io/docs/Spring-MVC-step-by-step/part4.html
* http://docs.spring.io/spring-framework/docs/2.5.x/api/org/springframework/web/servlet/ModelAndView.html
* http://jeromejaglale.com/doc/java/spring/mvc

##### Rename HelloController to InventoryController
    mv ~/git/spring-mvc/src/springapp/web/HelloController.java src/springapp/web/InventoryController.java

##### Rename HelloControllerTests to InventoryControllerTests
    mv ~/git/spring-mvc/test/springapp/web/HelloControllerTests.java test/springapp/web/InventoryControllerTests.java

##### Update InventoryController
```
vim ~/git/spring-mvc/src/springapp/web/InventoryController.java
```
*Give the InventoryController a reference to the ProductManager*  
*Return a Map with both the date and time and the Products list given by the ProductManager.*  
```java
package springapp.web;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import springapp.service.ProductManager;

public class InventoryController implements Controller {

    protected final Log logger = LogFactory.getLog(getClass());

    private ProductManager productManager;

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String now = (new java.util.Date()).toString();
        logger.info("returning hello view with " + now);

        Map<String, Object> myModel = new HashMap<String, Object>();
        myModel.put("now", now);
        myModel.put("products", this.productManager.getProducts());

        return new ModelAndView("hello", "model", myModel);
    }


    public void setProductManager(ProductManager productManager) {
        this.productManager = productManager;
    }

}
```
##### Update InventoryControllerTests
```
vim ~/git/spring-mvc/test/springapp/web/InventoryControllerTests.java
```
```java
package springapp.web;

import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import springapp.service.SimpleProductManager;
import springapp.web.InventoryController;

import junit.framework.TestCase;

public class InventoryControllerTests extends TestCase {

    public void testHandleRequestView() throws Exception{
        InventoryController controller = new InventoryController();
        controller.setProductManager(new SimpleProductManager());
        ModelAndView modelAndView = controller.handleRequest(null, null);
        assertEquals("hello", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel());
        Map modelMap = (Map) modelAndView.getModel().get("model");
        String nowValue = (String) modelMap.get("now");
        assertNotNull(nowValue);
    }
}
```
##### Iterate the Products from the model with the JSTL forEach tag
    vim ~/git/spring-mvc/war/WEB-INF/jsp/hello.jsp
```html
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head><title><fmt:message key="title"/></title></head>
  <body>
    <h1><fmt:message key="heading"/></h1>
    <p><fmt:message key="greeting"/> <c:out value="${model.now}"/></p>
    <h3>Products</h3>
    <c:forEach items="${model.products}" var="prod">
      <c:out value="${prod.description}"/> <i>$<c:out value="${prod.price}"/></i><br><br>
    </c:forEach>
  </body>
</html>
```
##### Update springapp-servlet.xml
```
vim ~/git/spring-mvc/war/WEB-INF/springapp-servlet.xml
```
*Add product beans written in XML*  
*Define a ResourceBundleMessageSource called messageSource*  
```xml
<?xml version="1.0" encoding="UTF-8"?>

<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans-2.5.xsd">

    <!-- the application context definition for the springapp DispatcherServlet -->

    <bean id="productManager" class="springapp.service.SimpleProductManager">
        <property name="products">
            <list>
                <ref bean="product1"/>
                <ref bean="product2"/>
                <ref bean="product3"/>
            </list>
        </property>
    </bean>

    <bean id="product1" class="springapp.domain.Product">
        <property name="description" value="Lamp"/>
        <property name="price" value="5.75"/>
    </bean>
        
    <bean id="product2" class="springapp.domain.Product">
        <property name="description" value="Table"/>
        <property name="price" value="75.25"/>
    </bean>

    <bean id="product3" class="springapp.domain.Product">
        <property name="description" value="Chair"/>
        <property name="price" value="22.79"/>
    </bean>

    <bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource">
        <property name="basename" value="messages"/>
    </bean>

    <bean name="/hello.htm" class="springapp.web.InventoryController">
        <property name="productManager" ref="productManager"/>
    </bean>

    <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="viewClass" value="org.springframework.web.servlet.view.JstlView"/>
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>

</beans>
```
##### Create a ResourceBundleMessageSource
```
vim ~/git/spring-mvc/war/WEB-INF/classes/messages.properties
```
*Add three entries whose keys match the fmt:message tags added to 'hello.jsp'*
```
title=SpringApp
heading=Hello :: SpringApp
greeting=Greetings, it is now
```

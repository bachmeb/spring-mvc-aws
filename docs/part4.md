# Spring MVC on AWS
## Part 4
[README](/README.md)

### References
* http://docs.spring.io/docs/Spring-MVC-step-by-step/part4.html

##### Rename HelloController to InventoryController
    mv ~/git/spring-mvc/src/springapp/web/HelloController.java src/springapp/web/InventoryController.java

##### Rename HelloControllerTests to InventoryControllerTests
    mv ~/git/spring-mvc/test/springapp/web/HelloControllerTests.java test/springapp/web/InventoryControllerTests.java

##### Update InventoryController
    vim ~/git/spring-mvc/src/springapp/web/InventoryController.java
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
*Give the InventoryController a reference to the ProductManager*
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

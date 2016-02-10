# Spring MVC 2.5 on AWS

## Part 3

### References
* http://docs.spring.io/docs/Spring-MVC-step-by-step/part3.html

##### Make a POJO for a domain class called Product
    mkdir $DEV/src/springapp/domain
    vim $DEV/src/springapp/domain/Product.java
```java
package springapp.domain;

import java.io.Serializable;

public class Product implements Serializable {

    private String description;
    private Double price;
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Description: " + description + ";");
        buffer.append("Price: " + price);
        return buffer.toString();
    }
}
```
##### Write the unit tests for the Product class
    mkdir $DEV/test/springapp/domain
    vim $DEV/test/springapp/domain/ProductTests.java
```java
package springapp.domain;

import junit.framework.TestCase;

public class ProductTests extends TestCase {

    private Product product;

    protected void setUp() throws Exception {
        product = new Product();
    }

    public void testSetAndGetDescription() {
        String testDescription = "aDescription";
        assertNull(product.getDescription());
        product.setDescription(testDescription);
        assertEquals(testDescription, product.getDescription());
    }

    public void testSetAndGetPrice() {
        double testPrice = 100.00;
        assertEquals(0, 0, 0);    
        product.setPrice(testPrice);
        assertEquals(testPrice, product.getPrice(), 0);
    }
  
}
```
##### Create the service interface
    mkdir $DEV/src/springapp/service
    vim $DEV/src/springapp/service/ProductManager.java
```java
package springapp.service;

import java.io.Serializable;
import java.util.List;

import springapp.domain.Product;

public interface ProductManager extends Serializable{

    public void increasePrice(int percentage);
    
    public List<Product> getProducts();
    
}
```

##### Create the service class
    vim $DEV/src/springapp/service/SimpleProductManager.java
```java
package springapp.service;

import java.util.List;

import springapp.domain.Product;

public class SimpleProductManager implements ProductManager {

    public List<Product> getProducts() {
        throw new UnsupportedOperationException();
    }

    public void increasePrice(int percentage) {
        throw new UnsupportedOperationException();        
    }

    public void setProducts(List<Product> products) {
        throw new UnsupportedOperationException();        
    }

}
```
##### Write a unit test for the service class
    mkdir $DEV/test/springapp/service
    vim $DEV/test/springapp/service/SimpleProductManagerTests.java
```java
package springapp.service;

import junit.framework.TestCase;

public class SimpleProductManagerTests extends TestCase {

    private SimpleProductManager productManager;
        
    protected void setUp() throws Exception {
        productManager = new SimpleProductManager();
    }

    public void testGetProductsWithNoProducts() {
        productManager = new SimpleProductManager();
        assertNull(productManager.getProducts());
    }

}
```
##### Run the unit tests
    ant tests
```
Buildfile: /home/bachmeb/git/spring-mvc/build.xml

build:
    [javac] Compiling 3 source files to /home/bachmeb/git/spring-mvc/war/WEB-INF/classes

buildtests:
    [javac] Compiling 2 source files to /home/bachmeb/git/spring-mvc/war/WEB-INF/classes

tests:
    [junit] Running springapp.domain.ProductTests
    [junit] Testsuite: springapp.domain.ProductTests
    [junit] Tests run: 2, Failures: 0, Errors: 0, Time elapsed: 0.002 sec
    [junit] Tests run: 2, Failures: 0, Errors: 0, Time elapsed: 0.002 sec
    [junit]
    [junit] Running springapp.service.SimpleProductManagerTests
    [junit] Testsuite: springapp.service.SimpleProductManagerTests
    [junit] Tests run: 1, Failures: 0, Errors: 1, Time elapsed: 0.009 sec
    [junit] Tests run: 1, Failures: 0, Errors: 1, Time elapsed: 0.009 sec
    [junit]
    [junit] Testcase: testGetProductsWithNoProducts(springapp.service.SimpleProductManagerTests):       Caused an ERROR
    [junit] null
    [junit] java.lang.UnsupportedOperationException
    [junit]     at springapp.service.SimpleProductManager.getProducts(SimpleProductManager.java:10)
    [junit]     at springapp.service.SimpleProductManagerTests.testGetProductsWithNoProducts(SimpleProductManagerTests.java:15)
    [junit]
    [junit]
    [junit] Test springapp.service.SimpleProductManagerTests FAILED
    [junit] Running springapp.web.HelloControllerTests
    [junit] Testsuite: springapp.web.HelloControllerTests
    [junit] Feb 9, 2016 4:30:27 PM springapp.web.HelloController handleRequest
    [junit] INFO: Returning hello view with Tue Feb 09 16:30:27 EST 2016
    [junit] Tests run: 1, Failures: 0, Errors: 0, Time elapsed: 0.059 sec
    [junit] Tests run: 1, Failures: 0, Errors: 0, Time elapsed: 0.059 sec
    [junit]
    [junit] ------------- Standard Error -----------------
    [junit] Feb 9, 2016 4:30:27 PM springapp.web.HelloController handleRequest
    [junit] INFO: Returning hello view with Tue Feb 09 16:30:27 EST 2016
    [junit] ------------- ---------------- ---------------

BUILD FAILED
/home/bachmeb/git/spring-mvc/build.xml:71: tests.failed=true
            ***********************************************************
            ***********************************************************
            ****  One or more tests failed!  Check the output ...  ****
            ***********************************************************
            ***********************************************************

Total time: 1 second
```

##### Implement the unit tests
    nano $DEV/test/springapp/service/SimpleProductManagerTests.java
```java
package springapp.service;

import java.util.ArrayList;
import java.util.List;

import springapp.domain.Product;

import junit.framework.TestCase;

public class SimpleProductManagerTests extends TestCase {

    private SimpleProductManager productManager;
    private List<Product> products;
    
    private static int PRODUCT_COUNT = 2;
    
    private static Double CHAIR_PRICE = new Double(20.50);
    private static String CHAIR_DESCRIPTION = "Chair";
    
    private static String TABLE_DESCRIPTION = "Table";
    private static Double TABLE_PRICE = new Double(150.10);         
        
    protected void setUp() throws Exception {
        productManager = new SimpleProductManager();
        products = new ArrayList<Product>();
        
        // stub up a list of products
        Product product = new Product();
        product.setDescription("Chair");
        product.setPrice(CHAIR_PRICE);
        products.add(product);
        
        product = new Product();
        product.setDescription("Table");
        product.setPrice(TABLE_PRICE);
        products.add(product);
        
        productManager.setProducts(products);
    }

    public void testGetProductsWithNoProducts() {
        productManager = new SimpleProductManager();
        assertNull(productManager.getProducts());
    }
    
    public void testGetProducts() {
        List<Product> products = productManager.getProducts();
        assertNotNull(products);        
        assertEquals(PRODUCT_COUNT, productManager.getProducts().size());
    
        Product product = products.get(0);
        assertEquals(CHAIR_DESCRIPTION, product.getDescription());
        assertEquals(CHAIR_PRICE, product.getPrice());
        
        product = products.get(1);
        assertEquals(TABLE_DESCRIPTION, product.getDescription());
        assertEquals(TABLE_PRICE, product.getPrice());      
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
    [junit] Running springapp.domain.ProductTests
    [junit] Testsuite: springapp.domain.ProductTests
    [junit] Tests run: 2, Failures: 0, Errors: 0, Time elapsed: 0.001 sec
    [junit] Tests run: 2, Failures: 0, Errors: 0, Time elapsed: 0.001 sec
    [junit]
    [junit] Running springapp.service.SimpleProductManagerTests
    [junit] Testsuite: springapp.service.SimpleProductManagerTests
    [junit] Tests run: 2, Failures: 0, Errors: 2, Time elapsed: 0.003 sec
    [junit] Tests run: 2, Failures: 0, Errors: 2, Time elapsed: 0.003 sec
    [junit]
    [junit] Testcase: testGetProductsWithNoProducts(springapp.service.SimpleProductManagerTests):       Caused an ERROR
    [junit] null
    [junit] java.lang.UnsupportedOperationException
    [junit]     at springapp.service.SimpleProductManager.setProducts(SimpleProductManager.java:18)
    [junit]     at springapp.service.SimpleProductManagerTests.setUp(SimpleProductManagerTests.java:38)
    [junit]
    [junit]
    [junit] Testcase: testGetProducts(springapp.service.SimpleProductManagerTests):     Caused an ERROR
    [junit] null
    [junit] java.lang.UnsupportedOperationException
    [junit]     at springapp.service.SimpleProductManager.setProducts(SimpleProductManager.java:18)
    [junit]     at springapp.service.SimpleProductManagerTests.setUp(SimpleProductManagerTests.java:38)
    [junit]
    [junit]
    [junit] Test springapp.service.SimpleProductManagerTests FAILED
    [junit] Running springapp.web.HelloControllerTests
    [junit] Testsuite: springapp.web.HelloControllerTests
    [junit] Feb 9, 2016 4:34:10 PM springapp.web.HelloController handleRequest
    [junit] INFO: Returning hello view with Tue Feb 09 16:34:10 EST 2016
    [junit] Tests run: 1, Failures: 0, Errors: 0, Time elapsed: 0.073 sec
    [junit] Tests run: 1, Failures: 0, Errors: 0, Time elapsed: 0.073 sec
    [junit]
    [junit] ------------- Standard Error -----------------
    [junit] Feb 9, 2016 4:34:10 PM springapp.web.HelloController handleRequest
    [junit] INFO: Returning hello view with Tue Feb 09 16:34:10 EST 2016
    [junit] ------------- ---------------- ---------------

BUILD FAILED
/home/bachmeb/git/spring-mvc/build.xml:71: tests.failed=true
            ***********************************************************
            ***********************************************************
            ****  One or more tests failed!  Check the output ...  ****
            ***********************************************************
            ***********************************************************

Total time: 1 second
```
    
##### Implement the getters and setters for the Product class
    nano $DEV/src/springapp/service/SimpleProductManager.java
```java
package springapp.service;

import java.util.ArrayList;
import java.util.List;

import springapp.domain.Product;

public class SimpleProductManager implements ProductManager {

    private List<Product> products;
    
    public List<Product> getProducts() {
        return products;
    }

    public void increasePrice(int percentage) {
        // TODO Auto-generated method stub      
    }

    public void setProducts(List<Product> products) {
        this.products = products;
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
    [junit] Running springapp.domain.ProductTests
    [junit] Testsuite: springapp.domain.ProductTests
    [junit] Tests run: 2, Failures: 0, Errors: 0, Time elapsed: 0.002 sec
    [junit] Tests run: 2, Failures: 0, Errors: 0, Time elapsed: 0.002 sec
    [junit]
    [junit] Running springapp.service.SimpleProductManagerTests
    [junit] Testsuite: springapp.service.SimpleProductManagerTests
    [junit] Tests run: 2, Failures: 0, Errors: 0, Time elapsed: 0.003 sec
    [junit] Tests run: 2, Failures: 0, Errors: 0, Time elapsed: 0.003 sec
    [junit]
    [junit] Running springapp.web.HelloControllerTests
    [junit] Testsuite: springapp.web.HelloControllerTests
    [junit] Feb 9, 2016 4:35:11 PM springapp.web.HelloController handleRequest
    [junit] INFO: Returning hello view with Tue Feb 09 16:35:11 EST 2016
    [junit] Tests run: 1, Failures: 0, Errors: 0, Time elapsed: 0.072 sec
    [junit] Tests run: 1, Failures: 0, Errors: 0, Time elapsed: 0.072 sec
    [junit]
    [junit] ------------- Standard Error -----------------
    [junit] Feb 9, 2016 4:35:11 PM springapp.web.HelloController handleRequest
    [junit] INFO: Returning hello view with Tue Feb 09 16:35:11 EST 2016
    [junit] ------------- ---------------- ---------------

BUILD SUCCESSFUL
Total time: 1 second
```
##### Implememt the test for increasePrice()
    nano $DEV/test/springapp/service/SimpleProductManagerTests.java
```java
package springapp.service;

import java.util.ArrayList;
import java.util.List;

import springapp.domain.Product;

import junit.framework.TestCase;

public class SimpleProductManagerTests extends TestCase {

    private SimpleProductManager productManager;

    private List<Product> products;
    
    private static int PRODUCT_COUNT = 2;
    
    private static Double CHAIR_PRICE = new Double(20.50);
    private static String CHAIR_DESCRIPTION = "Chair";
    
    private static String TABLE_DESCRIPTION = "Table";
    private static Double TABLE_PRICE = new Double(150.10);         
    
    private static int POSITIVE_PRICE_INCREASE = 10;
    
    protected void setUp() throws Exception {
        productManager = new SimpleProductManager();
        products = new ArrayList<Product>();
        
        // stub up a list of products
        Product product = new Product();
        product.setDescription("Chair");
        product.setPrice(CHAIR_PRICE);
        products.add(product);
        
        product = new Product();
        product.setDescription("Table");
        product.setPrice(TABLE_PRICE);
        products.add(product);
        
        productManager.setProducts(products);
    }

    public void testGetProductsWithNoProducts() {
        productManager = new SimpleProductManager();
        assertNull(productManager.getProducts());
    }
    
    public void testGetProducts() {
        List<Product> products = productManager.getProducts();
        assertNotNull(products);        
        assertEquals(PRODUCT_COUNT, productManager.getProducts().size());
    
        Product product = products.get(0);
        assertEquals(CHAIR_DESCRIPTION, product.getDescription());
        assertEquals(CHAIR_PRICE, product.getPrice());
        
        product = products.get(1);
        assertEquals(TABLE_DESCRIPTION, product.getDescription());
        assertEquals(TABLE_PRICE, product.getPrice());      
    }   
    
    public void testIncreasePriceWithNullListOfProducts() {
        try {
            productManager = new SimpleProductManager();
            productManager.increasePrice(POSITIVE_PRICE_INCREASE);
        }
        catch(NullPointerException ex) {
            fail("Products list is null.");
        }
    }
    
    public void testIncreasePriceWithEmptyListOfProducts() {
        try {
            productManager = new SimpleProductManager();
            productManager.setProducts(new ArrayList<Product>());
            productManager.increasePrice(POSITIVE_PRICE_INCREASE);
        }
        catch(Exception ex) {
            fail("Products list is empty.");
        }           
    }
    
    public void testIncreasePriceWithPositivePercentage() {
        productManager.increasePrice(POSITIVE_PRICE_INCREASE);
        double expectedChairPriceWithIncrease = 22.55;
        double expectedTablePriceWithIncrease = 165.11;
        
        List<Product> products = productManager.getProducts();      
        Product product = products.get(0);
        assertEquals(expectedChairPriceWithIncrease, product.getPrice());
        
        product = products.get(1);      
        assertEquals(expectedTablePriceWithIncrease, product.getPrice());       
    }
        
}
```
##### Implement the increasePrice() method in SimpleProductManager
    nano $DEV/src/springapp/service/SimpleProductManager.java
```java
package springapp.service;

import java.util.List;

import springapp.domain.Product;

public class SimpleProductManager implements ProductManager {

    private List<Product> products;
    
    public List<Product> getProducts() {
        return products;
    }

    public void increasePrice(int percentage) {
        if (products != null) {
            for (Product product : products) {
                double newPrice = product.getPrice().doubleValue() * 
                                    (100 + percentage)/100;
                product.setPrice(newPrice);
            }
        }
    }
    
    public void setProducts(List<Product> products) {
        this.products = products;
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
    [junit] Running springapp.domain.ProductTests
    [junit] Testsuite: springapp.domain.ProductTests
    [junit] Tests run: 2, Failures: 0, Errors: 0, Time elapsed: 0.004 sec
    [junit] Tests run: 2, Failures: 0, Errors: 0, Time elapsed: 0.004 sec
    [junit]
    [junit] Running springapp.service.SimpleProductManagerTests
    [junit] Testsuite: springapp.service.SimpleProductManagerTests
    [junit] Tests run: 5, Failures: 0, Errors: 0, Time elapsed: 0.002 sec
    [junit] Tests run: 5, Failures: 0, Errors: 0, Time elapsed: 0.002 sec
    [junit]
    [junit] Running springapp.web.HelloControllerTests
    [junit] Testsuite: springapp.web.HelloControllerTests
    [junit] Feb 9, 2016 4:37:51 PM springapp.web.HelloController handleRequest
    [junit] INFO: Returning hello view with Tue Feb 09 16:37:51 EST 2016
    [junit] Tests run: 1, Failures: 0, Errors: 0, Time elapsed: 0.069 sec
    [junit] Tests run: 1, Failures: 0, Errors: 0, Time elapsed: 0.069 sec
    [junit]
    [junit] ------------- Standard Error -----------------
    [junit] Feb 9, 2016 4:37:51 PM springapp.web.HelloController handleRequest
    [junit] INFO: Returning hello view with Tue Feb 09 16:37:51 EST 2016
    [junit] ------------- ---------------- ---------------

BUILD SUCCESSFUL
Total time: 1 second
```

* * *
[README](/README.md)

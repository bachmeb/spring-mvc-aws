# Spring MVC on AWS
[README](/README.md)

## Part 3

##### Make a POJO for a domain class called Product
    mkdir src/springapp/domain
    vim src/springapp/domain/Product.java
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
    mkdir test/springapp/domain
    vim test/springapp/domain/ProductTests.java
```
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
    mkdir src/springapp/service
    vim src/springapp/service/ProductManager.java
```
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
    vim src/springapp/service/SimpleProductManager.java
```
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
    mkdir test/springapp/service
    vim test/springapp/service/SimpleProductManagerTests.java
```
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

build:
    [javac] Compiling 3 source files to /home/brian/git/spring-mvc/war/WEB-INF/classes

buildtests:
    [javac] Compiling 2 source files to /home/brian/git/spring-mvc/war/WEB-INF/classes

tests:
    [junit] Running springapp.domain.ProductTests
    [junit] Testsuite: springapp.domain.ProductTests
    [junit] Tests run: 2, Failures: 0, Errors: 0, Time elapsed: 0.005 sec
    [junit] Tests run: 2, Failures: 0, Errors: 0, Time elapsed: 0.005 sec
    [junit] 
    [junit] Running springapp.service.SimpleProductManagerTests
    [junit] Testsuite: springapp.service.SimpleProductManagerTests
    [junit] Tests run: 1, Failures: 0, Errors: 1, Time elapsed: 0.007 sec
    [junit] Tests run: 1, Failures: 0, Errors: 1, Time elapsed: 0.007 sec
    [junit] 
    [junit] Testcase: testGetProductsWithNoProducts(springapp.service.SimpleProductManagerTests):	Caused an ERROR
    [junit] null
    [junit] java.lang.UnsupportedOperationException
    [junit] 	at springapp.service.SimpleProductManager.getProducts(SimpleProductManager.java:10)
    [junit] 	at springapp.service.SimpleProductManagerTests.testGetProductsWithNoProducts(SimpleProductManagerTests.java:15)
    [junit] 
    [junit] 
    [junit] Test springapp.service.SimpleProductManagerTests FAILED
    [junit] Running springapp.web.HelloControllerTests
    [junit] Testsuite: springapp.web.HelloControllerTests
    [junit] Feb 03, 2016 7:28:31 PM springapp.web.HelloController handleRequest
    [junit] INFO: Returning hello view with Wed Feb 03 19:28:31 UTC 2016
    [junit] Tests run: 1, Failures: 0, Errors: 0, Time elapsed: 0.07 sec
    [junit] Tests run: 1, Failures: 0, Errors: 0, Time elapsed: 0.07 sec
    [junit] 
    [junit] ------------- Standard Error -----------------
    [junit] Feb 03, 2016 7:28:31 PM springapp.web.HelloController handleRequest
    [junit] INFO: Returning hello view with Wed Feb 03 19:28:31 UTC 2016
    [junit] ------------- ---------------- ---------------

BUILD FAILED
/home/brian/git/spring-mvc/build.xml:61: tests.failed=true
            ***********************************************************
            ***********************************************************
            ****  One or more tests failed!  Check the output ...  ****
            ***********************************************************
            ***********************************************************


```

##### Implement the unit tests
    vim test/springapp/service/SimpleProductManagerTests.java
```
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
build:

buildtests:
    [javac] Compiling 1 source file to /home/brian/git/spring-mvc/war/WEB-INF/classes

tests:
    [junit] Running springapp.domain.ProductTests
    [junit] Testsuite: springapp.domain.ProductTests
    [junit] Tests run: 2, Failures: 0, Errors: 0, Time elapsed: 0.002 sec
    [junit] Tests run: 2, Failures: 0, Errors: 0, Time elapsed: 0.002 sec
    [junit] 
    [junit] Running springapp.service.SimpleProductManagerTests
    [junit] Testsuite: springapp.service.SimpleProductManagerTests
    [junit] Tests run: 2, Failures: 0, Errors: 2, Time elapsed: 0.007 sec
    [junit] Tests run: 2, Failures: 0, Errors: 2, Time elapsed: 0.007 sec
    [junit] 
    [junit] Testcase: testGetProductsWithNoProducts(springapp.service.SimpleProductManagerTests):	Caused an ERROR
    [junit] null
    [junit] java.lang.UnsupportedOperationException
    [junit] 	at springapp.service.SimpleProductManager.setProducts(SimpleProductManager.java:18)
    [junit] 	at springapp.service.SimpleProductManagerTests.setUp(SimpleProductManagerTests.java:38)
    [junit] 
    [junit] 
    [junit] Testcase: testGetProducts(springapp.service.SimpleProductManagerTests):	Caused an ERROR
    [junit] null
    [junit] java.lang.UnsupportedOperationException
    [junit] 	at springapp.service.SimpleProductManager.setProducts(SimpleProductManager.java:18)
    [junit] 	at springapp.service.SimpleProductManagerTests.setUp(SimpleProductManagerTests.java:38)
    [junit] 
    [junit] 
    [junit] Test springapp.service.SimpleProductManagerTests FAILED
    [junit] Running springapp.web.HelloControllerTests
    [junit] Testsuite: springapp.web.HelloControllerTests
    [junit] Feb 03, 2016 7:35:51 PM springapp.web.HelloController handleRequest
    [junit] INFO: Returning hello view with Wed Feb 03 19:35:51 UTC 2016
    [junit] Tests run: 1, Failures: 0, Errors: 0, Time elapsed: 0.056 sec
    [junit] Tests run: 1, Failures: 0, Errors: 0, Time elapsed: 0.056 sec
    [junit] 
    [junit] ------------- Standard Error -----------------
    [junit] Feb 03, 2016 7:35:51 PM springapp.web.HelloController handleRequest
    [junit] INFO: Returning hello view with Wed Feb 03 19:35:51 UTC 2016
    [junit] ------------- ---------------- ---------------

BUILD FAILED
/home/brian/git/spring-mvc/build.xml:61: tests.failed=true
            ***********************************************************
            ***********************************************************
            ****  One or more tests failed!  Check the output ...  ****
            ***********************************************************
            ***********************************************************

Total time: 1 second
```
    
##### Implement the getters and setters for the Product class
    vim src/springapp/service/SimpleProductManager.java
```
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
    
##### Implememt the test for increasePrice()
    vim test/springapp/service/SimpleProductManagerTests.java
```
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
    vim src/springapp/service/SimpleProductManager.java
```
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


* * *
[README](/README.md)

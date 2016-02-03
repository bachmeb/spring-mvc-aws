# Spring MVC on AWS
[README](/README.md)

## Part 3

##### Make a POJO for a domain class called Product
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
  vim src/springapp/service/ProductManager.java':
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
##### 
##### 





* * *
[README](/README.md)

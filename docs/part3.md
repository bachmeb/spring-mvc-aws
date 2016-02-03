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
    vim src/springapp/service/SimpleProductManager.java':
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
##### 
##### 
##### 
##### 
##### 





* * *
[README](/README.md)

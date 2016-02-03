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
##### 
##### 
##### 
##### 
##### 
##### 
##### 
##### 





* * *
[README](/README.md)

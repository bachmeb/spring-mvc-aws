# spring-mvc-aws

## References
* http://docs.spring.io/docs/Spring-MVC-step-by-step/
* http://tomcat.apache.org/whichversion.html
* https://docs.oracle.com/cd/E21454_01/html/821-2532/inst_cli_jdk_javahome_t.html
* https://ant.apache.org/manual/Tasks/property.html
* http://stackoverflow.com/questions/6568634/how-to-solve-cause-the-class-org-apache-tools-ant-taskdefs-optional-junit-juni
* http://stackoverflow.com/questions/15601469/jar-not-loaded-see-servlet-spec-2-3-section-9-7-2-offending-class-javax-serv

## Sequence
#### [Part 1](/docs/part1.md)
* Setup a VM
* Install Java and Tomcat
* Create a project folder
* Make a web.xml, index.html, build.xml, and build.properties file
* Edit tomcat-users.xml
* Restart Tomcat
* Deploy the app
* Test the app
* Copy the required libraries to the project
* Define a DispatcherServlet in web.xml
* Create springapp-servlet.xml
* Create a Controller
* Test the project
* Build the project
* Deploy the project

#### [Part 2](/docs/part2.md)
* Add the Java Standard Tag Library
* Make a JSP header file
* Add the header to the index and the hello files
* Update the unit test to look for the view name and a time value
* Update the controller to provide a view name and a time value
* Add a path prefix and a *.jsp suffix to each view name by adding a viewResolver to springapp-servlet.xml
* Update the unit test to look for a logical view name, rather than a full path the the view file
* Update the controller to return a logical view name, rather than a full path to the view file

#### [Part 3](/docs/part3.md)
* Make a POJO for the domain class
* Write a unit test for the domain class
* Create a service interface
* Create a service class and implement the service interface
* Write a unit test for the service class
* Run the unit tests
* Implement the unit tests
* Run the unit tests
* Implement the getters and setters for the domain class
* Run the unit tests
* Implement the tests for the service class methods
* Implement the service class methods
* Run the unit tests

#### [Part 4](/docs/part4.md)
* 

# Spring MVC on AWS
[README](/README.md)
## Part 1

### References
* http://docs.spring.io/docs/Spring-MVC-step-by-step/part1.html
* http://stackoverflow.com/questions/8611777/java-lang-nosuchmethoderror-javax-servlet-servletcontext-getcontextpathljava
* http://wiki.metawerx.net/wiki/JARFilesYouShouldNeverIncludeInYourWebapp
* https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html

##### Create a new vm
https://aws.amazon.com/ec2/

##### Step 1: Choose an Amazon Machine Image (AMI)
	Amazon Linux AMI 2015.09.1 (HVM), SSD Volume Type - ami-f0091d91

##### Step 2: Choose an Instance Type
	Family: General purpose
	Type: t2.micro
	vCPUs: 1
	Memory: 1
	Instance Storage: EBS only
	EBS-Optimized: -
	Network Performance: Low to Moderate

##### Step 3: Configure Instance Details
	Number of instances: 	1
	Network: (default)
	Subnet: No preference (default subnet in any Availability Zone)
	EBS-optimized: No
	Monitoring: No
	Termination protection: YES <---- UPDATE THIS
	Shutdown behavior: Stop
	IAM role: None
	Tenancy: default
	Host ID: 
	Affinity: Off
	Kernel ID: Use default
	RAM disk ID: Use default
	User data: 
	Assign Public IP: Use subnet setting (Enable)
	Network interfaces: 
	Purchasing option: On demand

##### Step 4: Add Storage
	Volume Type: Root
	Device: /dev/xvda
	Snapshot: snap-ad8e61f8
	Size (GiB): 8
	Volume Type: General Purpose SSD (GP2)
	IOPS: 24 / 3000
	Delete on Termination: Yes
	Encrypted: Not Encrypted

##### Step 5: Tag Instance
	Key: Name
	Value:

##### Step 6: Configure Security Group 
*Allow ICMP, SSH, and TCP traffic from your IP address*

    Type	    Protocol	Port Range	Source
    All TCP	    TCP	        0 - 65535	your ip address/32
    SSH	        TCP	        22	        your ip address/32
    All ICMP	All	        N/A         your ip address/32

##### Download the key pair and change the mode to 400
	chmod 400 pemfile.pem

##### Ping the IP address to confirm that ICMP traffic is allowed from your IP address
	ping [ec2.ipa.ddr.ess]

##### Connect via SSH
	ssh -i pemfile.pem ec2-user@[ec2.ipa.ddr.ess]

##### Switch to root 
	sudo su

##### Ask who am I?
	whoami
	
##### Update yum
	yum update

##### Create a user account for development
	useradd [your new account name]
    
##### Set the password for your development account
	passwd [your new account name]

##### Edit sudoers file
	vim /etc/sudoers

##### Add development account to sudoers file
	## ALLOW {MYACCOUNT} TO SUDO
	[your new account name] ALL=(ALL) ALL

##### Switch to development user
	su [your new account name]
    
##### Ask who am I?
	whoami
    
##### Check the value of the home directory environment variable
	echo $HOME

##### Go home
	cd $HOME

##### Check the Linux distro version
	cat /proc/version

##### Check the hostname of the ec2 instance
	hostname

##### Check the DNS domain name of the ec2 instance
	dnsdomainname

##### Check the internal IP address
	ifconfig

##### Check the external IP address
	wget http://ipinfo.io/ip -qO -

##### Show all of the environment variables
	declare

##### Look for JAVA in the list of environmental variables
	env | grep JAVA

##### Echo the current JAVA_HOME
	echo $JAVA_HOME

##### Ask where is Java?
	whereis java

##### Check the Java version
	java -version

##### See if the Java compiler is installed
	whereis javac
	javac -version

##### Search yum for openjdk
	yum search openjdk
	
##### Install the Open JDK version 1.7
	sudo yum install java-1.7.0-openjdk-devel.x86_64

##### Check the Java compiler version
	javac -version

##### Tell Linux to use the Java interpreter in the JDK 1.7
	sudo /usr/sbin/alternatives --config java

##### Read the symlinks in /usr/lib/jvm/
	ls -l /usr/lib/jvm/

##### Confirm that /usr/lib/jvm/java points to etc/alternatives/java_sdk
	ls -l /usr/lib/jvm/java

##### Confirm that /etc/alternatives/java_sdk points to /usr/lib/jvm/java-1.7.0-openjdk.x86_64
	ls -l /etc/alternatives/java_sdk

##### Confirm that /usr/lib/jvm/java-1.7.0-openjdk.x86_64 points to /usr/lib/jvm/java-1.7.0-openjdk-1.7.0.91.x86_64
	ls -l /usr/lib/jvm/java-1.7.0-openjdk.x86_64

##### Confirm that /usr/lib/jvm/java-1.7.0-openjdk-1.7.0.91.x86_64 is the installation directory for Java SDK 1.7
	ls -l /usr/lib/jvm/java-1.7.0-openjdk-1.7.0.91.x86_64

##### Echo the $JAVA_HOME environment variable
	echo $JAVA_HOME

##### Set the JAVA_HOME environment variable to the Open JDK directory
	export JAVA_HOME='/usr/lib/jvm/java'

##### Echo the $JAVA_HOME environment variable
	echo $JAVA_HOME

##### Install git
	sudo yum install git
    
##### Make a project directory
	pwd
	mkdir -p ~/git/spring-mvc

##### Initialize the git repository
	cd ~/git/spring-mvc
	git init

##### Make a remote repository
	http://github.com
    
##### Add the remote repository
	git remote add origin http://github.com/[username]/[reponame].git

##### Configure git
	git config user.name [username]
	git config user.email [your email address]

##### Pull from the remote directory
	git pull origin master

##### Check the permissions on the files in the project folder and make sure that you can read, write, and execute them
	ls -l ~/git/spring-mvc
	chmod -R 775 ~/git/spring-mvc

##### Make a .gitignore file
	nano .gitignore
```
*.class

# Mobile Tools for Java (J2ME)
.mtj.tmp/

# Package Files #
*.jar
*.war
*.ear

# virtual machine crash logs, see http://www.java.com/en/download/help/error_hotspot.xml
hs_err_pid*

# AWS key
*.pem

# Tomcat
build.properties
build/
dist/
```
##### Commit changes and push to the remote repository
	git status
	git add --all
	git commit -m "git ignore"
	git push --set-upstream origin master
	
##### Search yum for Tomcat
	sudo yum search tomcat
	
##### Install Tomcat 6
	sudo yum install tomcat6 tomcat6-webapps tomcat6-admin-webapps

##### Ask where is Tomcat?
	whereis tomcat6

##### Get a listing of the Catalina Base directory
	ls -l /usr/share/tomcat6/
```
total 4
drwxr-xr-x 2 root root   4096 Feb  1 22:01 bin
lrwxrwxrwx 1 root tomcat   12 Feb  1 22:01 conf -> /etc/tomcat6
lrwxrwxrwx 1 root root     23 Feb  1 22:01 lib -> /usr/share/java/tomcat6
lrwxrwxrwx 1 root root     16 Feb  1 22:01 logs -> /var/log/tomcat6
lrwxrwxrwx 1 root root     23 Feb  1 22:01 temp -> /var/cache/tomcat6/temp
lrwxrwxrwx 1 root root     24 Feb  1 22:01 webapps -> /var/lib/tomcat6/webapps
lrwxrwxrwx 1 root root     23 Feb  1 22:01 work -> /var/cache/tomcat6/work
```
##### See what is listening on port 8080
	sudo lsof -ni:8080
	
##### Start the Tomcat service
	sudo service tomcat6 status
	sudo service tomcat6 start
	sudo service --status-all | grep tomcat
	
##### See what is listening on port 8080
	sudo lsof -ni:8080

##### Install Lynx
	sudo yum install lynx

##### Try the default Tomcat home page
	lynx localhost:8080
```
If you're seeing this page via a web browser, it means you've setup Tomcat successfully. Congratulations!
```

##### Check the external IP address
	wget http://ipinfo.io/ip -qO -

##### Open a web browser on your local machine and go to http://[ec2 ip address]:8080
```
 As you may have guessed by now, this is the default Tomcat home page.
```

##### Make a directory for the web.xml file
	mkdir -p war/WEB-INF

##### Make the web.xml file
	vim ~/git/spring-mvc/war/WEB-INF/web.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app version="2.4"
         xmlns="http://java.sun.com/xml/ns/j2ee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee 
         http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd" >
  <welcome-file-list>
    <welcome-file>
      index.jsp
    </welcome-file>
  </welcome-file-list>
</web-app>
```
##### Make an index file
	vim ~/git/spring-mvc/war/index.jsp
```html
<html>
  <head><title>Example :: Spring Application</title></head>
  <body>
    <h1>Example - Spring Application</h1>
    <p>This is my test. Welcome to index.jsp</p>
  </body>
</html>
```

##### Create a build.xml file. Change the springapp value to whatever you would like. 
	vim ~/git/spring-mvc/build.xml
```xml
<?xml version="1.0"?>

<project name="springapp" basedir="." default="usage">
  <!-- This build.properties file is kept in the root of your home directory -->
    <property file="${user.home}/build.properties"/>

    <property name="src.dir" value="src"/><!-- Make sure this dir exists -->
    <property name="web.dir" value="war"/>
    <property name="build.dir" value="${web.dir}/WEB-INF/classes"/>
    <property name="name" value="springapp"/>

    <path id="master-classpath">
        <fileset dir="${web.dir}/WEB-INF/lib">
            <include name="*.jar"/>
        </fileset>
        <!-- We need the servlet API classes: -->
        <!--  * for Tomcat 5/6 use servlet-api.jar -->
        <!--  * for other app servers - check the docs -->
        <fileset dir="${appserver.lib}">
            <include name="servlet*.jar"/>
        </fileset>
        <pathelement path="${build.dir}"/>
    </path>

    <target name="usage">
        <echo message=""/>
        <echo message="${name} build file"/>
        <echo message="-----------------------------------"/>
        <echo message=""/>
        <echo message="Available targets are:"/>
        <echo message=""/>
        <echo message="build     --> Build the application"/>
        <echo message="deploy    --> Deploy application as directory"/>
        <echo message="deploywar --> Deploy application as a WAR file"/>
        <echo message="install   --> Install application in Tomcat"/>
        <echo message="reload    --> Reload application in Tomcat"/>
        <echo message="start     --> Start Tomcat application"/>
        <echo message="stop      --> Stop Tomcat application"/>
        <echo message="list      --> List Tomcat applications"/>
        <echo message=""/>
    </target>

    <target name="build" description="Compile main source tree java files">
        <mkdir dir="${build.dir}"/>
        <!-- SET THE SOURCE AND TARGET CORRECTLY -->
        <!-- ADD THE includeantruntime PROPERTY -->
        <javac destdir="${build.dir}" 
        	source="1.6" 
        	target="1.6" 
        	debug="true"
        	deprecation="false" 
        	optimize="false" 
        	failonerror="true"
        	includeantruntime="false"
        	>
            <src path="${src.dir}"/>
            <classpath refid="master-classpath"/>
        </javac>
    </target>

    <target name="deploy" depends="build" description="Deploy application">
        <copy todir="${deploy.path}/${name}" preservelastmodified="true">
            <fileset dir="${web.dir}">
                <include name="**/*.*"/>
            </fileset>
        </copy>
    </target>

    <target name="deploywar" depends="build" description="Deploy application as a WAR file">
        <war destfile="${name}.war"
             webxml="${web.dir}/WEB-INF/web.xml">
            <fileset dir="${web.dir}">
                <include name="**/*.*"/>
            </fileset>
        </war>
        <copy todir="${deploy.path}" preservelastmodified="true">
            <fileset dir=".">
                <include name="*.war"/>
            </fileset>
        </copy>
    </target>
    
<!-- ============================================================== -->
<!-- Tomcat tasks - remove these if you don't have Tomcat installed -->
<!-- ============================================================== -->

    <path id="catalina-ant-classpath">
        <!-- We need the Catalina jars for Tomcat -->
        <!--  * for other app servers - check the docs -->
        <fileset dir="${appserver.lib}">
            <include name="catalina-ant.jar"/>
        </fileset>
    </path>

    <taskdef name="install" classname="org.apache.catalina.ant.InstallTask">
        <classpath refid="catalina-ant-classpath"/>
    </taskdef>
    <taskdef name="reload" classname="org.apache.catalina.ant.ReloadTask">
        <classpath refid="catalina-ant-classpath"/>
    </taskdef>
    <taskdef name="list" classname="org.apache.catalina.ant.ListTask">
        <classpath refid="catalina-ant-classpath"/>
    </taskdef>
    <taskdef name="start" classname="org.apache.catalina.ant.StartTask">
        <classpath refid="catalina-ant-classpath"/>
    </taskdef>
    <taskdef name="stop" classname="org.apache.catalina.ant.StopTask">
        <classpath refid="catalina-ant-classpath"/>
    </taskdef>

    <target name="install" description="Install application in Tomcat">
        <install url="${tomcat.manager.url}"
                 username="${tomcat.manager.username}"
                 password="${tomcat.manager.password}"
                 path="/${name}"
                 war="${name}"/>
    </target>

    <target name="reload" description="Reload application in Tomcat">
        <reload url="${tomcat.manager.url}"
                 username="${tomcat.manager.username}"
                 password="${tomcat.manager.password}"
                 path="/${name}"/>
    </target>

    <target name="start" description="Start Tomcat application">
        <start url="${tomcat.manager.url}"
                 username="${tomcat.manager.username}"
                 password="${tomcat.manager.password}"
                 path="/${name}"/>
    </target>

    <target name="stop" description="Stop Tomcat application">
        <stop url="${tomcat.manager.url}"
                 username="${tomcat.manager.username}"
                 password="${tomcat.manager.password}"
                 path="/${name}"/>
    </target>

    <target name="list" description="List Tomcat applications">
        <list url="${tomcat.manager.url}"
                 username="${tomcat.manager.username}"
                 password="${tomcat.manager.password}"/>
    </target>

<!-- End Tomcat tasks -->

</project>
```

##### Make a build properties file in your home directory
	vim ~/build.properties
```
# Ant properties for building the springapp

appserver.home=/usr/share/tomcat6
appserver.lib=${appserver.home}/lib
deploy.path=${appserver.home}/webapps

tomcat.manager.url=http://localhost:8080/manager
tomcat.manager.username=tomcat
tomcat.manager.password=s3cret
```
##### Create Tomcat user named 'tomcat' with 's3cret' as their password
	sudo vim /usr/share/tomcat6/conf/tomcat-users.xml
```xml
<?xml version='1.0' encoding='utf-8'?>
<tomcat-users>
  <role rolename="manager"/>
  <user username="tomcat" password="s3cret" roles="manager"/>
</tomcat-users>
```
	
##### Restart the Tomcat service to get the server to pick up the config file changes
	sudo service tomcat6 status
	sudo service tomcat6 restart
	sudo service --status-all | grep tomcat

##### Visit the default Tomcat home page to make sure it is up an running
	lynx localhost:8080
	
##### Install Ant
	sudo yum install ant

##### Check the Ant version
	ant -version
```
Apache Ant(TM) version 1.8.3 compiled on February 25 2015
```
##### Run ant
	ant -v
```
usage:
     [echo] 
     [echo] springapp build file
     [echo] -----------------------------------
     [echo] 
     [echo] Available targets are:
     [echo] 
     [echo] build     --> Build the application
     [echo] deploy    --> Deploy application as directory
     [echo] deploywar --> Deploy application as a WAR file
     [echo] install   --> Install application in Tomcat
     [echo] reload    --> Reload application in Tomcat
     [echo] start     --> Start Tomcat application
     [echo] stop      --> Stop Tomcat application
     [echo] list      --> List Tomcat applications
     [echo] 

BUILD SUCCESSFUL
Total time: 0 seconds
```

##### List the intstalled Tomcat applications
	ant -v list

##### Make a directory for Java files
	mkdir src  
	
##### Build the project
	ant -v build

##### Give your developer account permission to write to the webapps directory
	sudo chown -R [YOUR DEVELOPER ACCOUNT] /usr/share/tomcat6/webapps

##### Deploy the project
	ant -v deploy

##### Test the web app
	lynx localhost:8080/springapp

##### Find a copy of the Spring Framework 2.5 with dependencies
* https://github.com/bachmeb/spring-framework-2.5

##### Download and uppack Spring Framework 2.5 with dependencies
	cd /opt
	sudo mkdir spring-framework
	cd spring-framework/
	sudo wget http://s3.amazonaws.com/dist.springframework.org/release/SPR/spring-framework-2.5-with-dependencies.zip
	sudo unzip spring-framework-2.5-with-dependencies.zip
	
##### Make a lib directory in WEB-INF
	mkdir ~/git/spring-mvc/war/WEB-INF/lib

##### Find spring.jar, spring-webmvc.jar, and commons-logging.jar in the spring-framework package
	find /opt/spring-framework/spring-framework-2.5/ | grep spring.jar
	find /opt/spring-framework/spring-framework-2.5/ | grep spring-webmvc.jar
	find /opt/spring-framework/spring-framework-2.5/ | grep commons-logging.jar

##### Copy the libraries to WEB-INF/lib
	find /opt/spring-framework/spring-framework-2.5/ | grep commons-logging.jar | xargs cp -t ~/git/spring-mvc/war/WEB-INF/lib/
	find /opt/spring-framework/spring-framework-2.5/ | grep spring-webmvc.jar | xargs cp -t ~/git/spring-mvc/war/WEB-INF/lib/
	find /opt/spring-framework/spring-framework-2.5/ | grep spring.jar | xargs cp -t ~/git/spring-mvc/war/WEB-INF/lib/
	ls -l ~/git/spring-mvc/war/WEB-INF/lib/

##### List the contents of each jar file
	jar tf war/WEB-INF/lib/spring.jar
	jar tf war/WEB-INF/lib/spring-webmvc.jar 
	jar tf war/WEB-INF/lib/commons-logging.jar 

##### Define a [DispatcherServlet](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html) in web.xml. Map the servlet to the *.htm file pattern.
	vim ~/git/spring-mvc/war/WEB-INF/web.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>

<web-app version="2.4"
         xmlns="http://java.sun.com/xml/ns/j2ee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee 
         http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd" >

  <welcome-file-list>
    <welcome-file>
      index.jsp
    </welcome-file>
  </welcome-file-list>
  
  <servlet>
    <servlet-name>springapp</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
  </servlet>

  <servlet-mapping>
    <servlet-name>springapp</servlet-name>
    <url-pattern>*.htm</url-pattern>
  </servlet-mapping>

</web-app>
```
##### Build, deploy, and test the project
	ant build
	sudo ant deploy
	lynx localhost:8080/springapp

##### Create springapp-servlet.xml
	vim ~/git/spring-mvc/war/WEB-INF/springapp-servlet.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>

<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans-2.5.xsd">

  <!-- the application context definition for the springapp DispatcherServlet -->

  <bean name="/hello.htm" class="springapp.web.HelloController"/>

</beans>
```

##### Build, deploy, and test the project
	ant build
	sudo ant deploy
	lynx localhost:8080/springapp
	
##### Create HelloController
	mkdir -p ~/git/spring-mvc/src/springapp/web
	vim ~/git/spring-mvc/src/springapp/web/HelloController.java
```java
package springapp.web;

import org.springframework.web.servlet.mvc.Controller; // spring-webmvc.jar
import org.springframework.web.servlet.ModelAndView; // spring-webmvc.jar

import javax.servlet.ServletException; // servlet-api.jar
import javax.servlet.http.HttpServletRequest; // servlet-api.jar
import javax.servlet.http.HttpServletResponse; // servlet-api.jar

import org.apache.commons.logging.Log; // commons-logging.jar
import org.apache.commons.logging.LogFactory; // commons-logging.jar

import java.io.IOException;

public class HelloController implements Controller {

    protected final Log logger = LogFactory.getLog(getClass());

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        logger.info("Returning hello view");

        return new ModelAndView("hello.jsp");
    }

}
```
##### Update the master-classpath in build.xml to look for servlet*.jar in the spring framework 2.5 directory
	vim ~/git/spring-mvc/build.xml
```
    <path id="master-classpath">
        <fileset dir="${web.dir}/WEB-INF/lib">
            <include name="*.jar"/>
        </fileset>
        <!-- We need the servlet API classes: -->
        <!--  * for Tomcat 5/6 use servlet-api.jar -->
        <!--  * for other app servers - check the docs -->
        <fileset dir="${appserver.lib}">
            <include name="servlet*.jar"/>
        </fileset>

        <fileset dir="/opt/spring-framework/spring-framework-2.5/lib/j2ee/">
            <include name="servlet*.jar"/>
        </fileset>

        <pathelement path="${build.dir}"/>
    </path>

```
##### Build the project
	ant build
	
##### Create a test class
	mkdir -p ~/git/spring-mvc/test/springapp/web
	vim ~/git/spring-mvc/test/springapp/web/HelloControllerTests.java
```java
package springapp.web;

import org.springframework.web.servlet.ModelAndView;
import springapp.web.HelloController;
import junit.framework.TestCase;

public class HelloControllerTests extends TestCase {

    public void testHandleRequestView() throws Exception{		
        HelloController controller = new HelloController();
        ModelAndView modelAndView = controller.handleRequest(null, null);		
        assertEquals("hello.jsp", modelAndView.getViewName());
    }
}
```
##### Add test tasks to build script
	vim ~/git/spring-mvc/build.xml
```xml
    <property name="test.dir" value="test"/>
    <!-- SET THE SOURCE AND TARGET VALUES CORRECTLY-->
    <target name="buildtests" description="Compile test tree java files">
        <mkdir dir="${build.dir}"/>
        <javac destdir="${build.dir}" 
        	source="1.6" 
        	target="1.6" 
        	debug="true"
            	deprecation="false" 
            	optimize="false" 
            	failonerror="true"
            	includeantruntime="false"
            	>
            <src path="${test.dir}"/>
            <classpath refid="master-classpath"/>
        </javac>
    </target>
    
    <target name="tests" depends="build, buildtests" description="Run tests">
        <junit printsummary="on"
            fork="false"
            haltonfailure="false"
            failureproperty="tests.failed"
            showoutput="true">
            <classpath refid="master-classpath"/>
            <formatter type="brief" usefile="false"/>
            
            <batchtest>
                <fileset dir="${build.dir}">
                    <include name="**/*Tests.*"/>
                </fileset>
            </batchtest>
            
        </junit>
        
        <fail if="tests.failed">
            tests.failed=${tests.failed}
            ***********************************************************
            ***********************************************************
            ****  One or more tests failed!  Check the output ...  ****
            ***********************************************************
            ***********************************************************
        </fail>
    </target>
```
##### Install Ant JUnit
	sudo yum search ant-junit
	sudo yum install ant-junit

##### Copy junit-3.8.2.jar to the WEB-INF/lib directory
	find /opt/spring-framework/spring-framework-2.5 
	find /opt/spring-framework/spring-framework-2.5 | grep junit-3.8.2.jar 
	find /opt/spring-framework/spring-framework-2.5 | grep junit-3.8.2.jar | xargs cp -t ~/git/spring-mvc/war/WEB-INF/lib/

##### List the contents of the WEB-INF/lib directory	
	ls -la ~/git/spring-mvc/war/WEB-INF/lib/
```
drwxrwxr-x 2 bachmeb bachmeb    4096 Feb  9 15:12 .
drwxrwxr-x 4 bachmeb bachmeb    4096 Feb  9 14:43 ..
-rw-r--r-- 1 bachmeb bachmeb   52915 Feb  9 14:37 commons-logging.jar
-rw-r--r-- 1 bachmeb bachmeb  120640 Feb  9 15:12 junit-3.8.2.jar
-rw-r--r-- 1 bachmeb bachmeb 2838649 Feb  9 14:37 spring.jar
-rw-r--r-- 1 bachmeb bachmeb  330204 Feb  9 14:37 spring-webmvc.jar
```

##### Run test tasks
	ant tests
```
Buildfile: /home/bachmeb/git/spring-mvc/build.xml

build:

buildtests:
    [javac] Compiling 1 source file to /home/bachmeb/git/spring-mvc/war/WEB-INF/classes

tests:
    [junit] Running springapp.web.HelloControllerTests
    [junit] Testsuite: springapp.web.HelloControllerTests
    [junit] Feb 8, 2016 9:17:45 PM springapp.web.HelloController handleRequest
    [junit] INFO: Returning hello view
    [junit] Tests run: 1, Failures: 0, Errors: 0, Time elapsed: 0.043 sec
    [junit] Tests run: 1, Failures: 0, Errors: 0, Time elapsed: 0.043 sec
    [junit]
    [junit] ------------- Standard Error -----------------
    [junit] Feb 8, 2016 9:17:45 PM springapp.web.HelloController handleRequest
    [junit] INFO: Returning hello view
    [junit] ------------- ---------------- ---------------

BUILD SUCCESSFUL
Total time: 1 second
```
##### Make a hello.jsp file next to the index file
	vim ~/git/spring-mvc/war/hello.jsp
```html
<html>
  <head><title>Hello :: Spring Application</title></head>
  <body>
    <h1>Hello - Spring Application</h1>
    <p>Greetings. You have reached hello.jsp</p>
  </body>
</html>
```

##### Run test tasks
	ant tests

##### Compile and deploy the application
	sudo ant deploy
	ant reload

##### Test the web page in a browser
	lynx http://localhost:8080/springapp/hello.htm
```
The HTTP request goes to springapp/hello.htm
hello.htm is mapped to HelloController.java in springapp-servlet.xml
HelloController returns a ModelAndView named "hello.jsp"
The springapp/hello.jsp page is returned to the browser
```
	
##### Read the catalina.out file
	cat /usr/share/tomcat6/logs/catalina.out


* * *
[README](/README.md)

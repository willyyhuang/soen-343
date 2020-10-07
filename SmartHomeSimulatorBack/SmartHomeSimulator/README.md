# Back-End Overview

###Installation 

You have to download java 11 , jdk 11.0.8 or newer and a java text editor such as Intellij (prefered) , Netbeans or Eclipse. 
Make sure to see the README.md file in the root of the project if you want to test the backend and frontend.

### Maven and Spring Boot

We used Maven, Spring Boot and jdk 11 to form the backend.

Maven is a software project management and comprehension tool. Maven is integrated with Intellij, Eclipse or 
Netbeans in all recent versions. It comes with a file named pom.xml where we can specify any external dependency we need 
to run the code. Any external JAR or ZIP file we might need can be specified in pom.xml instead of having to manually 
download it on our computer.
        
Basically it will automatically go and find these dependencies instead of having to download each one individually to be
able to run our code.

Since Spring boot is a dependency and it is specified in pom.xml, we don't have to download anything.

###Models

This Package will contain the models for users and homes. They will have the same properties they have in the database.

###DAO

This package will deal with the database; Add, Edit or Remove. It has an Interface and its implementation. That way, we 
can use dependency injection in the Service Package.

###API

It contains the controllers, the classes that will receive the https calls. 

Here is what we have for now:

**Adding a user (POST)**: "http://localhost:****/api/v1/user/register"  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;body:  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"username":"username",  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "password":"password"  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}

Returns 0 if user exists and 1 if it was successful 

**Removing a user (POST)**: "http://localhost:****/api/v1/user/remove?username=username"  

Returns 0 if user doesn't exist and 1 if it was successful

**Identifying a user (POST)**: "http://localhost:****/api/v1/user/login"  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;body:  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"username":"username",  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "password":"password"  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}

Returns username if right password, Not found if user does not exist and No Match if wrong password

**Edit Password (POST)**: "http://localhost:****/api/v1/user/editPassword"  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;body:  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"username":"username",  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "password":"NewPassword"  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}

Returns 0 if user doesn't exist and 1 if it was successful     

**Edit HomeLocation (POST)**: "http://localhost:****/api/v1/user/editHomeLocation"  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;body:  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"username":"username",  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "password":"PasswordNotNecessary",
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "homeLocation":"homeLocation"  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}

Returns 0 if user doesn't exist and 1 if it was successful   


###Service

This package contains the classes that will be triggered when an API call is made to deal with the database and return 
information to the Front-End when needed (like return a user for example).

###In other words

API call --> Service --> DAO --> Service --> Front-End

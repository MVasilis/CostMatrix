# CostMatrix

The technologies used for this application are:
SpringBoot,  Spring Reactive, spring webFlux, H2 Database, Lombok, Rest Api

# Build project

To build this project, you need to have installed in your PC Java 11 and Maven 3.6.2. After downloading the sourceCode, you can use an IDE like IntelliJ and through the terminal (in the project root) :

    - run command -> mvn clean install or mvn clean install -DskipTests During this process, project will download all the necessery dependencies. Please be sure, that your IDE is supporting Lombok. After the succeess of the build, you can run the application by :
    - using command -> mvn spring-boot:run (in the project roo).


Because we use SpringBoot and H2 database, Application has an embeded tomcat and DB. This means that the application will run without any issue. to have access in the DB while the application is running, using a browser please go to -> localhost:port/h2 No password is needed. (http://localhost:8081/h2) 
In the property file, the port has been configured as 8081. (you can change it by going at application.properties) Also at the application.properties we are keeping the configuration for H2 embeded DataBase.

How to create a running Jar. 
- After building project, in the terminal and in the project root you need to run command "mvn package" The jar will be created inside the target directory. To run this jar file, through the command line, navigate to the directory where the jar file exists. Run command -> java -jar (file_name).jar.

What to do after running application.
- After seccesfully exeturing command java -jar (file_name).jar  we have a running application. This means that if we navigate to http://localhost:8081/h2 we will have access to the inmemory database. Loggin without using password and execute below Sql Script

CREATE TABLE COST_MATRIX (
	COST_ID UUID NOT NULL PRIMARY KEY,
    COUNTRY VARCHAR(10) UNIQUE,
    COST DECIMAL(10,2)
);

After the Sql execution, we have ready out RDBMS table. And we can start using application.
Cost Matrix End points
- POST -->  localhost:8081/cost-matrix
 Body -> {
          "country":"OTHER",
          "cost":18
         }
With the above endPoint we can start add cost matrix to our DB

- PUT -> localhost:8081/cost-matrix/{costMatrixId}
  Body -> {
          "country":"CountryCode",
          "cost":new Value
         }
With the above EndPoint we can update any CostMastirx by Id

- Get -> localhost:8081/cost-matrix/ 
Above endPoint is retrieving all added costMatrix values

- Delete -> localhost:8081/cost-matrix/{costMatrixId}
With the above endPoint we can delete a given cost Matrix

As we can see, above we have the CRUD functionality. 

To Retrieve any Cost by CardNumber we need to call below endPoint
- Get -> localhost:8081/card/{cardNumber}/clearing-cost
Calling this endPoint, Application will call binList service, to find the Country code, and By country code will return us the cost, in form like below
{
    "country": "GR",
    "cost": 18.00
}



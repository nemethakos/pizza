# pizza

The goal of the application to find the best combinations (up to 5) of pizza order for a specified amount of money, where each pizza can be ordered multiple times and the prices of pizzas can be the same. It uses _backtracking_ to discover all valid combinations.

![alt text](https://github.com/nemethakos/pizza/raw/main/media/varieties.PNG)

The backtracking algorithm runs _until_:
  - find 5 perfect combination (no remaining amount of money)
  - iterates over all valid combinations (less than **1,000,000** combinations examined)
  - the number of examined combinations reaches **1,000,000**

The amount of money for the pizza ordering is between **1 - 1,000,000** HUF

To build the applications the followings are needed:
- java 11 (or higher)
- maven 3.6 (or higher)

To run the web application (accessible at http://localhost:8080) :
```
   mvn spring-boot:run
```

To deploy on Azure
(change azure-webapp-maven-plugin settings to avoid collision)
```
   mvn package azure-webapp:deploy
```

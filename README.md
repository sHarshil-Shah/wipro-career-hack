# wipro-career-hack


Install Docker on your system

##Deployment Guide - Frontend:
Setup appropriate Backend URL in wipro-career-hack\frontend\src\app\globals.ts file
1. frontend contains angular portal which runs on 4200 port
2. Run following command from frontend folder when docker is running
3. docker build -t wipro-hack-frontend .
4. docker run -d --name frontend -p 80:80 wipro-hack-frontend


##Deployment Guide - Backend:
Setup appropriate Frontend URL in wipro-career-hack\backend\src\main\java\com\harshil\mail\sendmail.java file
1. backend has spring boot which runs on 8083 port
2. Run following command from backend folder when docker is running 
3. docker build -t wipro-hack-backend .
4. docker run -d --name backend -p 8083:8083 wipro-hack-backend


Go to localhost:4200 to ENJOY!


## Docker Images available on Docker HUB
1. Via GitHub Actions I have created 2 publicaliy available docker image for anyone to user directly.
2. Configured URLs: 65.0.20.254 for frontend and 65.1.105.69:8083 for backend
3. these docker images are already running on these servers



## For MySQL server
Hosted on AWS RDS
DB URL = wiprohack.cw5ssbjmhdhe.ap-south-1.rds.amazonaws.com:3306
db = wiprohack
username = admin
password = adminadmin





## Without Docker Native Deployment

1. Frontend - Angular

  - go to frontend directory
  - npm install
  - ng serve

App will start on localhost:4200 by default

2. Backend - Spring Boot
  - go to backend directory
  - mvn clean
  - mvn compile 
  - mvn package
  - java -cp target/myApp-0.0.1-SNAPSHOT.jar go.to.myApp.select.file.to.execute

Backend will run on localhost:8083 while it will connect to MySQL server running on AWS RDS



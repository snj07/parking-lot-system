# ParkingLot System

## Project Tech Stack:
Programming Language : Java<br/>
Build tool: Gradle<br/>
Unit testing: JUnit5<br/>

## Project structure:
1. **constants:** Project related constants including error messages, output messages, command constants and regex
2. **controller:** contains classes to handle input from console/file and command execution
3. **dao:** contains classes to handle data 
4. **enums:** contains project related enums including custom error codes
5. **exception:** contains project specific exception
6. **model:** contains data models
7. **observers:** contains observers for ParkingLot commands to capture the output of commands
8. **service:** contains business logic classes for ParkingLot system
9. **strategy:** contains project strategies currently having Parking strategy
10. **utils:** contains utility methods for reading the file
11 **Main.java** contains main method to run the project
 

## Commands to run project:
Please update the relative path of the input file in the below command
```
gradlew build
gradlew run --args "H:\Workspaces\intelliJ\parkinglot_system\src\main\resources\input.txt"
```

## Commands to run unit test:
```
gradlew clean test
```
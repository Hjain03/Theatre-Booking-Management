# Theatre Booking Management

You run a small theater and each month, you have patrons mail in requests for pre-sale tickets.  You need to process these ticket requests and either tell them where their party will sit or explain to the patron why you can't complete their order.

## Getting Started

The project is developed using Automation build tool Maven so it would be easy to set it up and running in your local . Either you can import the project or Maven project on your eclipse or directly setting up the workspace with the project will work . 

### Prerequisites

1. Since the project is developed using Java Programming language so JDK(1.6 & above)/JRE is required in local to run the project.
2. It would be good to have Maven installed in your local to run and test the application easily .

### Installing

A step by step series of examples that tell you have to get a development env running

For installing Java use this URL 

```
https://www.java.com/en/download/help/download_options.xml
```

For installing Maven use this URL 

```
https://maven.apache.org/install.html
```

After successful installation of Prerequisites . In a development environment, use the following call to build and install artifacts into the local repository.

```
mvn install
```
Please run the project as Java Application to start its execution .
After running the application . You have to enter the input as per specified in the requirement 

Sample input:

```
6 6
3 5 5 3
4 6 6 4
2 8 8 2
6 6

Smith 2
Jones 5
Davis 6
Wilson 100
Johnson 3
Williams 4
Brown 8
Miller 12
```
Your program must produce results to standard output in the same order as the requests, with the name of the person who requested the ticket and either the row and section of the ticket or the explanations "Sorry, we can't handle your party" or "Call to split party."

Sample output:

```
Smith Row 1 Section 1
Jones Row 2 Section 2
Davis Row 1 Section 2
Wilson Sorry, we can't handle your party.
Johnson Row 2 Section 1
Williams Row 1 Section 1
Brown Row 4  Section 2
Miller Call to split party
```
In case of Wrong Input you will be prompted again to enter correct one as Theatre layout requires only Numbers not characters 
Sample input:

```
6 a
Sorry,The input given is expected to be Number seperated by space for section in rows e.g '4 4 5' . Please try again..!
6 6 

# 2
Sorry,The input given is expected to be Characters seperated by space followed by number e.g 'ABC 3' . Please try again..!
Smith 2
```
Sample output:

```
Smith Row 1 Section 1
```

## Running the tests

Explain how to run the automated tests for this system

### Break down into end to end tests

You can simple run the test cases using eclipse JUnit Test execution or Using Maven

For Example:

```
mvn test
```

### And coding style tests

You can test code compliances using SonarLint plugin available in eclipse .

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Authors

* **Himanshu Jain** - *Initial work* - [Hjain](https://github.com/Hjain03)

_How-To_ -- API-Testing Project Tutorial using Retrofit
========================================================

Overview
--------

Retrofit is a type-safe HTTP client for Android and Java � developed by Square (Dagger, Okhttp). It allows synchronous and asynchronous API calls in order to use retrieved information or test the endpoints the API has.

The main goal of this tutorial is to explain how Retrofit could help testing an API, automating the QA process while this API is created. For this purpose, this tutorial will use JSONplaceholder Public API (https://jsonplaceholder.typicode.com/) consuming all the endpoints this API has (for further information of endpoints and response/request structure, please visit the provided website).

If you need a Start-point structured project with all aspects contained in this tutorial, you may check the author's public repository and clone/download his project (https://github.com/ikariset/basic-retrofit-project).


## 0. What do I Need?

- A proper Java IDE, like Eclipse or NetBeans
- Basic usage of Maven (how to add dependencies to a Maven Project)
- Basic usage of TestNG (Basic annotations)

	
## 1. Installation:

### 1.a. Creating a Maven Project
First at all, you must create a maven project in order to get Retrofit and JSON Management dependencies. In Eclipse IDE, you can do this in File > New... > Other... (or push "Ctrl + N" button combo)
	
RF_TUT_01 ![alt text](https://github.com/adam-p/markdown-here/raw/master/src/common/images/icon48.png "Logo Title Text 1")
	
Then, choose "Maven project".
	
RF_TUT_02
	
If you need to start testing quickly, in this window check "Create a simple project (skip archetype selection)" and click in "Next" button. Otherwise, just click on "Next" button and select your preferred archetype of the displayed list. For this tutorial, a simple Maven Project works well.
	
RF_TUT_03
	
Define project's Group and Artifact ID of your preference. For this tutorial, "com.retrofitexample" and "tutorialretrofit" is used, respectively, as Group and Artifact ID.
	
RF_TUT_04
	
**-TIP-**
_Now that you have a proper Maven Project, you can choose Java Compiler for this project. For this, right-click in the project folder and go to "properties" option._
	
RF_TUT_05_RCLICK
	
Now, go to "Java compiler" section and uncheck "Use compliance from execution environment 'XXXX' on the 'Java Build Path'" option. Then, you can select your preferred Java version. Click on "Apply and close" for save those changes (1). Finally, click on "Yes" when rebooting prompt is shown(2).
	
RF_TUT_05
**(1)**

RF_TUT_06
**(2)**

	
### 1.b. Dependencies / POM

Retrofit can be added to our maven project as a dependency using maven pom.xml. Plus, you need to add some dependencies for JSON (de)serialization. For this example, used dependencies are as following:
```xml
  	<dependencies>
		<dependency>
		    <groupId>com.squareup.retrofit2</groupId>
		    <artifactId>retrofit</artifactId>
		    <version>2.5.0</version>
		</dependency>  
		<dependency>  
		    <groupId>com.squareup.retrofit2</groupId>
		    <artifactId>converter-gson</artifactId>
		    <version>2.5.0</version>
		</dependency>
		<dependency>
	    	<groupId>org.testng</groupId>
		    <artifactId>testng</artifactId>
		    <version>6.14.3</version>
		    <scope>test</scope>
		</dependency>
  	</dependencies>
```
	
**-TIP-** 
_If you need to download updated dependencies in your Retrofit API Testing project, take a look from mvnrepository (https://mvnrepository.com/) or Sonatype (https://search.maven.org/) Maven Search and search all packages shown above._
	
## II. Package Structure
		
The following package structure of tests was proposed in order to support new tests and endpoints, giving to this tutorial project scalability. Also, this structure supports external frameworks if needed.
	
Package description:
		1. classes: This package contains all the classes used for (de)serialization of JSON, obtained from file source or from API.
		2. commons: This package contains all common classes used in tests, mainly used for environment configuration.
		3. interfaces: This package contains all interfaces of all components that API has, defining all available methods (POST, PUT, GET, DELETE,...) and its structure.
		4. tests: This package contains all established tests in API Testing project, defined by Interfaces.
	
Additional to this, 'src/test/resources/' (5.) contains all JSON used for create new deserialized objects for data storage in API. 
	
RF_TUT_1_02
	
To create all this packages, right-click on the parent folder (For this tutorial, the parent folder is 'src/test/java') and assign the package name as preferred.
	
RF_TUT_1_01
	
## III. 'classes' Package: POJO classes creation

POJO classes are used for serialize and/or de-serialize marked texts like JSON or XML. In this tutorial, POJO classes are used with JSON files, stored in 'src/test/resources/classes/{pojo_class}', let 'pojo_class' be the class that use all JSON contained in this subfolder, e.g if 'users' class in 'src/test/java/classes' exists, it must be created a 'users' folder within 'src/test/resources/classes/' and store all JSON files used with the related class.
	
One point to consider is get all JSON used to make request to tested API. For this example, using JSONplaceholder Public API documentation (https://github.com/typicode/jsonplaceholder#Available-resources), object definition for 'users' and 'posts' components are as following:
	
	users
	{
	  "id": 1,
	  "name": "Leanne Graham",
	  "username": "Bret",
	  "email": "Sincere@april.biz",
	  "address": {
		"street": "Kulas Light",
		"suite": "Apt. 556",
		"city": "Gwenborough",
		"zipcode": "92998-3874",
		"geo": {
		  "lat": "-37.3159",
		  "lng": "81.1496"
		}
	  },
	  "phone": "1-770-736-8031 x56442",
	  "website": "hildegard.org",
	  "company": {
		"name": "Romaguera-Crona",
		"catchPhrase": "Multi-layered client-server neural-net",
		"bs": "harness real-time e-markets"
	  }
	}
	
	posts
	{
	  "userId": 1,
	  "id": 1,
	  "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
	  "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
	}
	
	RF_TUT_1_03
	
	For this task, a quick way to get POJO classes is using 'jsonschema2pojo' website (http://www.jsonschema2pojo.org/). Just access to this page, paste the request JSON for the required component and set up all option as showed on follow image:
	
	RF_TUT_1_04
	
	To generate all the POJO classes (class hierarchy included), just click on "Preview" or "Zip" buttons. In this tutorial, it'll be useful to click on the second option.
	
	RF_TUT_1_05
	
	-REMINDER- 
	Remember to always use names in singular while naming a class, like 'User', 'Post', etc. 
	
	Now, you need to place all generated classes in your project, in 'classes' package (previously created in II.)
	
	>Image Pending
	
	Repeat this for all components you need to test in the API.
	
	>Image Pending
	
	-TIP-
	If your target API response body is different than the request body (User and Post classes, for this example), you should consider to make a POJO class created with this response aditionally (Using API documentation required) to the request-based ones.
	
	
IV. 'interfaces' package: setting all endpoints
	
	The 'interfaces' package, as it described before, contains all services of the available components in an API, using Interfaces to this task. To create an interface, just right-click in 'interface' package and select 'Interface' in 'New' option.
	
	RF_TUT_1_06
	
	One more time, using JSONplaceholder Public API documentation (https://github.com/typicode/jsonplaceholder#Available-resources) or your API definition documentation, you can get all endpoints has in each components (POST, GET, DELETE,...). 'Users' and 'Posts' endpoints was used in this example project, defining POST and GET Methods in each interface.
	
	RF_TUT_1_07
	
	IV.a Interfaces structure
	
	For all interfaces included in this project, its structure is as following:
	
	RF_TUT_1_08
	
	'@GET' and '@POST' annotations define method type for each endpoint in current interface (marked in red), being also available other common methods like '@PUT' or '@DELETE'. It exists a general annotation that might be useful in case of less common methods -@HTTP(method = "{method type}", path = "/path/to/endpoint", hasBody = [true|false])- let 'method' be the method type used in the endpoint, 'path' the endpoint path of current endpoint and 'hasBody' a flag that determines body existance in current endpoint.
	
	-TIP- The @Headers annotation is useful for define aspects like authentication data, or content-type of body data.
	
	All methods in interfaces can get parameters, often used with body data or data identifiers (like User ID, or Post ID), which it has its proper annotations for body data ('@Body' annotation), query filters or identifiers ('@Query' annotation) or path definition ("@Path('{value}')", let 'value' the variable name defined in current endpoint path)
	
	Repeat this for all endpoint you need to define in the project.
	
	
V. 'tests' package: using all endpoints
	
	V.a. Creating Base configuration in 'commons package'.
	
	It's useful to create a class that contains all previous configuration, in order to use it in all test classes as parent class. 'BaseConfiguration.java' is built with TestNG methods and annotations, giving some actions after and before the proper test is running (mainly used for environment and reporting configuration, for this project).
	
	Basic structure of TestNG tests are defined with annotations in order like @AfterMethod --> @Test --> @BeforeMethod. Its exists more annotations focused on Test Groups and other steps, but for this example's purpose only the previously defined ones will be used. Then, 'BasicConfiguration' is created as following image shows:
	
	RF_TUT_2_01 to RF_TUT_2_03
	
	As you can see, BaseConfiguration attributes store API URL, ExtentReport suite location (Relative Path is preferred) and creation data, making easy to change this for all test cases, if it's needed (1).
	
	@AfterMethod was used for set up environment and report tools with data previously stored in attributes (2), and @BeforeMethod was used for store result from test in report previously initialized (3).
	
	
	V.b Creating your first test.
	
	If you follow this tutorial at this point, you would be capable of create all test cases you need to. Test case basic structure follows same steps between them, described as: 
	
		1. Get JSON data (stored in 'Request' package) and de-serialize it in its proper POJO class (Optional step, only if needed to send data).
		2. Create the 'retrofit' service implementing an interface stored in 'interface' package.
		3. Invoke a method from the service that call an endpoint in target API.
		4. Make Assertions using the API response as needed.
		
	Following image shows the previously described structure with more details, using proper methods from interfaces and classes previously created.
	
	RF_TUT_2_04
	
	-REMINDER- Keep an eye to the "extends" distinction assigning to 'BaseConfiguration' as parent class, in order to get all environment and extentreport configuration (as abroaded on chapter V.a).
	
	If you could create and run a test successfully, you possibly will get a new folder in your project folder with the ExtentReport. In this example, this folder is created in 'target' folder, called 'extent-report' (this can be defined in 'BaseConfiguration', changing 'extentLocation' attribute).
	
	DONE!
	
	At this moment, you can create all other Test Cases in order to verify the target API features works good, as needed to.
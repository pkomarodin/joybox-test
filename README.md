# JOYBOX TEST

<!-- GETTING STARTED -->
## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites

This is an example of how to list things you need to use the software and how to install them.
* Java
* Maven
  ```sh
  mvn clean install
  ```

### Run Service

_Below is an example of how to run this project._

   ```sh
   mvn spring-boot:run
   ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>



### API

_Below is an list of API in this project._

1. Get List Book by Subject 
   ```sh
   http:localhost:8088/v1/book
   ```
   Request Param
   ```sh
   ?subject={subject-keyword}
   ```
2. Submit Book Pick Up Schedule
   ```sh
   http:localhost:8088/v1/schedule/submit   
   ```
   Request Body
   ```sh
   {
      "user": String,
      "book": {
        "title": String,
        "author": String,
        "edition_count": Integer
      },
      "pick_up_date": Date (Format : "yyyy-MM-dd HH:mm:ss")
   }
   ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>


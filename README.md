<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a name="readme-top"></a>

<!-- PROJECT SHIELDS -->
[![LinkedIn][linkedin-shield]][linkedin-url]

<!-- PROJECT LOGO -->
<br />
<div align="center">

  <h3 align="center">Car Rental Booking and Management System</h3>

  <p align="center">
    A comprehensive system for managing car rentals, bookings, clients, and insurance options.
  </p>
</div>

<br><br>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About The Project

The Car Rental Booking and Management System is a comprehensive application designed to streamline the management of car rentals, bookings, clients, and insurance options. This system is built using JavaFX and CSS for the user interface, Scene Builder for the frontend, and MySQL for the database backend, providing a robust and scalable solution.


[![find Vehicle Page][findVehicles]](project_images/findVehicles.png)
<br><br>

[![Manage Vehicle Page][manageVehicles]](project_images/manageVehicles.png)

#### Key Features:


  <li>User Interface (UI) Applications:  Modern UI with JavaFX, enhanced with CSS for styling and FXML for layout.</li>



  <li>MYSQL Database:  Store and retrieve data reliably with support for transactions.</li>


  <li>Management:  Add, update, delete vehicle, client, reservation information.</li>


  <li>Signup/Login mechansim:  Validates users with their credentials</li>



  <li>Thread Safety:  Synchronized methods, ExecutorService, Transaction management, Scheduled tasks.</li>



  <li>Design Patterns:  MVC, DAO, Strategy.</li>



#### Built With

* [Java](https://www.java.com/)
* [JavaFX](https://openjfx.io/)
* [MySQL](https://www.mysql.com/)
* [Maven](https://maven.apache.org/)
* [SceneBuilder](https://gluonhq.com/products/scene-builder/)

<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running, follow these simple steps.

### Prerequisites

You will need the following software installed:
* [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html)
* [Git](https://git-scm.com/downloads)
* [Maven](https://maven.apache.org/download.cgi)

* [Set up Maven Environment for Windows](https://www.qamadness.com/knowledge-base/how-to-install-maven-and-configure-environment-variables/)
* [Set up Maven Environment for Mac/Linux](https://www.baeldung.com/install-maven-on-windows-linux-mac)

### Installation

1. **Clone the repo using Terminal/Command Prompt**:

     ```sh
     git clone https://github.com/shyke0611/Car_Rental_Project.git
     ```

2. **Navigate to the project directory**:
     ```sh
     cd Car_Rental_Project
     ```

3. **Install Maven dependencies**:
     ```sh
     mvn clean install
     ```

4. **Run the application**:
     ```sh
     mvn clean javafx:run
     ```

<!-- USAGE EXAMPLES -->
## Usage
Entity-Relation Diagram of How Project was Planned:
[![Manage Vehicle Page][ERD]](project_images/ERD.png)

### Quick Demo of Project Using Custom Runtime ###
Clone the project repository, move to the project directory, and then run the code below:

**For Mac Users:**
  ```sh
    ./custom-runtime-macos/bin/java -jar target/car_rental_book_and_manage-1.0.jar
   ```

**For Windows Users:**
 ```sh
    custom-runtime-windows/bin/java -jar target/car_rental_book_and_manage-1.0.jar
   ```
  



<!-- ROADMAP -->
## Roadmap

- [x]  Implement features for the rental management section
- [x]  Implement features for the rental booking section
- [x]  Apply user experience 
- [ ] Integrate additional payment methods
- [ ] Expand to client-web server

<!-- REFLECTION -->
## Reflection

#### Context and Motivation
This Car Rental Booking and Management System was a month long personal project aimed at refining my Java skills. I undertook this project to deepen my understanding of software development using java and user experience (UX) design principles. It provided an excellent opportunity to expand my knowledge in both technical and UX aspects.

#### Project Goals
The goal was to build a comprehensive system for managing car rentals, bookings, clients, and insurance options. I aimed to create a robust application using JavaFX for the user interface and MySQL for the database backend, ensuring a seamless and user-friendly experience.

#### Challenges and Learning Experience
A major challenge was ensuring thread safety and preventing race conditions, requiring extensive research into synchronization mechanisms for managing concurrent operations. Addressing these issues enhanced my understanding of Java concurrency and improved my problem-solving skills.


<!-- CONTACT -->
## Contact

Email - hyungkenine2003@gmail.com

Project Link - [https://github.com/shyke0611/Car_Rental_Project.git](https://github.com/shyke0611/Car_Rental_Project.git)

<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

* [othneildrew - Best README Template](https://github.com/othneildrew/Best-README-Template)
* [Maven Repository](https://mvnrepository.com/)
* [Font Awesome](https://fontawesome.com)
* [Font Awesome Cheatsheet](https://fontawesome.com/v4/cheatsheet/)

<!-- MARKDOWN LINKS & IMAGES -->
[contributors-shield]: https://img.shields.io/github/contributors/github_username/repo_name.svg?style=for-the-badge
[contributors-url]: https://github.com/github_username/repo_name/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/github_username/repo_name.svg?style=for-the-badge
[forks-url]: https://github.com/github_username/repo_name/network/members
[stars-shield]: https://img.shields.io/github/stars/github_username/repo_name.svg?style=for-the-badge
[stars-url]: https://github.com/github_username/repo_name/stargazers
[issues-shield]: https://img.shields.io/github/issues/github_username/repo_name.svg?style=for-the-badge
[issues-url]: https://github.com/github_username/repo_name/issues
[license-shield]: https://img.shields.io/github/license/github_username/repo_name.svg?style=for-the-badge
[license-url]: https://github.com/github_username/repo_name/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/andrew-hk-shin
[findVehicles]: project_images/findVehicles.png
[manageVehicles]: project_images/manageVehicles.png
[ERD]: project_images/ERD.png
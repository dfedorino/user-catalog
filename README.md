# User Catalog
A simple user management application. Demo project for Spring Boot. Technology stack:
1. Java 8;
2. Maven;
3. Spring Boot 2.4.5;
4. Spring Web;
5. Spring Data JPA;
6. H2 Database;

## Project's structure
The project is based on the 3-tier architecture which consists of the following tiers:
1. **Presentation tier** - the user interface and communication layer of the application, where the end user interacts with 
   the application. Its main purpose is to display information to and collect information from the user.
   For the presentation tier the MVC pattern was used to organize the structure of the interacting with users through a 
   web service:
    1. _Model_ - representation of the business object, a user with all properties required for business. This layer is 
       linked to the Application tier as models are usually loaded from a persistent storage.
    2. _View_ - organises output/display for a user of the current project application. It is responsible for receiving 
       user input and displaying data according to the output;
    3. _Controller_ - deals with requests from outside, validates it, filters it and refers to an appropriate Model. 
       After getting relevant data from the Model layer, controller creates a response and sends it to the View layer.
       
2. **Application tier** - also known as the logic tier or middle tier. Application tier is responsible for processing data, 
   received from the Presentation tier and store/update/search/etc. data in the Data tier.
   
3. **Data tier**, sometimes called database tier, is where the information processed by the application is stored and 
   managed.

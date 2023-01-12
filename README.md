## TO GET START
Developers could download or copy the project to start their development. To make the framework satisfy your requirements, you could change simply change the setting. And then, you are all good to start function or business logic development.

# About BaseSpringProject
The base java framework is a maven project, which has integrated the popular plugins or components for SpringBoot framework. This project provides the useful framework that is easily to access and CI(continuous integration). It is a server-side service, which includes two parts that enable the service connect to database and expose the API to the front service.

This project is able to connect MySQL database by using popular plugin called MyBatisPlus, which saved the development on CRUD related function. But it still need to write the SQL by yourself in XML file while perform the table join operation. On the other hand, the project also supports connect to MongoDB in different branch.

For visible API documents, this project has integrated Swagger 3 for auto API document generation. And Redis has been used for in-memory data storage, especially for high frequent accessiable data such as user login token or dynamic good storage management.

## Structure
The structure are combined by controller, service and data mapping. API related information check has been implemented in different controllers. The Business logic basically are implemented in serviceImp class. The data related operation usally implement in data mapping xml for each table.

## Purpose
With the baseSpringProject, the developers could start the project with a useful template and reduce the time on framework construction.

## For the support
Please leave me message through Github or submit the issue to resolve problems or get help from me. 

## Further update
Optimised the util class for Redis and Database, etc.

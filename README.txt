To use the sample application, clone this repository (or extract the zip file to
your local directory. The application is broken into four services, a Skills and
Scheduling app for tracking consultants' skill levels and customer commitments,
an example using the reactive client to invoke both of these services, and then
a chat application built using Server Sent Events (SSE). 

Run the Maven command 'mvn install' from the directory that contains the 
extracted .zip files to build the project and install it to your local directory. 
This creates the directory contain the sample source code and configuration for 
the liberty server, jaxrs21samples, and it starts the server. 

To start and stop the server, issue the following commands from the
<extract-directory> directory:
      mvn liberty:start-server
      mvn liberty:stop-server


To access the Reactive Client sample application, visit the following URL from 
your browser: 
      http://localhost:9080/RxClient/index.html 

To access the Server Sent Events (SSE) sample app, visit the following URL from
your browser (ideally from more than one browser):
      http://localhost:9080/SseChat/index.html

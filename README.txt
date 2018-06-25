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

This page will allow you to book a consultant with a set of skills for a certain
date range.  This uses the JAX-RS 2.1 Reactive Client APIs to make multiple REST
requests to the Skills and Scheduling apps.  The sample code for the Reactive
Client is available here:
      https://github.com/andymc12/sample-jaxrs21/blob/master/RxClient/src/main/java/io/openliberty/sample/rxclient/ExampleResource.java


To access the Server Sent Events (SSE) sample app, visit the following URL from
your browser (ideally from more than one browser):
      http://localhost:9080/SseChat/index.html

You can see how the JAX-RS 2.1 SSE APIs make this work by viewing the sample
code here:
      https://github.com/andymc12/sample-jaxrs21/blob/master/SseChat/src/main/java/io/openliberty/sample/ssechat/ChatResource.java

The SSE client is an HTML page that uses JavaScript:
      https://github.com/andymc12/sample-jaxrs21/blob/master/SseChat/src/main/webapp/index.html

Note that at the time I wrote this app, Microsoft browsers did not provide support
for SSEs - I have tested this successfully on recent versions of Safari, Chrome and
Firefox.

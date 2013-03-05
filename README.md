## Alfresco Api Sample app  
This is a basic server-side Java servlet application that uses the Alfresco API.  It uses:

1. [Alfresco Spring Social](https://github.com/Alfresco/spring-social-alfresco)
2. [OpenCMIS](http://chemistry.apache.org/java/opencmis.html)
3. [Alfresco OpenCMIS Extension](https://code.google.com/a/apache-extras.org/p/alfresco-opencmis-extension/)
4. Alfresco Cloud OAuth2

The application contains an embedded jetty instance that will be started on port 8090.  You can run the sample code using either Gradle (the gradle wrapper is used so you don't need an install first) or Maven.

### Application setup
An account on the [Alfresco Developer Portal](https://developer.alfresco.com) is required.  You will need to register an application on the portal.

* **Application name** - this will be shown to a user of your application when they authorize your app.
* Api Key - The **client_id** of your application (auto-generated)
* Key Secret - The **client_secret** of your application (auto-generated)
* **Callback URL** - For this application the url is `http://localhost:8090/alfapi/dance/callback`  

**You will need to edit src/main/resources/config.properties** - enter the client_id, client_secret and redirect_uri (callback url) that you registered in the [Alfresco Developer Portal](https://developer.alfresco.com).

### Running the Application

#### Gradle users
* Type: `gradlew jettyRun` on windows or `./gradlew jettyRun` on mac/linux.  
* You should see the line: `Running at http://localhost:8090/alfapi`
* Open your webbrowser at <http://localhost:8090/alfapi>
 
#### Maven users
* Type: `mvn jetty:run`  
* You should see the line: `Running at http://localhost:8090/alfapi`
* Open your webbrowser at <http://localhost:8090/alfapi>

#### Eclipse users
* To generate eclipse config/project files using gradle: `gradlew eclipse`  
* To generate eclipse config/project files using maven: `mvn eclipse:eclipse`  
  
### Video presentation that uses this sample code
At Alfresco Devcon 2012 there was a presentation that explained the Alfresco API, 
how to register as a developer and how to use the api.  This project was used as the sample
code during the presentation.

https://www.youtube.com/watch?v=s6o-6_tjz2Q&feature=share&list=PLyJdWuUHM3ijuAepDT3d8HPCzK39W3T1p

### License
Copyright (C) 2012 Alfresco Software Limited

This file is part of an unsupported extension to Alfresco.

Alfresco Software Limited licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
# ASR Trivial

This web application consists on a number of quizes coming from [Open Trivia Database](https://opentdb.com). It is built in Java EE with [IBM Open Liberty](https://openliberty.io) web server.

It is deployed in IBM Cloud as a [Cloud Foundry](https://www.ibm.com/cloud/cloud-foundry) application (Liberty for Java). The webapp can be accessed here:

https://trivial-asr.eu-gb.mybluemix.net

This web application is based in the following microservices, provided by IBM Cloud:

- [Cloudant NoSQL Database](https://www.ibm.com/cloud/cloudant)
- [Watson Language Translator](https://www.ibm.com/cloud/watson-language-translator)
- [Watson Language Text to Speech](https://www.ibm.com/cloud/watson-text-to-speech)
- [App ID](https://www.ibm.com/cloud/app-id)

Moreover, it uses IBM Cloud CI/CD tools to use DevOps and improve the coding experience.

## Run locally

In order to have all microservices running correctly and integrated with the application, you need an [IBM Cloud account](https://cloud.ibm.com/login).

Next, you will need to create the services mentioned before. Once created, you can generate credentials for local use, generated as a JSON file. You should get the `"apikey"` and the `"url"` from each service and copy them in their corresponding properties file of [resources](src/main/resources). Remember to remove "template" from the filenames.

The App ID service is more complicated. I recommend reading the documentation and watching some videos of this [YouTube playlist](https://www.youtube.com/playlist?list=PLbAYXkuqwrX2WLQqR0LUtjT77d4hisvfK).

Finally, and assuming that [Java 1.8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html) and [Maven](https://maven.apache.org/download.cgi) are correctly installed, you can clone this repository and run the following commands in a terminal:

```
cd trivial-asr
mvn clean install
mvn liberty:dev -DskipTests=true
```

The web server will be listening at `http://localhost:9080`.

## Run on IBM Cloud

For this, you will need to create a Cloud Foundry application on IBM Cloud. You may need to modify the configurations of the [manifest.yaml](manifest.yaml) (possibly `name`, `path`, `routes`, and `services`).

Before deploying the application, you should run the scripts `build_server.sh` and `prod_server.sh`. These scripts will produce a ZIP file with the packaged Liberty server.

Assuming that Cloud Foundry CLI is installed in your system, the folloging commands will deploy your application to IBM Cloud:

```
cf api <the API domain where the Cloud Foundry app is created>
cf login # You will be asked for email and password of the IBM Cloud account
cf push
cf logout
```

## Run with CI/CD

For this task, you have to enable [Continuous Delivery](https://www.ibm.com/cloud/continuous-delivery) in the Cloud Foundry application, from IBM Cloud. You will need to specify the Git URL and the commands to execute on the stages you define.

This is the advanced level. If you have arrived here, sure you know how to configure CI/CD in your application.

Hope it is useful! :smile:

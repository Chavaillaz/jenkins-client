# Jenkins Client

[Jenkins]: https://www.jenkins.io

[CompletableFuture]: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/CompletableFuture.html

[Jackson]: https://github.com/FasterXML/jackson

[JavaHttp]: https://openjdk.org/groups/net/httpclient/intro.html

[ApacheHttp]: https://hc.apache.org/httpcomponents-client-5.2.x/

[OkHttp]: https://square.github.io/okhttp/

[Vertx]: https://vertx.io/docs/vertx-web-client/java/

![Dependency Check](https://github.com/chavaillaz/jenkins-client/actions/workflows/snyk.yml/badge.svg)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.chavaillaz/jenkins-client/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.chavaillaz/jenkins-client)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

This library allows you to interact with a Jenkins instance and choose which HTTP client to use. It is also easily
extendable, allowing you to have custom clients. All requests are performed asynchronously and return a
[CompletableFuture][CompletableFuture]. The serialization and deserialization of domain objects are performed
using [Jackson][Jackson].

Presently, it supports the following HTTP clients:

- [Java HTTP client][JavaHttp] (included since Java 11)
- [Apache HTTP client][ApacheHttp] 5.5
- [OkHttp client][OkHttp] 4.12
- [Vert.x client][Vertx] 4.5

Note that this library requires at least **Java 17** and has been tested with
a [Jenkins instance version 2.426.1][Jenkins].

## Installation

The dependency is available in maven central (see badge for version):

```xml
<dependency>
    <groupId>com.chavaillaz</groupId>
    <artifactId>jenkins-client</artifactId>
</dependency>
```

Don't forget to also declare the HTTP client you want to use as a dependency (see below), as it is only indicated as
optional in the project, to avoid gathering them all although only one is needed.

### Java HTTP client

It does not require any dependency (already in Java).

### Apache HTTP client

It requires the following dependency:

```xml
<dependency>
    <groupId>org.apache.httpcomponents.client5</groupId>
    <artifactId>httpclient5</artifactId>
    <version>5.2.x</version>
</dependency>
```

### OkHttp client

It requires the following dependency:

```xml
<dependency>
    <groupId>com.squareup.okhttp3</groupId>
    <artifactId>okhttp</artifactId>
    <version>4.12.x</version>
</dependency>
```

### Vert.x client

It requires the following dependency:

```xml
<dependency>
    <groupId>io.vertx</groupId>
    <artifactId>vertx-web-client</artifactId>
    <version>4.5.x</version>
</dependency>
```

## Usage

### Features

- **[JobApi](src/main/java/com/chavaillaz/client/jenkins/api/JobApi.java) -
  Everything for folders, views, jobs and their builds**
    - Folders
        - `getFolder(Path path)`
        - `getFolderConfiguration(Path path)`
        - `createFolder(Path path, String folderName)`
        - `renameFolder(Path path, String folderName, String newName)`
        - `deleteFolder(Path path, String folderName)`
    - Views
        - `getView(Path path, String viewName)`
        - `getViewConfiguration(Path path, String viewName)`
        - `createView(Path path, String viewName)`
        - `updateViewConfiguration(Path path, String viewName, String configXML)`
        - `deleteView(Path path, String viewName)`
    - Jobs
        - `getJob(Path path, String jobName)`
        - `getJobConfiguration(Path path, String jobName)`
        - `getJobDescription(Path path, String jobName)`
        - `createJob(Path path, String jobName, String configXML)`
        - `updateJobConfiguration(Path path, String jobName, String configXML)`
        - `renameJob(Path path, String jobName, String newName)`
        - `enableJob(Path path, String jobName)`
        - `disableJob(Path path, String jobName)`
        - `deleteJob(Path path, String jobName)`
    - Builds
        - `getLastBuildNumber(Path path, String jobName)`
        - `getBuild(Path path, String jobName, int buildNumber)`
        - `getArtifact(Path path, String jobName, int buildNumber, String artifactPath)`
        - `getTestReport(Path path, String jobName, int buildNumber)`
        - `getTestCoverageReport(Path path, String jobName, int buildNumber)`
        - `getConsoleOutput(Path path, String jobName, int bufferOffset)`
        - `getConsoleOutput(Path path, String jobName, int buildNumber, int bufferOffset)`
        - `buildJob(Path path, String jobName)`
        - `buildJob(Path path, String jobName, Map<Object, Object> properties)`
        - `stopBuild(Path path, String jobName, int buildNumber)`
- **[PipelineApi](src/main/java/com/chavaillaz/client/jenkins/api/PipelineApi.java) -
  Everything for pipelines**
    - `getRunHistory(Path path, String pipelineName, String branchName)`
    - `getRun(Path path, String pipelineName, String branchName, long buildNumber)`
    - `getRunPendingActions(Path path, String pipelineName, String branchName, long buildNumber)`
    - `getRunArtifacts(Path path, String pipelineName, String branchName, long buildNumber)`
    - `getRunNode(Path path, String pipelineName, String branchName, long buildNumber, String nodeId)`
    - `getRunNodeLog(Path path, String pipelineName, String branchName, long buildNumber, String nodeId)`
- **[PluginApi](src/main/java/com/chavaillaz/client/jenkins/api/PluginApi.java) -
  Everything for plugins**
    - `getPlugins()`
    - `getPlugins(Integer depth)`
    - `installPlugin(String pluginId)`
- **[QueueApi](src/main/java/com/chavaillaz/client/jenkins/api/QueueApi.java) -
  Everything for queues**
    - `getQueueItems()`
    - `getQueueItem(long queueId)`
    - `getQueueItemOptional(long queueId)`
    - `cancelQueueItem(long id)`
- **[SystemApi](src/main/java/com/chavaillaz/client/jenkins/api/SystemApi.java) -
  Everything for system management**
    - `getSystemInfo()`
    - `getOverallLoad()`
    - `quietDown()`
    - `cancelQuietDown()`
    - `restart()`
    - `safeRestart()`
    - `exit()`
    - `safeExit()`
    - `reload()`
- **[UserApi](src/main/java/com/chavaillaz/client/jenkins/api/UserApi.java) -
  Everything for users**
    - `getUser()`
    - `generateToken(String tokenName)`
    - `revokeToken(String tokenUuid)`

### Client instantiation

Instantiate the Jenkins client of your choice by giving your Jenkins instance URL. Depending on your needs, you may also
want to add authentication with an access token using `withTokenAuthentication` or with username and password using
`withUserAuthentication`. If you need to connect via a proxy, you can specify it using `withProxy`.
Below an example for each HTTP client:

- [JavaHttpJenkinsClient](src/main/java/com/chavaillaz/client/jenkins/java/JavaHttpJenkinsClient.java)

```java
JenkinsClient client = JenkinsClient.javaClient("https://jenkins.mycompany.com")
        .withUserAuthentication("myUsername", "myPassword")
        .withProxy("http://proxy.mycompany.com:1234");
```

- [ApacheHttpJenkinsClient](src/main/java/com/chavaillaz/client/jenkins/apache/ApacheHttpJenkinsClient.java)

```java
JenkinsClient client = JenkinsClient.apacheClient("https://jenkins.mycompany.com")
        .withUserAuthentication("myUsername", "myPassword")
        .withProxy("http://proxy.mycompany.com:1234");
```

- [OkHttpJenkinsClient](src/main/java/com/chavaillaz/client/jenkins/okhttp/OkHttpJenkinsClient.java)

```java
JenkinsClient client = JenkinsClient.okHttpClient("https://jenkins.mycompany.com")
        .withUserAuthentication("myUsername", "myPassword")
        .withProxy("http://proxy.mycompany.com:1234");
```

- [VertxHttpJenkinsClient](src/main/java/com/chavaillaz/client/jenkins/vertx/VertxHttpJenkinsClient.java)

```java
JenkinsClient client = JenkinsClient.vertxClient("https://jenkins.mycompany.com")
        .withUserAuthentication("myUsername", "myPassword")
        .withProxy("http://proxy.mycompany.com:1234");
```

From this `JenkinsClient` you will then be able to get the desired APIs described in the [feature chapter](#features).

### Iterable results

When requesting a list of elements, the client returns an object encapsulating this list, sometimes with more pieces of
information depending on the Jenkins endpoint called. From this object, you can iterate directly on its child elements.

## Contributing

If you have a feature request or have found a bug, you can:

- Write an issue
- Create a pull request

### Code style

The code style is based on the default one from IntelliJ, except for the following cases:

#### Imports

Imports are ordered as follows:

- All static imports in a block
- Java non-static imports in a block
- All non-static imports in a block

A single blank line separates every block. Within each block the imported names appear in alphabetical sort order.
Wildcard imports, static or otherwise, are not used.

#### Arrangements

The attributes of **domain classes** are ordered alphabetically.

## License

This project is under Apache 2.0 License.
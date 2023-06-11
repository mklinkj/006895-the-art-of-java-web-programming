# 26장. 예제 Maven 적용 및 Spring 4 버전업



## 실행

Jetty Maven 플러그인을 적용하였기 때문에, Maven 실행가능 환경이면 다음명령으로 바로 웹서버를 띄울 수 있다.

```sh
mvn clean jetty:run
```

* Java 17 환경 이상에서 실행 필요



## 라이브러리

기존 WEB-INF/lib의 내용을 보면... 

* Spring
  * spring.jar는  Spring 2.0 라이브러리들의 fat jar 같다. 여러 라이브러리들이 모여있음.
  * 그런데 Spring 3.0도 있음.
  * Maven을 적용하고 Spring 4의 마지막 버전인 [4.3.30.RELEASE](https://mvnrepository.com/artifact/org.springframework/spring-core/4.3.30.RELEASE)를 적용.
    * Spring 4 버전으로 먼저 해보고 잘 안된다면 [3.2.18.RELEASE](https://mvnrepository.com/artifact/org.springframework/spring-core/3.2.18.RELEASE)를 적용하자.
      *  Spring 3 버전을 쓴다면... 지금 내 환경에서 사용하기가 힘들다.. 일단 Spring 4로 설정하자.
    * Spring 6을 적용할 수도 있는데, 이미 Depreacted된 메서드도 있고, 바꿀 부분이 많아진다.
* Log4j 
  * 1.2.16  
* Tiles
  * 2.2.2
* ojdbc 6
  * 예제를 바로 확인할 수 있게 Oracle에서 HSQLDB 메모리 모드로 전환해서 사용하자.

##### 라이브러리 목록

```
cglib-nodep-2.1_3.jar                                   cglib-nodep-2.2.jar
com.springsource.javax.validation-1.0.0.GA.jar          com.springsource.org.aopalliance-1.0.0.jar
com.springsource.org.aspectj.weaver-1.6.8.RELEASE.jar   commons-beanutils-1.8.3.jar
commons-dbcp-1.4.jar                                    commons-dbcp.jar
commons-digester-2.0.jar                                commons-fileupload-1.2.2.jar
commons-io-2.0.1.jar                                    commons-logging-1.1.1.jar
commons-logging.jar                                     commons-pool-1.5.6.jar
commons-pool.jar                                        json_simple-1.1.jar
jstl.jar                                                log4j-1.2.16.jar
mybatis-3.0.5.jar                                       mybatis-spring-1.0.1.jar
mysql-connector-java-3.0.14-production-bin.jar          ojdbc6.jar
org.springframework.aop-3.0.6.RELEASE.jar               org.springframework.asm-3.0.6.RELEASE.jar
org.springframework.aspects-3.0.6.RELEASE.jar           org.springframework.beans-3.0.6.RELEASE.jar
org.springframework.context-3.0.6.RELEASE.jar           org.springframework.core-3.0.6.RELEASE.jar
org.springframework.expression-3.0.6.RELEASE.jar        org.springframework.jdbc-3.0.6.RELEASE.jar
org.springframework.transaction-3.0.6.RELEASE.jar       org.springframework.web-3.0.6.RELEASE.jar
org.springframework.web.servlet-3.0.6.RELEASE.jar       slf4j-api-1.5.8.jar
slf4j-jdk14-1.5.8.jar                                   spring.jar
standard.jar                                            tiles-api-2.2.2.jar
tiles-core-2.2.2.jar                                    tiles-jsp-2.2.2.jar
tiles-servlet-2.2.2.jar                                 tiles-template-2.2.2.jar
```


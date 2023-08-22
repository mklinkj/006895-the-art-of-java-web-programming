#!/bin/sh
# Jetty 실행시는 JSTL 라이브러리를 Provided 로 설정하는 프로필로 실행시킨다.
./mvnw clean -DskipTests jetty:run -Pjetty-run
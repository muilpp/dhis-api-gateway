From openjdk:11
copy ./target/test-0.0.1-SNAPSHOT.jar test-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","test-0.0.1-SNAPSHOT.jar"]
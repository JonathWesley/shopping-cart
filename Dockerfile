FROM openjdk:11

ADD shopping-cart-0.0.1-SNAPSHOT.jar shopping-cart.jar

ENTRYPOINT ["java", "-jar","shopping-cart.jar"]

EXPOSE 9060
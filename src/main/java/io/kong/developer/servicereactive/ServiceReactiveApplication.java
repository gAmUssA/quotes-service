package io.kong.developer.servicereactive;

import com.github.javafaker.Faker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.dekorate.kubernetes.annotation.Env;
import io.dekorate.kubernetes.annotation.KubernetesApplication;
import io.dekorate.kubernetes.annotation.Port;

@SpringBootApplication
@KubernetesApplication(
    ports = @Port(name = "web", containerPort = 8080),
    envVars = @Env(name = "QUOTE_SERVICE", value = "backToFuture")
)
public class ServiceReactiveApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServiceReactiveApplication.class, args);
  }
}


@RestController
class QuoteEndpoint {

  @Value("${QUOTE_SERVICE:backToFuture}")
  private String quoteConfig;

  @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<Response> hello() {
    final Faker faker = Faker.instance();
    final String quote;

    switch (quoteConfig.toUpperCase()) {
      case "CHUCK":
        quote = faker.chuckNorris().fact();
        break;
      case "HOBBIT":
        quote = faker.hobbit().quote();
        break;
      default:
        quote = faker.backToTheFuture().quote();
    }
    return new ResponseEntity<>(new Response(quote), HttpStatus.OK);
  }
}

class Response {

  private final String message;

  Response(final String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
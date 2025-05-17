package org.example.thesisdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ThesisDemoApplicationTests {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void helloEndpointReturnsExpectedMessage() {
    ResponseEntity<String> response = restTemplate.getForEntity("/hello", String.class);
    assertThat(response.getBody()).contains("This is a message from a Spring Application running in a Kubernetes cluster!");
  }
}

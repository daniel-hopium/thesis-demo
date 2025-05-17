package org.example.thesisdemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DemoController {

  @GetMapping("/hello")
  public String hello() {
    log.info("Received a request to /hello endpoint");
    return "This is a message from a Spring Application running in a Kubernetes cluster!";
  }
}
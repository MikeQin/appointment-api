package com.appointment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@Slf4j
public class Config {

  public Config() {
    log.info("Config ...");
  }
}

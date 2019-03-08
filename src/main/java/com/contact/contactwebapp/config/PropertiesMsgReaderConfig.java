/**
 * 
 */
package com.contact.contactwebapp.config;

import com.contact.contactwebapp.util.general.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author yashjain
 *
 */
@Component
@Configuration
public class PropertiesMsgReaderConfig {

  private static Logger logger = LogManager.getLogger(PropertiesMsgReaderConfig.class);

  @Value("${properties.message.validation}")
  private String propertiesFileLoc;

  @Bean
  public Properties propertiesReader() {
    Properties props = new Properties();
    InputStream configStream = PropertiesMsgReaderConfig.class.getResourceAsStream(propertiesFileLoc);
    try {
      props.load(configStream);
      configStream.close();
    } catch (IOException e) {
      logger.error(StringUtil.getStackTraceInStringFmt(e));
    }

    return props;
  }

}
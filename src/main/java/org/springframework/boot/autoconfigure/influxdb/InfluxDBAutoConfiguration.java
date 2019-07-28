package org.springframework.boot.autoconfigure.influxdb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import info.boruisi.platform.data.influxdb.client.InfluxDBClient;
import info.boruisi.platform.data.influxdb.client.InfluxDBFactoryBean;
import info.boruisi.platform.data.influxdb.config.InfluxDBProperties;

@Configuration
@EnableConfigurationProperties(InfluxDBProperties.class)
public class InfluxDBAutoConfiguration {

  private final InfluxDBProperties properties;

  @Autowired
  public InfluxDBAutoConfiguration(InfluxDBProperties properties){
    this.properties = properties;
  }

  @Bean
  @ConditionalOnMissingBean
  InfluxDBFactoryBean influxDBFactory() throws Exception{
    return new InfluxDBFactoryBean(properties);
  }

  @Bean
  @ConditionalOnMissingBean
  InfluxDBClient influxDBClient(InfluxDBFactoryBean influxDBFactory) throws Exception{
    return new InfluxDBClient(influxDBFactory);
  }
    
}
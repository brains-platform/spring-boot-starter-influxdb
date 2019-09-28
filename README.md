# spring-boot-starter-influxdb

[![Build Status](https://travis-ci.org/brains-platform/spring-boot-starter-influxdb.svg?branch=master)](https://travis-ci.org/brains-platform/spring-boot-starter-influxdb)
[![codecov](https://codecov.io/gh/brains-platform/spring-boot-starter-influxdb/branch/master/graph/badge.svg)](https://codecov.io/gh/brains-platform/spring-boot-starter-influxdb)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/6ae034294c8e4075877b1c3b1cb9258f)](https://app.codacy.com/app/dagmom/spring-boot-starter-influxdb?utm_source=github.com&utm_medium=referral&utm_content=brains-platform/spring-boot-starter-influxdb&utm_campaign=Badge_Grade_Dashboard)

InfluxDB Client for SpringBoot Starter

## Usage

### import

pom.xml
```xml
<dependency>
  <groupId>info.boruisi.platform.data</groupId>
  <artifactId>spring-boot-starter-influxdb</artifactId>
  <version>${influxdb-starter.version}</version>
</dependency>
```

application.yml
```yaml
spring:
  data:
    influxdb:
      url: http://127.0.0.1:8086
      username: admin
      password: 123456
      database: monitor_db
      retention-policy: autogen
```
### use api

Writing using point
```java
@Autowired
private InfluxDBClient influxDBClient;
Point point = Point.measurement("disk")
                   .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                   .addField("idle", 90L)
                   .addField("user", root)
                   .tag("path", "/root")
                   .build();
influxDBClient.write(point);
```

Writing using POJO, Read InfluxDB java-client Official User Guide, Add @Measurement,@TimeColumn and @Column annotations:
```java
// POJO 
influxDBClient.write(POJO pojo);
```

We also support write data by udp and query api etc. Just read InfluxDBClient code.

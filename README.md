# spring-boot-starter-influxdb
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

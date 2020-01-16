package info.boruisi.platform.data.influxdb.client;

import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.dto.QueryResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.influxdb.InfluxDBAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfluxDBAutoConfiguration.class)
@Slf4j
public class InfluxDBClientTest extends TestCase {


    @Autowired
    private InfluxDBClient influxDBClient;

    @Test
    public void testQuery(){
        QueryResult result = influxDBClient.query("select * from cpu order by time desc limit 1");
        assertEquals("query result",1,result.getResults().size());
        log.info(result.getResults().get(0).toString());
    }

}

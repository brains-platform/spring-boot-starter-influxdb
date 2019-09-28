package info.boruisi.platform.data.influxdb.client;

import info.boruisi.platform.data.influxdb.config.InfluxDBProperties;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

/**
 * InfluxDB Connection Factory
 * @author guhao
 * @since  2019/07/20
 */
public class InfluxDBFactoryBean implements FactoryBean<InfluxDB>,InitializingBean,DisposableBean {

  private static final Logger logger = LoggerFactory.getLogger(InfluxDBFactoryBean.class);

  private InfluxDBProperties properties;
  private InfluxDB influxDB;

  public InfluxDBFactoryBean(final InfluxDBProperties properties) {
    this.properties = properties;
  }

  @Override
  public void destroy() throws Exception {
    influxDB.close();
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    Assert.notNull(properties, "Property 'properties' is required");
  }

  @Override
  public InfluxDB getObject() throws Exception {
    return buildInfluxDB();
  }

  @Override
  public Class<InfluxDB> getObjectType() {
    return InfluxDB.class;
  }

  @Override
  public boolean isSingleton() {
    return false;
  }
    
  public InfluxDBProperties getProperties(){
    return properties;
  }

  /**
   * 初始化InfluxDB连接
   * @return
   */
  public InfluxDB buildInfluxDB() {
    if (influxDB == null) {
      final Builder client = new OkHttpClient.Builder()
        .connectTimeout(properties.getConnectTimeout(), TimeUnit.SECONDS)
        .writeTimeout(properties.getWriteTimeout(), TimeUnit.SECONDS)
        .readTimeout(properties.getReadTimeout(), TimeUnit.SECONDS);
      logger.info("InfluxDB OkHttpClient Builder, connectTimeout:[{}],writeTimeout:[{}],readTimeout:[{}].", properties.getConnectTimeout(), properties.getWriteTimeout(), properties.getReadTimeout());
      influxDB = InfluxDBFactory.connect(properties.getUrl(), properties.getUsername(), properties.getPassword(),client);
      logger.info("Using InfluxDB '[{}]' on '[{}]'", properties.getDatabase(), properties.getUrl());
      if (properties.isGzip()) {
        logger.info("Enabled gzip compression for HTTP requests");
        influxDB.enableGzip();
      }
      if(properties.isEnableBatch()){
        BatchOptions batchOptions = BatchOptions.DEFAULTS.actions(properties.getBatchAction()).flushDuration(properties.getFlushDuration()).jitterDuration(properties.getJitterDuration());
        influxDB.enableBatch(batchOptions);
      }
    }
    return influxDB;
  }

}
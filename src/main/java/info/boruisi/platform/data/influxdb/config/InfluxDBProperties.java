package info.boruisi.platform.data.influxdb.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * InfluxDB Client Properties
 */
@ConfigurationProperties(prefix = "spring.data.influxdb")
public class InfluxDBProperties {

  /**
   * InfluxDB http url
   */
  private String url;

  /**
   * auth username
   */
  private String username;

  /**
   * auth password
   */
  private String password;

  /**
   * database for http
   */
  private String database;

  /**
   * retention policy
   */
  private String retentionPolicy;

  /**
   * http connection timeout,Time unit second
   */
  private int connectTimeout = 10;

  /**
   * http read timeout,Time unit second
   */
  private int readTimeout = 30;

  /**
   * http write timeout,Time unit second
   */
  private int writeTimeout = 10;

  /**
   * gzip compression for HTTP requests
   */
  private boolean gzip = false;

  private int batchAction = 2000;

    /**
     * Time unit millisecond
     */
  private int flushDuration = 1000;

  /**
   * Jitters the batch flush interval by a random amount(milliseconds). This is primarily to avoid
   * large write spikes for users running a large number of client instances.
   * ie, a jitter of 500ms and flush duration 1000ms means flushes will happen every 1000-1500ms.
   */
  private int jitterDuration = 500;

  /**
   * udp write port
   */
  private int udpPort = 8089;

  private boolean enableBatch = false;

  /**
   * @return String return the url
   */
  public String getUrl() {
      return url;
  }

  /**
   * @param url the url to set
   */
  public void setUrl(String url) {
      this.url = url;
  }

  /**
   * @return String return the username
   */
  public String getUsername() {
      return username;
  }

  /**
   * @param username the username to set
   */
  public void setUsername(String username) {
      this.username = username;
  }

  /**
   * @return String return the password
   */
  public String getPassword() {
      return password;
  }

  /**
   * @param password the password to set
   */
  public void setPassword(String password) {
      this.password = password;
  }

  /**
   * @return String return the database
   */
  public String getDatabase() {
      return database;
  }

  /**
   * @param database the database to set
   */
  public void setDatabase(String database) {
      this.database = database;
  }

  /**
   * @return String return the retentionPolicy
   */
  public String getRetentionPolicy() {
      return retentionPolicy;
  }

  /**
   * @param retentionPolicy the retentionPolicy to set
   */
  public void setRetentionPolicy(String retentionPolicy) {
      this.retentionPolicy = retentionPolicy;
  }

  /**
   * @return int return the connectTimeout
   */
  public int getConnectTimeout() {
      return connectTimeout;
  }

  /**
   * @param connectTimeout the connectTimeout to set
   */
  public void setConnectTimeout(int connectTimeout) {
      this.connectTimeout = connectTimeout;
  }

  /**
   * @return int return the readTimeout
   */
  public int getReadTimeout() {
      return readTimeout;
  }

  /**
   * @param readTimeout the readTimeout to set
   */
  public void setReadTimeout(int readTimeout) {
      this.readTimeout = readTimeout;
  }

  /**
   * @return int return the writeTimeout
   */
  public int getWriteTimeout() {
      return writeTimeout;
  }

  /**
   * @param writeTimeout the writeTimeout to set
   */
  public void setWriteTimeout(int writeTimeout) {
      this.writeTimeout = writeTimeout;
  }

  /**
   * @return boolean return the gzip
   */
  public boolean isGzip() {
      return gzip;
  }

  /**
   * @param gzip the gzip to set
   */
  public void setGzip(boolean gzip) {
      this.gzip = gzip;
  }

  /**
   * @return int return the udpPort
   */
  public int getUdpPort() {
      return udpPort;
  }

  /**
   * @param udpPort the udpPort to set
   */
  public void setUdpPort(int udpPort) {
      this.udpPort = udpPort;
  }

  public int getBatchAction() {
      return batchAction;
  }

  public void setBatchAction(int batchAction) {
      this.batchAction = batchAction;
  }

  public int getFlushDuration() {
      return flushDuration;
  }

  public void setFlushDuration(int flushDuration) {
      this.flushDuration = flushDuration;
  }

  public boolean isEnableBatch() {
      return enableBatch;
  }

  public void setEnableBatch(boolean enableBatch) {
      this.enableBatch = enableBatch;
  }

  public int getJitterDuration() {
    return jitterDuration;
  }

  public void setJitterDuration(int jitterDuration) {
    this.jitterDuration = jitterDuration;
  }
}
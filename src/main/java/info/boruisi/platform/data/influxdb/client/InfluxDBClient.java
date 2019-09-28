package info.boruisi.platform.data.influxdb.client;

import info.boruisi.platform.data.influxdb.config.InfluxDBProperties;
import org.influxdb.InfluxDB;
import org.influxdb.dto.BoundParameterQuery;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Result;
import org.influxdb.impl.InfluxDBResultMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * InfluxDB Client
 * @author guhao
 * @since  2019/07/20
 */
public class InfluxDBClient implements InitializingBean {

  private static final Logger logger = LoggerFactory.getLogger(InfluxDBClient.class);

  private InfluxDBFactoryBean influxDBFactory;
  private InfluxDB influxDB;

  public InfluxDBClient(final InfluxDBFactoryBean influxDBFactory) throws Exception{
    this.influxDBFactory = influxDBFactory;
    this.influxDB = influxDBFactory.getObject();
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    Assert.notNull(influxDBFactory, "Property 'influxDBFactory' is required");
  }

  /**
   * @return InfluxDBFactoryBean return the influxDBFactory
   */
  private InfluxDBFactoryBean getInfluxDBFactory() {
      return influxDBFactory;
  }

  /**
   * @param influxDBFactory the influxDBFactory to set
   */
  public void setInfluxDBFactory(InfluxDBFactoryBean influxDBFactory) {
      this.influxDBFactory = influxDBFactory;
  }

  /**
   * @return InfluxDB return the influxDB
   */
  public InfluxDB getInfluxDB() throws Exception {
      return influxDBFactory.getObject();
  }

  /**
   * @param influxDB the influxDB to set
   */
  public void setInfluxDB(InfluxDB influxDB) {
      this.influxDB = influxDB;
  }

  private InfluxDBProperties getProperties(){
    return getInfluxDBFactory().getProperties();
  }

  private String getDatabase() {
    return getProperties().getDatabase();
  }

  private String getRetentionPolicy() {
    return getProperties().getRetentionPolicy();
  }

  private int getUdpPort() {
    return getInfluxDBFactory().getProperties().getUdpPort();
  }

  /**
   * Write a single Point to the database.
   *
   * @param point The point to write
   */
  public void write(final Point point){
    influxDB.write(getDatabase(), getRetentionPolicy(), point);
  }

  /**
   * Write a single Point to the database.
   * @param measurement the name of the measurement.
   * @param tags        the Map of tags to set
   * @param fields      the fields to set
   * @param timeToSet the time for this point
   */
  public void write(String measurement, Map<String, String> tags, Map<String, Object> fields,final long timeToSet) {
    write(measurement,tags,fields,timeToSet,TimeUnit.MILLISECONDS);
  }

  /**
   * Write a single Point to the database.
   * @param measurement the name of the measurement.
   * @param tags        the Map of tags to set
   * @param fields      the fields to set
   * @param timeToSet the time for this point
   * @param precisionToSet the TimeUnit
   */
  public void write(String measurement, Map<String, String> tags, Map<String, Object> fields,final long timeToSet, final TimeUnit precisionToSet) {
    Point.Builder builder = Point.measurement(measurement);
    builder.tag(tags);
    builder.fields(fields);
    builder.time(timeToSet,precisionToSet);
    write(builder.build());
  }

  /**
   * Write a single Point with POJO to the database.
   *
   * @param pojo The POJO Object with annotation {@link org.influxdb.annotation.Measurement} on fields to write
   */
  public <T> void write(final T pojo){
    Point point = Point.measurementByPOJO(pojo.getClass()).addFieldsFromPOJO(pojo).build();
    write(point);
  }


  /**
   * Write a single Point to the database.
   * @param measurement the name of the measurement.
   * @param tags        the Map of tags to set
   * @param fields      the fields to set
   * @param timeToSet the time for this point
   */
  public void writeUdp(String measurement, Map<String, String> tags, Map<String, Object> fields,final long timeToSet) {
    writeUdp(measurement,tags,fields,timeToSet,TimeUnit.MILLISECONDS);
  }

  /**
   * Write a single Point to the database.
   * @param measurement the name of the measurement.
   * @param tags        the Map of tags to set
   * @param fields      the fields to set
   * @param timeToSet the time for this point
   * @param precisionToSet the TimeUnit
   */
  public void writeUdp(String measurement, Map<String, String> tags, Map<String, Object> fields,final long timeToSet, final TimeUnit precisionToSet) {
    Point.Builder builder = Point.measurement(measurement);
    builder.tag(tags);
    builder.fields(fields);
    builder.time(timeToSet,precisionToSet);
    writeUdp(builder.build());
  }

  /**
   * Write a single Point to the database through UDP.
   *
   * @param point The point to write
   */
  public void writeUdp(final Point point) {
    writeUdp(getUdpPort(), point);
  }

  /**
   * Write a single Point to the database through UDP.
   *
   * @param udpPort the udpPort to write to.
   * @param point The point to write
   */
  public void writeUdp(int udpPort,Point point) {
    influxDB.write(udpPort, point);
  }

  /**
   * Write a single Point to the database through UDP.
   *
   * @param pojo The POJO Object with annotation {@link org.influxdb.annotation.Measurement} on class to write
   */
  public <T> void writeUdp(final T pojo){
    writeUdp(getUdpPort(), pojo);
  }

  /**
   * Write a single Point to the database through UDP.
   *
   * @param pojo The POJO Object with annotation {@link org.influxdb.annotation.Measurement} on fields to write
   */
  public <T> void writeUdp(int udpPort,final T pojo){
    Point point = Point.measurementByPOJO(pojo.getClass()).addFieldsFromPOJO(pojo).build();
    writeUdp(udpPort, point);
  }

  /**
   * Execute a query against a database.
   * @param command   the query to execute.
   * @return  a List of Series which matched the query.
   */
  public QueryResult query(String command){
    Query query = new Query(command, getDatabase());
    return influxDB.query(query);
  }

  /**
   * Execute a query against a database.
   * @param influxQL   the query to execute.
   * @param params     Query using parameter binding("prepared statements")
   * @return  a List of Series which matched the query.
   */
  public QueryResult query(final String influxQL, Map<String,Object> params){
    BoundParameterQuery.QueryBuilder queryBuilder = BoundParameterQuery.QueryBuilder.newQuery(influxQL).forDatabase(getDatabase());
    for (Map.Entry<String,Object> param:params.entrySet()){
      queryBuilder.bind(param.getKey(),param.getValue());
    }
    Query query = queryBuilder.create();
    return influxDB.query(query);
  }

  /**
   * Execute a query against a database.
   * @param command   the query to execute.
   * @param timeUnit  the time unit of the results.
   * @return  a List of Series which matched the query.
   */
  public QueryResult query(String command,final TimeUnit timeUnit){
    Query query = new Query(command, getDatabase());
    return influxDB.query(query,timeUnit);
  }

  /**
   * Execute a query against a database and ignore error
   * @param command   the query to execute.
   * @return  a List of Series which matched the query.
   */
  public List<Result> queryResults(String command){
    QueryResult result = query(command);
    if(result.hasError()){
      logger.error("execute query error:[{}]", result.getError());
    }
    return result.getResults();
  }

  /**
   * Execute a query against a database and ignore error
   * @param command   the query to execute.
   * @param timeUnit  the time unit of the results.
   * @return  a List of Series which matched the query.
   */
  public List<Result> queryResults(String command,final TimeUnit timeUnit){
    QueryResult result = query(command,timeUnit);
    if(result.hasError()){
      logger.error("execute query error:[{}]", result.getError());
    }
    return result.getResults();
  }

  /**
   * Execute a query and mapper to POJO
   * @param command   the query to execute.
   * @param clazz     POPO's Class
   * @param <T>       the target type
   * @return  QueryResult to your POJO
   */
  public <T> List<T> query(String command,Class<T> clazz){
    return query(command,clazz,null);
  }

  /**
   * Execute a query and mapper to POJO
   * @param command   the query to execute.
   * @param clazz     POPO's Class
   * @param timeUnit  the time unit of the results.
   * @param <T>       the target type
   * @return  QueryResult to your POJO
   */
  public <T> List<T> query(String command,Class<T> clazz,final TimeUnit timeUnit){
    QueryResult queryResult = query(command);
    InfluxDBResultMapper resultMapper = new InfluxDBResultMapper(); // thread-safe - can be reused
    return resultMapper.toPOJO(queryResult, clazz, timeUnit);
  }

}
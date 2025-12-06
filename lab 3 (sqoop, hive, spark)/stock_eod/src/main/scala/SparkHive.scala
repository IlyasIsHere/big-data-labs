import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql.functions._ // Required for agg, avg, max, col, etc.
import org.apache.log4j.{Level, Logger}

object SparkHive {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    Logger.getLogger("akka").setLevel(Level.ERROR)

    val conf = new SparkConf().setAppName("SparkHive").setMaster("yarn-client")
    val sc = new SparkContext(conf)
    val sqlContext = new org.apache.spark.sql.hive.HiveContext(sc)
    val myDF = sqlContext.sql("select * from stock.stock_eod")

    println("--- Schema of stock_eod ---")
    myDF.printSchema()

    // ==========================================
    // Processing 1: Calculate Average Closing Price per Stock
    // ==========================================
    println("--- Processing 1: Average Closing Price per Stock ---")
    val avgCloseDF = myDF.groupBy("stockticker")
      .agg(avg("closeprice").as("avg_close_price"))
      .orderBy(desc("avg_close_price"))

    avgCloseDF.show(10) // Show top 10 results

    // ==========================================
    // Processing 2: Identify High Volatility Days (High - Low)
    // ==========================================
    println("--- Processing 2: Highest Volatility Days ---")
    val volatilityDF = myDF.withColumn("price_spread", col("highprice") - col("lowprice"))
      .select("stockticker", "tradedate", "price_spread", "volume")
      .orderBy(desc("price_spread"))

    volatilityDF.show(10)

    // ==========================================
    // Processing 3: Find Max Volume per Stock
    // ==========================================
    println("--- Processing 3: Max Volume per Stock ---")
    val maxVolDF = myDF.groupBy("stockticker")
      .agg(max("volume").as("max_volume"))
      .orderBy(desc("max_volume"))

    maxVolDF.show(10)

    sc.stop()
  }
}
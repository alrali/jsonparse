import org.apache.spark.sql.SparkSession
import java.util.Properties
import org.apache.spark.sql.SaveMode

object PostgresJsonLoad {
  def main(args:Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("yarn")
      .appName("postgres json example")
      .getOrCreate()

    val url = "jdbc:postgresql://ecommdemo.cy65sdz3dwwz.ap-southeast-2.rds.amazonaws.com:5432/postgres"

    val properties = new Properties()
    properties.put("user", "akolla")
    properties.put("password", "Anil$198419")
    Class.forName("org.postgresql.Driver")

    val table = "iconic.stg_EcommTrans"

    val trans_df = spark.read
//      .option("multiline", "true")
      .json("s3://data-test-iconic-demo/inbound/sample.json")
    trans_df.printSchema()
    trans_df.show(10,false)
    println("================== Record Count: " + trans_df.count())

    trans_df.write.mode(SaveMode.Overwrite).jdbc(url, table, properties)

  }
}

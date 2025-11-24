import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

object RatedFilmsPerUser {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf = new SparkConf().setAppName("FilmsRating").setMaster("yarn-client")
    val sc =new SparkContext(conf)

    // lecture du fichier CSV
    var data = sc.textFile("ratings.csv")

    // Extraire le header
    val header = data.first();

    // supprimer le header des données en utilisant la transformation filter
    data = data.filter(row => row != header)

    val result = data.map(line => line.split(',')(0).toString) // getting the user id from each line
      .countByValue() // count ratings for the same user id

    // classer et afficher les resultats
    result.toSeq.sorted.foreach(println)
  }
}

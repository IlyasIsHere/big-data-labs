import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

object FilmsStatistics {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf = new SparkConf().setAppName("FilmsStatistics").setMaster("yarn-client")
    val sc = new SparkContext(conf)

    // lire le fichier pour en créer un RDD
    var movies = sc.textFile("movies.csv")
    var ratings = sc.textFile("ratings.csv")

    // extraire la première ligne : l’entête
    val headerMovies = movies.first();
    val headerRatings = ratings.first();

    // filtrer l’entête pour la supprimer
    movies = movies.filter(row => row != headerMovies)
    ratings = ratings.filter(row => row != headerRatings)

    // Get only comedy movies

    val comedyMovies = movies.map(row => row.split(','))
      .map(fields => (fields(1), fields(2)))
      .flatMapValues(x => x.split('|'))
      .filter(x => x._2 == "Comedy")
      .map(x => x._1)

    comedyMovies.collect().sorted
      .take(10).foreach(println)
    comedyMovies.coalesce(1).saveAsTextFile("FilmsComedy")

    // ===================================================================
    // Number of movie ratings per user

    val userRatingsCount = ratings.map(row => row.split(","))
      .map(fields => (fields(0), 1))
      .reduceByKey(_ + _)

    userRatingsCount.take(10).foreach(println)
    userRatingsCount.saveAsTextFile("UserRatingsCount.csv")

    // ===================================================================
    // Number of ratings per movie

    val movieRatingsCount = ratings.map(row => row.split(","))
      .map(fields => (fields(1), 1))
      .reduceByKey(_ + _)

    val movieTitles = movies.map(row => row.split(","))
      .map(fields => (fields(0), fields(1)))

    val joined = movieRatingsCount.join(movieTitles)

    joined.take(10).foreach(println)
    joined.saveAsTextFile("MovieRatingsCount.csv")

  }
}
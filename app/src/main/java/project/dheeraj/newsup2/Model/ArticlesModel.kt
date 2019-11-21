package project.dheeraj.newsup2.Model


data class ArticlesModel (val status : String, val totalResults : Integer, val articles: List<Articles>)

data class Articles(val source : Source,
                    val author : String,
                    val title : String,
                    val description : String,
                    val url : String,
                    val urlToImage : String,
                    val publishedAt : String,
                    val content : String)

data class Source(val id : String,
                  val name : String)
//    lateinit var status : String
//    lateinit var totalResults : Integer
//    lateinit var articles : List<Articles>
//
//
//    object Articles{
//
//        lateinit var source : List<Source>
//        lateinit var author : String
//        lateinit var title : String
//        lateinit var description : String
//        lateinit var url : String
//        lateinit var urlToImage : String
//        lateinit var publishedAt : String
//        lateinit var content : String
//
//        object Source{
//
//            lateinit var id : String
//            lateinit var name : String
//
//        }
//
//    }


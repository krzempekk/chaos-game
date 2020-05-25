import scala.collection.mutable.ListBuffer
import io.circe.parser._
import io.circe.generic.auto._

case class InitialVector(x: Double, y: Double)
case class Preset(name: String, initialVectors: List[InitialVector], multiplier: Double, canReselectVertex: Boolean)

//class Presets(var name: String, var width: Int, var height: Int) {
//    def initialParameters: (Double, GameVectors, Boolean) = {
//        val vectors = new GameVectors
//        name match {
//            case "Sierpinsky" =>
//                vectors.addInitialVectors(ListBuffer(Vector2D(width-10,height-10), Vector2D(10,height-10), Vector2D((width-20)/2,10)))
//                (0.5,vectors,true)
//            case "Rectangular" =>
//                vectors.addInitialVectors(ListBuffer(Vector2D(width-10,height-10), Vector2D(10,height-10), Vector2D(10,10), Vector2D(width-10,10)))
//                (0.5,vectors,false)
//            case "Pentagon"=>
//                vectors.addInitialVectors(ListBuffer(Vector2D((width-20)/5,height-10), Vector2D(10,(height-20)/2)
//                  ,Vector2D((width-20)/2,10), Vector2D(width-10,(height-20)/2),Vector2D((width-20)*4/5,height-10)))
//                (0.5,vectors,false)
//            case "Vicsek"=>
//                vectors.addInitialVectors(ListBuffer(Vector2D(width-10,height-10), Vector2D(10,height-10),
//                     Vector2D(10,10), Vector2D(width-10,10), Vector2D((width-20)/2,(height-20)/2)))
//                (0.66,vectors,true)
//            case "Carpet"=>
//                vectors.addInitialVectors(ListBuffer(Vector2D(width-10,height-10), Vector2D(10,height-10),
//                    Vector2D(10,10), Vector2D(width-10,10), Vector2D(10,(height-20)/2), Vector2D((width-20)/2,height-20),
//                    Vector2D(width-10,(height-20)/2),Vector2D((width-20)/2,10)))
//                (0.66,vectors,true)
//        }
//    }

object Presets {
  def loadPresetFromJSON(filePath: String): Option[Preset] = {
    try {
      val source = scala.io.Source.fromFile(filePath)
      try {
        val rawString = source.mkString

        val result = decode[Preset](rawString)
        result match {
          case Left(error) =>
            println(error.getMessage)
            None
          case Right(preset) =>
            println("Preset with name " + preset.name + " loaded successfully!")
            Some(preset)
        }

      } finally {
        source.close()
      }
    } catch {
      case ex: java.io.FileNotFoundException =>
        println(ex.getMessage)
        None
    }
  }
}

import scala.collection.mutable.ListBuffer
import io.circe.parser._
import io.circe.generic.auto._
import io.circe.syntax._

case class InitialVector(x: Double, y: Double)
case class Preset(name: String, initialVectors: List[InitialVector], multiplier: Double, canReselectVertex: Boolean)

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

  def savePresetToJSON(filePath: String, name: String, initialVectors: List[InitialVector], multiplier: Double, canReselectVertex: Boolean): Unit = {
    val jsonPreset = Preset(name, initialVectors, multiplier, canReselectVertex).asJson
    reflect.io.File(filePath).writeAll(jsonPreset.toString())
  }
}

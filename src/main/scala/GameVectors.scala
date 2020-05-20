import scala.collection.mutable.ListBuffer

class GameVectors() {

  private var vectors = ListBuffer[Vector2D]()
  private val rand = scala.util.Random
  var currentVector = new Vector2D(0, 0)

  def +(vector2D: Vector2D): Unit =  this.vectors += vector2D

  def -(vector2D: Vector2D): Unit = this.vectors -= vector2D

  def getList: List[Vector2D] = this.vectors.toList

  def getRandomVector: Vector2D = this.vectors(rand.nextInt(this.vectors.size))

  def nextVector(fraction: Double): Vector2D = {
    var nextVector = this.getRandomVector
    nextVector = this.currentVector.getNextVector(nextVector, fraction)
    this.currentVector = nextVector
    nextVector
  }

}
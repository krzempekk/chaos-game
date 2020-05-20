import scala.collection.mutable.ListBuffer

class GameVectors() {

  var vectors = ListBuffer[Vector2D]()
  private val rand = scala.util.Random

  var generatedPoints: ListBuffer[Vector2D] = ListBuffer[Vector2D]()

  var currentVector = new Vector2D(0, 0)

  def +(vector2D: Vector2D): Unit = this.vectors += vector2D

  def -(vector2D: Vector2D): Unit = this.vectors -= vector2D

  def addInitialVectors(listBuffer: ListBuffer[Vector2D]): Unit = this.vectors = listBuffer

  def getInitialList: List[Vector2D] = this.vectors.toList

  def getGeneratedList: List[Vector2D] = this.generatedPoints.toList

  def getAll: List[Vector2D] = this.vectors.appendAll(this.generatedPoints).toList

  def getRandomVector: Vector2D = this.vectors(rand.nextInt(this.vectors.size))

  def nextVector(fraction: Double, angle: Double): Vector2D = {
    val nextInitial = this.getRandomVector
    var nextVector = this.currentVector.getNextVector(nextInitial, fraction)
    if(angle != 0 && nextInitial == vectors(0)) {
      println(nextVector - currentVector)
      println((nextVector - currentVector).rotate(angle))
      nextVector = (nextVector - currentVector).rotate(angle) + currentVector
    }
    this.generatedPoints.addOne(nextVector)
    this.currentVector = nextVector
    nextVector
  }

  def clear(): Unit = {
    this.vectors.clear()
    this.generatedPoints.clear()
  }



}

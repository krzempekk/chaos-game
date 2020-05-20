import scala.collection.mutable.ListBuffer

class GameVectors(var canReselectVertex: Boolean = true) {
  var vertices: ListBuffer[Vector2D] = ListBuffer[Vector2D]()
  private val rand = scala.util.Random
  var generatedPoints: ListBuffer[Vector2D] = ListBuffer[Vector2D]()

  var currentVertex = new Vector2D(0, 0)
  var currentPoint = new Vector2D(0, 0)

  def +(vector2D: Vector2D): Unit = this.vertices += vector2D

  def -(vector2D: Vector2D): Unit = this.vertices -= vector2D

  def addInitialVectors(listBuffer: ListBuffer[Vector2D]): Unit = this.vertices = listBuffer

  def getInitialList: List[Vector2D] = this.vertices.toList

  def getGeneratedList: List[Vector2D] = this.generatedPoints.toList

  def getAll: List[Vector2D] = this.vertices.appendAll(this.generatedPoints).toList

  def getRandomVertex: Vector2D = {
    val vectorsToChoose: ListBuffer[Vector2D] = this.vertices.clone()
    if(!this.canReselectVertex) vectorsToChoose -= this.currentVertex
    vectorsToChoose(rand.nextInt(vectorsToChoose.size))
  }

  def nextVector(fraction: Double, angle: Double): Unit = {
    val nextVertex = this.getRandomVertex
    var nextPoint = this.currentVertex.getNextVector(nextVertex, fraction)
    if(angle != 0 && nextVertex == this.vertices(0)) {
      println(nextPoint - this.currentPoint)
      println((nextPoint - this.currentPoint).rotate(angle))
      nextPoint = (nextPoint - this.currentPoint).rotate(angle) + this.currentPoint
    }
    this.generatedPoints.addOne(nextPoint)
    this.currentVertex = nextVertex
    this.currentPoint = nextPoint
  }

  def clear(): Unit = {
    this.vertices.clear()
    this.generatedPoints.clear()
  }



}

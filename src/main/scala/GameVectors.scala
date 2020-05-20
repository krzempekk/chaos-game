import scala.collection.mutable.ListBuffer

class GameVectors(var canReselectVertex: Boolean = true) {
  var vectors: ListBuffer[Vector2D] = ListBuffer[Vector2D]()
  private val rand = scala.util.Random
  var generatedPoints: ListBuffer[Vector2D] = ListBuffer[Vector2D]()

  var previousVector = new Vector2D(0, 0)
  var previousPoint = new Vector2D(0, 0)

  def +(vector2D: Vector2D): Unit = this.vectors += vector2D

  def -(vector2D: Vector2D): Unit = this.vectors -= vector2D

  def addInitialVectors(listBuffer: ListBuffer[Vector2D]): Unit = this.vectors = listBuffer

  def getInitialList: List[Vector2D] = this.vectors.toList

  def getGeneratedList: List[Vector2D] = this.generatedPoints.toList

  def getAll: List[Vector2D] = this.vectors.appendAll(this.generatedPoints).toList

  def getRandomVertex: Vector2D = {
    val vectorsToChoose: ListBuffer[Vector2D] = this.vectors.clone()
    if(!this.canReselectVertex) vectorsToChoose -= previousVector
    vectorsToChoose(rand.nextInt(vectorsToChoose.size))
  }

  def nextVector(fraction: Double, angle: Double): Unit = {
    val nextVertex = this.getRandomVertex
    var nextPoint = this.previousVertex.getNextVector(nextVertex, fraction)
    if(angle != 0 && nextVertex == vectors(0)) {
      println(nextPoint - previousPoint)
      println((nextPoint - previousPoint).rotate(angle))
      nextPoint = (nextPoint - previousPoint).rotate(angle) + previousPoint
    }
    this.generatedPoints.addOne(nextPoint)
    this.previousVector = nextVector
    this.previousPoint = nextPoint
  }

  def clear(): Unit = {
    this.vectors.clear()
    this.generatedPoints.clear()
  }



}

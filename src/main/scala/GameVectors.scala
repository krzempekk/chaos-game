import scala.collection.mutable.ListBuffer

class GameVectors(var canReselectVertex: Boolean = true) {

  var vertices: ListBuffer[Vector2D] = ListBuffer[Vector2D]()
  private val rand = scala.util.Random
  var generatedPoints: ListBuffer[Vector2D] = ListBuffer[Vector2D]()

  var multiplier = 0.5
  var angle = 0

  var currentVertex = new Vector2D(0, 0)
  var currentPoint = new Vector2D(0, 0)

  def +(vector2D: Vector2D): Unit = this.vertices += vector2D

  def -(vector2D: Vector2D): Unit = this.vertices -= vector2D

  def addInitialVectors(listBuffer: ListBuffer[Vector2D]): Unit = this.vertices = listBuffer

  def getInitialList: List[Vector2D] = this.vertices.toList

  def getGeneratedList: List[Vector2D] = this.generatedPoints.toList

  def getAll: List[Vector2D] = this.vertices.appendAll(this.generatedPoints).toList

  def getRandomVertex: Vector2D = {
    val verticesToChoose: ListBuffer[Vector2D] = this.vertices.clone()
    if(!this.canReselectVertex) verticesToChoose -= this.currentVertex
    verticesToChoose(rand.nextInt(verticesToChoose.size))
  }

  def nextVector(): Unit = {
    val nextVertex = this.getRandomVertex
    var nextPoint = this.currentPoint.getNextVector(nextVertex, multiplier)
    if(nextVertex == this.vertices(0)) {
      nextPoint = (nextPoint - this.vertices(0)).rotate(angle) + this.vertices(0)
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

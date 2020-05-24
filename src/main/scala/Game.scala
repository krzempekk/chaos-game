import scala.collection.mutable.ListBuffer

class Game {

  var gameVectors = new GameVectors
  var isPaused = true
  var addingStartingPoint = false
  var sidePane: SidePane = _

  def addPoint(point: Vector2D): Unit = {
    if(addingStartingPoint) gameVectors.currentPoint = point
    else gameVectors + point
    sidePane.updateVertexChoiceBox()
  }

  def getStartingPoint:Vector2D = this.gameVectors.currentPoint

  def setStartingPoint(point: Vector2D): Unit = gameVectors.currentPoint = point

  def removePoint(point: Vector2D): Unit = gameVectors - point

  def nextStep(): Unit = gameVectors.nextVector()

  def canReselectVertex: Boolean = gameVectors.canReselectVertex

  def setCanReselectVertex(canReselectVertex: Boolean): Unit = gameVectors.canReselectVertex = canReselectVertex

  def cleanGame(): Unit = {
    gameVectors.clear()
    this.isPaused = true
  }

  def getInitialPoints: List[Vector2D] = gameVectors.getInitialList

  def getGeneratedPoints: List[Vector2D] = gameVectors.getGeneratedList

  def startWithNew(newVectors: GameVectors, newMultiplier: Double, canReselectVertex: Boolean): Unit = {
    this.gameVectors.clear()
    this.gameVectors = newVectors
    this.setMultiplier(newMultiplier)
    this.setCanReselectVertex(canReselectVertex)
  }

  def setMultiplier(newMultiplier: Double):Unit = this.gameVectors.multiplier = newMultiplier

  def addAngle(point: Vector2D, angle: Double): Unit = this.gameVectors.addAngle(point, angle)
}

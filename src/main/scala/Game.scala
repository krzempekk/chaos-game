import scala.collection.mutable.ListBuffer

class Game {
  var gameVectors = new GameVectors
  var multiplier = 0.5
  var angle = 0
  var isPaused = false

  def addPoint(point: Vector2D): Unit = gameVectors + point

  def setStartingPoint(point: Vector2D): Unit = gameVectors.currentPoint = point

  def removePoint(point: Vector2D): Unit = gameVectors - point

  def nextStep(): Unit = gameVectors.nextVector(multiplier, angle)

  def setCanReselectVertex(canReselectVertex: Boolean): Unit = gameVectors.canReselectVertex = canReselectVertex

  def cleanGame(): Unit = {
    gameVectors.clear()
    this.isPaused = true
  }

  def getInitialPoints: List[Vector2D] = gameVectors.getInitialList

  def getGeneratedPoints: List[Vector2D] = gameVectors.getGeneratedList

  def startWithNew(newVectors: GameVectors, newMultiplier: Double): Unit = {
    this.gameVectors.clear()
    this.gameVectors = newVectors
    this.multiplier = newMultiplier
  }
}

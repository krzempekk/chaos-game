import scala.collection.mutable.ListBuffer

class Game {
  var gameVectors = new GameVectors
  var multiplier = 0.5
  var angle = math.Pi / 2
  var isPaused = true

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

  def startWithNew(newVectors: GameVectors, newMultiplier: Double, canReselectVertex: Boolean): Unit = {
    this.gameVectors.clear()
    this.gameVectors = newVectors
    this.multiplier = newMultiplier
    this.setCanReselectVertex(canReselectVertex)
  }
}

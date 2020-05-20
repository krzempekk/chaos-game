import scala.collection.mutable.ListBuffer

class Game {
  var gameVectors = new GameVectors
  //  var gameVectors = new GameVectors
  var multiplier = 0.5
  var isPaused = true

  def addPoint(point: Vector2D): Unit = gameVectors + point

  def setStartingPoint(point: Vector2D): Unit = gameVectors.currentVector = point

  def removePoint(point: Vector2D): Unit = gameVectors - point

  def nextStep(): Unit = gameVectors.nextVector(multiplier)

  def cleanGame(): Unit = {
    gameVectors.clear()
    this.isPaused = true
  }

  def getInitailPoints: List[Vector2D] = gameVectors.getInitialList

  def getGeneratedPoints: List[Vector2D] = gameVectors.getGeneratedList

  def startWithNew(newVectors: GameVectors, newMultiplier: Double): Unit = {
    this.gameVectors.clear()
    this.gameVectors = newVectors
    this.multiplier = newMultiplier
  }
}

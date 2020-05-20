import scala.collection.mutable.ListBuffer

class Game {
  var initialPoints = new GameVectors
  var generatedPoints: ListBuffer[Vector2D] = ListBuffer[Vector2D]()
  //  var gameVectors = new GameVectors
  var multiplier = 0.5
  var isPaused = true

  def addPoint(point: Vector2D): Unit = initialPoints + point

  def setStartingPoint(point: Vector2D): Unit = generatedPoints += point

  def removePoint(point: Vector2D): Unit = initialPoints - point

  def nextStep(): Unit = generatedPoints += generatedPoints.last.getNextVector(initialPoints.getRandomVector, multiplier)

  def cleanGame(): Unit = {
    initialPoints.clear()
    this.isPaused = true
  }

  def startWithNew(newVectors: GameVectors, newMultiplier: Double): Unit = {
    this.initialPoints = newVectors
    this.multiplier = newMultiplier
  }
}

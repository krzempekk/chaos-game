//object NeighbourRule extends Enumeration {
//  type NeighbourRule = Value
//  val Any, NotPrevious, NotPreviousNeighbour = Value
//}
//
//sealed trait NextNeighbour
//case object AnyNeighbour extends NextNeighbour
//case class NotInDistance(distance: Int) extends NextNeighbour

class Game {
  var multiplier = 0.66
  var isPaused = false
  var gameVectors = new GameVectors(false)

  def addPoint(point: Vector2D): Unit = gameVectors + point

  def setStartingPoint(point: Vector2D): Unit = gameVectors.previousPoint = point

  def removePoint(point: Vector2D): Unit = gameVectors - point

  def nextStep(): Unit = gameVectors.nextVector(multiplier)

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

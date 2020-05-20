class Game {
  var gameVectors = new GameVectors
  var multiplier = 0.5

  def addPoint(point: Vector2D): Unit = gameVectors + point

  def setStartingPoint(point: Vector2D): Unit =
    gameVectors.currentVector = point

  def removePoint(point: Vector2D): Unit = gameVectors - point

  def nextStep(): Unit = gameVectors.nextVector(multiplier)

}

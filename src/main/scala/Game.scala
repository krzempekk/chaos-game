class Game {
  var gameVectors = new GameVectors
  var multiplier = 0.5
  var isPaused = false

  def addPoint(point: Vector2D): Unit = gameVectors + point

  def setStartingPoint(point: Vector2D): Unit =
    gameVectors.currentVector = point

  def removePoint(point: Vector2D): Unit = gameVectors - point

  def nextStep(): Unit = gameVectors.nextVector(multiplier)

  def cleanGame(): Unit = {
    gameVectors.clear()
    this.isPaused = true
  }

  def startWithNew(newVectors: GameVectors, newMultiplier: Double) = {
    this.gameVectors = newVectors
    this.multiplier = newMultiplier
  }




}

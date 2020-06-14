import GameActionType.GameActionType

object GameActionType extends Enumeration {
  type GameActionType = Value
  val PointAdded, NextStep = Value
}

class Game(val boardWidth: Int, val boardHeight: Int) extends Subject[Game, GameActionType] {
  var gameVectors = new GameVectors
  var isPaused = true
  var addingStartingPoint = false
  var sidePane: SidePane = _

  def addPoint(point: Vector2D): Unit = {
    if(addingStartingPoint) gameVectors.currentPoint = point
    else gameVectors + point
    notifyObservers(GameActionType.PointAdded)
  }

  def getStartingPoint:Vector2D = this.gameVectors.currentPoint

  def setStartingPoint(point: Vector2D): Unit = gameVectors.currentPoint = point

  def removePoint(point: Vector2D): Unit = gameVectors - point

  def nextStep(): Unit = {
    gameVectors.nextVector()
    notifyObservers(GameActionType.NextStep)
  }

  def canReselectVertex: Boolean = gameVectors.canReselectVertex

  def setCanReselectVertex(canReselectVertex: Boolean): Unit = gameVectors.canReselectVertex = canReselectVertex

  def cleanGame(): Unit = {
    gameVectors.clear()
    this.isPaused = true
    setMultiplier(0.5)
  }

  def getInitialPoints: List[Vector2D] = gameVectors.getInitialList

  def getGeneratedPoints: List[Vector2D] = gameVectors.getGeneratedList

  def startWithNew(newVectors: GameVectors, newMultiplier: Double, canReselectVertex: Boolean): Unit = {
    this.gameVectors.clear()
    this.gameVectors = newVectors
    this.setMultiplier(newMultiplier)
    this.setCanReselectVertex(canReselectVertex)
  }

  def getMultiplier: Double = this.gameVectors.multiplier

  def setMultiplier(newMultiplier: Double): Unit = this.gameVectors.multiplier = newMultiplier

  def addAngle(point: Vector2D, angle: Double): Unit = this.gameVectors.addAngle(point, angle)

  def getInitialVectorsPreset: List[InitialVector] = {
    this.getInitialPoints.map(vector => InitialVector(vector.x.toDouble / boardWidth, vector.y.toDouble / boardHeight))
  }

  import UIActionType._

  def receiveUpdate(sidePane: SidePane, actionType: UIActionType): Unit = actionType match {
    case Pause => this.isPaused = true
    case Resume => this.isPaused = false
    case Reset => this.cleanGame()
    case AddingVertex => this.addingStartingPoint = false
    case AddingStartingPoint => this.addingStartingPoint = true
    case WrongMultiplier => this.isPaused = true
    case PresetLoaded =>
      val preset = sidePane.preset.get
      val initialVectors = preset.initialVectors.map(vector => Vector2D((vector.x * boardWidth).toInt, (vector.y * boardHeight).toInt))
      this.startWithNew(new GameVectors(initialVectors), preset.multiplier, preset.canReselectVertex)
      this.setStartingPoint(Vector2D(boardWidth / 2, boardHeight / 2))
  }
}

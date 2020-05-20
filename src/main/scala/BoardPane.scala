import javafx.application.Platform
import javafx.scene.canvas.Canvas
import javafx.scene.layout.Pane
import javafx.scene.paint.Color

class BoardPane(val width: Int, val height: Int, val game: Game) extends Pane {
  val canvas = new Canvas(width, height)
  val pointRadius = 3
  var canWrite = true

  canvas.setOnMouseClicked(event => {
    if(canWrite){
    this.game.addPoint(new Vector2D(event.getX.toInt, event.getY.toInt))
    this.update()
    }
  })

  this.setMinWidth(width)
  this.setMinHeight(height)
  this.setStyle("-fx-background-color: black");
  this.getChildren.add(canvas)

  override def layoutChildren(): Unit = {
    Platform.runLater(() => {
      super.layoutChildren()

      val gc = this.canvas.getGraphicsContext2D
      gc.clearRect(0, 0, width, height)

      gc.setFill(Color.RED)
      for (point <- game.getInitialPoints) gc.fillOval(point.x, point.y, this.pointRadius, this.pointRadius)
      gc.setFill(Color.WHITE)
      for (point <- game.getGeneratedPoints) gc.fillOval(point.x, point.y, this.pointRadius, this.pointRadius)
      gc.setFill(Color.BLUE)
      gc.fillOval(this.game.getStartingPoint.x, this.game.getStartingPoint.y, this.pointRadius, this.pointRadius)
    })
  }

  def update(): Unit = this.layoutChildren()
}

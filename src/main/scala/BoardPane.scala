import java.io.IOException

import javafx.application.Platform
import javafx.scene.canvas.Canvas
import javafx.scene.input.{KeyCode, MouseEvent}
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.stage.FileChooser
import javax.imageio.ImageIO
import scalafx.embed.swing.SwingFXUtils
import scalafx.scene.image.WritableImage



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

  canvas.setFocusTraversable(true)
  canvas.setOnMouseEntered(_ => canvas.requestFocus())

  canvas.setOnKeyPressed(keyEvent =>{
    val keyCode = keyEvent.getCode
    if(keyCode.equals(KeyCode.S)){
      this.saveImage()
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

  def saveImage(): Unit = {
    val fileChooser = new FileChooser()

    fileChooser.getExtensionFilters.add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"))

    val file = fileChooser.showSaveDialog(null)

    if(file != null){
      try{
        val writableImage = new WritableImage(800, 800)
        snapshot(null, writableImage)
        val renderedImage = SwingFXUtils.fromFXImage(writableImage, null)

        ImageIO.write(renderedImage, "png", file)
      }
      catch {
        case ex: IOException => ex.printStackTrace()
        case _: Throwable => println("Exception")
      }
    }
  }
}

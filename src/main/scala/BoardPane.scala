import java.io.IOException

import GameActionType.GameActionType
import UIActionType.UIActionType
import javafx.application.Platform
import javafx.scene.canvas.Canvas
import javafx.scene.input.KeyCode
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
  private val rand = scala.util.Random

  canvas.setOnMouseClicked(event => {
    if(canWrite) this.game.addPoint(Vector2D(event.getX.toInt, event.getY.toInt))
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
      gc.setFill(Color.rgb(50, 50 + rand.nextInt(30), 150))
      for (point <- game.getGeneratedPoints) gc.fillOval(point.x, point.y, this.pointRadius, this.pointRadius)
      gc.setFill(Color.BLUE)
      gc.fillOval(this.game.getStartingPoint.x, this.game.getStartingPoint.y, this.pointRadius, this.pointRadius)
    })
  }

  def reset(): Unit = {
    this.canvas.getGraphicsContext2D.clearRect(0,0, this.canvas.getWidth, this.canvas.getHeight)
    this.canWrite = true
  }

  def receiveUpdate(game: Game, actionType: GameActionType): Unit = actionType match {
    case _ => this.layoutChildren()
  }

  import UIActionType._

  def receiveUpdate(sidePane: SidePane, actionType: UIActionType): Unit = actionType match {
    case Reset => this.reset()
    case PresetLoaded => this.canWrite = false
    case _ =>
  }

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

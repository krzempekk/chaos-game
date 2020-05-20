import com.jfoenix.controls.JFXButton
import javafx.event.{ActionEvent, EventHandler}
import javafx.geometry.{Insets, Pos}
import javafx.scene.Scene
import javafx.scene.control.{Button, Label}
import javafx.scene.input.MouseEvent
import javafx.scene.layout.{Pane, StackPane}
import javafx.scene.text.{Font, Text, TextAlignment}
import scalafx.geometry.Orientation
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.FlowPane
import scalafx.stage.Stage

class SidePane(val width:Int,val height:Int, var game: Game) extends Pane {
  var buttonBar = new FlowPane()

  buttonBar.prefWidthProperty().bind(this.prefWidthProperty())
  buttonBar.setAlignment(Pos.CENTER)
  buttonBar.setHgap(5)

  this.setPadding(new Insets(10, 0, 10, 0))
  this.getChildren.add(this.buttonBar)
  this.addTextLabel("Choose one of prepared options or pick your own points to start", 15)
  this.addOptionButton("Sierpinsky")
  this.addOptionButton("Rectangular")
  this.addOptionButton("Pentagon")
  this.addPauseButton()
  this.addResetButton()
  this.getChildren.add(new Label(""))

  def addTextLabel(text: String, fontSize: Int): Unit = {
    val textLabel = new Text(text)
    textLabel.setFont(Font.font("Verdana", fontSize))
    textLabel.setTextAlignment(TextAlignment.CENTER)
    textLabel.wrappingWidthProperty.bind(this.prefWidthProperty)
    this.getChildren.add(textLabel)
  }

  def addOptionButton(text: String): Unit ={
    var button = new JFXButton(text)
    button.getStyleClass.add("button-rised")

    button.addEventHandler(MouseEvent.MOUSE_CLICKED)={

    }
    );
  }
}

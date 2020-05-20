import java.util.function.UnaryOperator
import java.util.regex.Pattern

import com.jfoenix.controls.JFXButton
import javafx.geometry.{Insets, Pos}
import javafx.scene.Scene
import javafx.scene.control.{Button, Label, TextField, TextFormatter}
import javafx.scene.layout.{Pane, StackPane}
import javafx.scene.text.{Font, Text, TextAlignment}
import javafx.util.StringConverter
import scalafx.geometry.Orientation
import scalafx.scene.layout.FlowPane

class SidePane(val width:Int,val height:Int, var game: Game) extends Pane {
  var buttonBar = new FlowPane(Orientation.Vertical)

  buttonBar.prefWidthProperty().bind(this.prefWidthProperty())
  buttonBar.setAlignment(Pos.CENTER_LEFT)
  buttonBar.setHgap(10)
  buttonBar.setVgap(10)

  this.setPadding(new Insets(10, 10, 100, 10))
  this.getChildren.add(this.buttonBar)

  this.addPauseButton
  this.addResetButton
  this.addOptionButton("Sierpinsky")
  this.addOptionButton("Rectangular")
  this.addOptionButton("Pentagon")
  this.addOwnOptions
  this.addTextLabel("Choose one of prepared options or pick your own points to start", 15)


  def addTextLabel(text: String, fontSize: Int): Unit = {
    val textLabel = new Text(text)
    textLabel.setFont(Font.font("Verdana", fontSize))
    textLabel.setTextAlignment(TextAlignment.CENTER)
    textLabel.wrappingWidthProperty.bind(this.prefWidthProperty)
    this.getChildren.add(textLabel)
  }

  def addOwnOptions: Unit ={
    val button = new JFXButton("Own points")
    button.getStyleClass.add("button-raised")

    button.setOnMouseClicked(event=> {
      val validEditingState = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?")

      val filter:UnaryOperator[TextFormatter.Change] = c => {
        var text = c.getControlNewText
        if(validEditingState.matcher(text).matches()){
           return c
        }
        else return null
      }

      val converter = new StringConverter[Double]() {
        def fromString(s: String): Double =
          if (s.isEmpty || "-" == s || "." == s || "-." == s) 0.0
          else s.toDouble

        def toString(d: Double): String = d.toString
      }

      val textFormatter = new TextFormatter[Double](converter,0.5,filter)
      val textField = new TextField()
      textField.setTextFormatter(textFormatter)

      this.buttonBar.getChildren.add(textField)
    }
    )
    this.buttonBar.getChildren.add(button)
  }

  def addPauseButton = {
    val button = new JFXButton("Pause")
    button.getStyleClass.add("button-raised")

    button.setOnMouseClicked(event=>{
      if(button.getText.equals("Pause")){
        button.setText("Resume")
      }
      else{
        button.setText("Pause")
      }
    }
    )
    this.buttonBar.getChildren.add(button)
  }

  def addResetButton ={
    val button = new JFXButton("Reset")
    button.getStyleClass.add("button-raised")

    button.setOnMouseClicked(event=>{

    }
    )
    this.buttonBar.getChildren.add(button)
  }

  def addOptionButton(text: String): Unit = {
    val button = new JFXButton(text)
    button.getStyleClass.add("button-rised")

    button.setOnMouseClicked(event=>{
      val preset = new Presets(text)
    })

    this.buttonBar.getChildren.add(button)
  }
}

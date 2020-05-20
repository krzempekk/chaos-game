import java.util.function.UnaryOperator
import java.util.regex.Pattern

import javafx.geometry.{Insets, Orientation, Pos}
import javafx.scene.control.{Button, TextField, TextFormatter}
import javafx.scene.layout.{FlowPane, Pane}
import javafx.scene.text.{Font, Text, TextAlignment}
import javafx.util.StringConverter

class SidePane(val width:Int,val height:Int, var game: Game) extends Pane {
  var buttonBar = new FlowPane(Orientation.VERTICAL)

  buttonBar.prefWidthProperty().bind(this.prefWidthProperty())
  buttonBar.setAlignment(Pos.CENTER)
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
    val button = new Button("Own points")
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

  def addPauseButton: Unit = {
    val button = new Button("Pause")
    button.getStyleClass.add("button-raised")

    button.setOnMouseClicked(event=>{
      if(button.getText.equals("Pause")){
        button.setText("Resume")
        game.isPaused=true
      }
      else{
        button.setText("Pause")
        game.isPaused=false
      }
    }
    )
    this.buttonBar.getChildren.add(button)
  }

  def addResetButton: Unit ={
    val button = new Button("Reset")
    button.getStyleClass.add("button-raised")

    button.setOnMouseClicked(event=>{

    }
    )
    this.buttonBar.getChildren.add(button)
  }

  def addOptionButton(text: String): Unit = {
    val button = new Button(text)
    button.getStyleClass.add("button-rised")

    button.setOnMouseClicked(event=>{
      val preset = new Presets(text)
      this.game.startWithNew(preset.initialParameters._2,preset.initialParameters._1)
      this.game.setStartingPoint(new Vector2D(0,600))
    })

    this.buttonBar.getChildren.add(button)
  }
}

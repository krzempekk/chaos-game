import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.geometry.{Insets, Orientation, Pos}
import javafx.scene.control.{Button, TextField, TextFormatter}
import javafx.scene.layout.{FlowPane, Pane}
import javafx.scene.text.{Font, Text, TextAlignment}
import scalafx.util.converter.DoubleStringConverter

class SidePane(val width:Int,val height:Int, var game: Game, var boardPane: BoardPane) extends Pane {
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
      val textFormatter = new TextFormatter[Double](new DoubleStringConverter(),0d)
      val textField = new TextField()
      textField.setTextFormatter(textFormatter)

      textFormatter.valueProperty().addListener(new ChangeListener[Double] {
        override def changed(observableValue: ObservableValue[_ <: Double], t: Double, t1: Double): Unit = {
          if(!t1.equals(0.0) && game.gameVectors.vertices.nonEmpty){
            game.multiplier = t1
            game.isPaused = false
            boardPane.canWrite = false
          }
        }
      })

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
      this.game.cleanGame()
      this.boardPane.canvas.getGraphicsContext2D.clearRect(0,0,this.boardPane.canvas.getWidth,this.boardPane.canvas.getHeight)
    })
    this.buttonBar.getChildren.add(button)
  }

  def addOptionButton(text: String): Unit = {
    val button = new Button(text)
    button.getStyleClass.add("button-raised")

    button.setOnMouseClicked(event=>{
      val preset = new Presets(text,this.boardPane.width,this.boardPane.height)
      this.game.startWithNew(preset.initialParameters._2, preset.initialParameters._1, preset.initialParameters._3)
      this.game.setStartingPoint(new Vector2D(this.boardPane.width/2,this.boardPane.height/2))
      this.game.isPaused=false
      this.boardPane.canWrite = false
    })

    this.buttonBar.getChildren.add(button)
  }
}

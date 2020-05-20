import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.geometry.{Insets, Orientation, Pos}
import javafx.scene.control.{Button, TextField, TextFormatter}
import javafx.scene.layout.{FlowPane, Pane, VBox}
import javafx.scene.paint.Color
import javafx.scene.text.{Font, Text, TextAlignment}
import scalafx.util.converter.{DoubleStringConverter, IntStringConverter}

class SidePane(val width:Int,val height:Int, var game: Game, var boardPane: BoardPane) extends Pane {
  var buttonBar = new VBox()
  var optionsBar = new VBox()
  var wrapper = new VBox()
  var numOfPoints = 0

  buttonBar.prefWidthProperty().bind(this.prefWidthProperty())
  buttonBar.setAlignment(Pos.CENTER)
  buttonBar.setSpacing(10)

  optionsBar.prefWidthProperty().bind(this.prefWidthProperty())
  optionsBar.setAlignment(Pos.CENTER)
  optionsBar.setSpacing(5)

  wrapper.setSpacing(10)

  buttonBar.setPadding(new Insets(0, 0, 0, 30))
  wrapper.getChildren.addAll(buttonBar,optionsBar)
  this.getChildren.add(this.wrapper)

  this.addTextLabel("Choose one of prepared options", 15)
  this.addTextLabel("or pick few points by your own",15)
  this.addPauseButton(game.isPaused)
  this.addResetButton
  this.addOptionButton("Sierpinsky")
  this.addOptionButton("Rectangular")
  this.addOptionButton("Pentagon")
  this.addOwnOptions
  this.addAngleOption

  def addAngleOption: Unit ={
    val button = new Button("Add angles")

    button.setOnMouseClicked(event =>{
        if(numOfPoints < game.gameVectors.vertices.length){
          numOfPoints = game.gameVectors.vertices.length
          this.addTableRow(game.gameVectors.vertices.last)
      }
    })

    this.buttonBar.getChildren.add(button)
  }

  def addTableRow(vector: Vector2D): Unit ={
    val text = new Text(vector.toString)
    text.setTextAlignment(TextAlignment.CENTER)
    val textFormatter = new TextFormatter[Double](new DoubleStringConverter(),0d)
    val textField = new TextField()
    textField.setTextFormatter(textFormatter)
    textField.setAlignment(Pos.CENTER)

    textFormatter.valueProperty().addListener(new ChangeListener[Double] {
      override def changed(observableValue: ObservableValue[_ <: Double], t: Double, t1: Double): Unit = {
        game.addAngle(vector,t1)
      }
      })
    this.optionsBar.getChildren.add(text)
    this.optionsBar.getChildren.add(textField)
  }
  
  def addTextLabel(text: String, fontSize: Int): Unit = {
    val textLabel = new Text(text)
    textLabel.setFont(Font.font("Verdana", fontSize))
    textLabel.setTextAlignment(TextAlignment.CENTER)
    textLabel.setFill(Color.CORNFLOWERBLUE)
    textLabel.wrappingWidthProperty.bind(this.prefWidthProperty)
    this.buttonBar.getChildren.add(textLabel)
  }

  def addOwnOptions: Unit ={
    val button = new Button("Set multiplier")
    button.getStyleClass.add("button-raised")

    button.setOnMouseClicked(event=> {
      val textFormatter = new TextFormatter[Double](new DoubleStringConverter(),0d)
      val textField = new TextField()
      textField.setTextFormatter(textFormatter)

      textFormatter.valueProperty().addListener(new ChangeListener[Double] {
        override def changed(observableValue: ObservableValue[_ <: Double], t: Double, t1: Double): Unit = {
          if(!t1.equals(0.0) && game.gameVectors.vertices.nonEmpty){
            game.isPaused = false
            boardPane.canWrite = false
          }
        }
      })
      var text = new Text("Set multiplier")
      this.optionsBar.getChildren.add(text)
      this.optionsBar.getChildren.add(textField)

    }
    )
    this.buttonBar.getChildren.add(button)
  }

  def addPauseButton(isPaused: Boolean): Unit = {
    var button = new Button("Pause")
    if(isPaused){ button = new Button("Resume")}
    button.getStyleClass.add("button-raised")

    button.setOnMouseClicked(event=>{
      if(button.getText.equals("Pause")){
        button.setText("Resume")
        game.isPaused=true
      }
      else{
        if(game.gameVectors.vertices.nonEmpty){
        button.setText("Pause")
        game.isPaused=false}
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
      this.optionsBar.getChildren.clear()
      this.boardPane.canWrite=true
      this.numOfPoints=0
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

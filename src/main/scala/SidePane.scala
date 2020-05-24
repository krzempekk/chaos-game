import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.event.{Event, EventHandler}
import javafx.geometry.{Insets, Orientation, Pos}
import javafx.scene.control.{Button, ChoiceBox, RadioButton, TextField, TextFormatter, ToggleGroup}
import javafx.scene.input.MouseEvent
import javafx.scene.layout.{FlowPane, Pane, VBox}
import javafx.scene.paint.Color
import javafx.scene.text.{Font, Text, TextAlignment}
import scalafx.util.converter.DoubleStringConverter

class SidePane(val width:Int,val height:Int, var game: Game, var boardPane: BoardPane) extends FlowPane {
  var buttonBar = new VBox()
  var optionsBar = new VBox()
  var vertexInfo = new VBox()
  var wrapper = new VBox()
  val vertexChoiceBox = new ChoiceBox[Vector2D]()
  var numOfPoints = 0

  var pauseButton = new Button()

  var editedVertex: Option[Vector2D] = None
  this.setPadding(new Insets(20,0,0,30))

  this.addTextLabel("Choose one of prepared options", 15)
  this.addTextLabel("or pick few points by your own",15)

  buttonBar.prefWidthProperty().bind(this.prefWidthProperty())
  buttonBar.setAlignment(Pos.CENTER)
  buttonBar.setSpacing(10)

  VBox.setMargin(buttonBar, new Insets(40))

  optionsBar.prefWidthProperty().bind(this.prefWidthProperty())
  optionsBar.setAlignment(Pos.CENTER)
  optionsBar.setSpacing(5)

  VBox.setMargin(optionsBar, new Insets(0,0,0,40))

  vertexInfo.prefWidthProperty().bind(this.prefWidthProperty())
  vertexInfo.setAlignment(Pos.CENTER)

  wrapper.setSpacing(10)

  buttonBar.setPadding(new Insets(0, 0, 0, 30))
  wrapper.getChildren.addAll(buttonBar, optionsBar, vertexInfo)
  this.getChildren.add(this.wrapper)

  this.addPauseButton(game.isPaused)
  this.addResetButton
  this.addOptionButton("Sierpinsky")
  this.addOptionButton("Rectangular")
  this.addOptionButton("Pentagon")
  this.addOptionButton("Vicsek")
  this.addOptionButton("Carpet")
  this.addOwnOptions
  this.addAngleOption
  this.addVertexInfo
  this.addDrawRadioButtons

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
    this.wrapper.getChildren.add(textLabel)
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

  def pauseButtonAction(): Unit = {
    if(this.pauseButton.getText.equals("Pause")){
      this.pauseButton.setText("Resume")
      game.isPaused=true
    }
    else{
      if(game.gameVectors.vertices.nonEmpty){
        this.pauseButton.setText("Pause")
        game.isPaused=false
      }
    }
  }

  def addPauseButton(isPaused: Boolean): Unit = {
    this.pauseButton.setText("Pause")
    if(isPaused){
      this.pauseButton.setText("Resume")
    }
    this.pauseButton.getStyleClass.add("button-raised")

    this.pauseButton.setOnMouseClicked(_=>{
      this.pauseButtonAction()
    }
    )
    this.buttonBar.getChildren.add(this.pauseButton)
  }

  def addResetButton: Unit ={
    val button = new Button("Reset")
    button.getStyleClass.add("button-raised")

    button.setOnMouseClicked(_=>{
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

    button.setOnMouseClicked(event => {
      val preset = new Presets(text, this.boardPane.width, this.boardPane.height)
      this.game.startWithNew(preset.initialParameters._2, preset.initialParameters._1, preset.initialParameters._3)
      this.game.setStartingPoint(Vector2D(this.boardPane.width/2,this.boardPane.height/2))
      if(this.game.isPaused) this.pauseButtonAction()
      this.boardPane.canWrite = false
    })

    this.buttonBar.getChildren.add(button)
  }

  def updateVertexChoiceBox(): Unit = {
    vertexChoiceBox.getItems.clear()
    for(vertex <- this.game.getInitialPoints) {
      vertexChoiceBox.getItems.add(vertex)
    }
  }

  def addVertexInfo: Unit = {
    vertexChoiceBox.onActionProperty().setValue((t: Event) => {
      editedVertex = Some(t.getTarget.asInstanceOf[ChoiceBox[Vector2D]].getValue)
    })

    this.vertexInfo.getChildren.add(vertexChoiceBox)
  }


  def addDrawRadioButtons: Unit = {
    val rbGroup = new ToggleGroup()

    val startingPointButton = new RadioButton("Adding starting point")
    startingPointButton.setToggleGroup(rbGroup)
    startingPointButton.setOnMouseClicked(event =>{
      this.game.addingStartingPoint = !this.game.addingStartingPoint
    })

    val vertexButton = new RadioButton("Adding vertex")
    vertexButton.setToggleGroup(rbGroup)
    vertexButton.setSelected(this.game.addingStartingPoint)
    vertexButton.setOnMouseClicked(event =>{
      this.game.addingStartingPoint = !this.game.addingStartingPoint
    })

    this.buttonBar.getChildren.add(startingPointButton)
    this.buttonBar.getChildren.add(vertexButton)
  }
}

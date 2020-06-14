import java.io.File

import GameActionType.GameActionType
import UIActionType.UIActionType
import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.event.Event
import javafx.geometry.{Insets, Pos}
import javafx.scene.control._
import javafx.scene.layout.{FlowPane, VBox}
import javafx.scene.paint.Color
import javafx.scene.text.{Font, Text, TextAlignment}
import javafx.stage.FileChooser
import scalafx.util.converter.DoubleStringConverter

object UIActionType extends Enumeration {
  type UIActionType = Value
  val Pause, Resume, Reset, AddingVertex, AddingStartingPoint, PresetLoaded, WrongMultiplier = Value
}

class SidePane(val width: Int, val height: Int, val game: Game) extends FlowPane with Subject[SidePane, UIActionType] {
  var buttonBar = new VBox()
  var optionsBar = new VBox()
  var vertexInfo = new VBox()
  var wrapper = new VBox()
  val vertexChoiceBox = new ChoiceBox[Vector2D]()
  var numOfPoints = 0

  var pauseButton = new Button()
  var preset: Option[Preset] = None

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
  this.addResetButton()
  this.addLoadPresetButton()
  this.addSavePresetButton()
  this.addMultiplierButton()
  this.addAngleOption()
  this.addVertexInfo()
  this.addDrawRadioButtons()

  def addAngleOption(): Unit ={
    val button = new Button("Add angles")

    button.setOnMouseClicked(event =>{
      if(numOfPoints < game.gameVectors.vertices.length) {
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

//  needs refactoring
  def addMultiplierButton(): Unit = {
    val button = new Button("Set multiplier")
    button.getStyleClass.add("button-raised")

    button.setOnMouseClicked(_ => {
      val textFormatter = new TextFormatter[Double](new DoubleStringConverter(),0d)
      val textField = new TextField()
      textField.setTextFormatter(textFormatter)

      textFormatter.valueProperty().addListener(new ChangeListener[Double] {
        override def changed(observableValue: ObservableValue[_ <: Double], t: Double, t1: Double): Unit = {
          if(!t1.equals(0.0) && game.gameVectors.vertices.nonEmpty) {
            notifyObservers(UIActionType.WrongMultiplier)
          }
          else {
            game.setMultiplier(t1)
          }
        }
      })

      val text = new Text("Set multiplier")
      this.optionsBar.getChildren.addAll(text, textField)
    })

    this.buttonBar.getChildren.add(button)
  }

  def pauseButtonAction(): Unit = {
    if (this.pauseButton.getText.equals("Pause")) {
      this.pauseButton.setText("Start")
      notifyObservers(UIActionType.Pause)
    }
    else if (game.gameVectors.vertices.nonEmpty) {
      this.pauseButton.setText("Pause")
      notifyObservers(UIActionType.Resume)
    }
  }

  def addPauseButton(isPaused: Boolean): Unit = {
    this.pauseButton.setText(if(isPaused) "Start" else "Pause")
    this.pauseButton.getStyleClass.add("button-raised")
    this.pauseButton.setOnMouseClicked(_ => this.pauseButtonAction())
    this.buttonBar.getChildren.add(this.pauseButton)
  }

  def addResetButton(): Unit = {
    val button = new Button("Reset")
    button.getStyleClass.add("button-raised")

    button.setOnMouseClicked(_ => {
      this.optionsBar.getChildren.clear()
      this.numOfPoints = 0
      notifyObservers(UIActionType.Reset)
      this.pauseButton.setText("Start")
    })

    this.buttonBar.getChildren.add(button)
  }

  private def chooseFile(): File = {
    val fileChooser = new FileChooser()
    fileChooser.getExtensionFilters.add(new FileChooser.ExtensionFilter("json files (*.json)", "*.json"))
    fileChooser.showOpenDialog(null)
  }

  def addLoadPresetButton(): Unit = {
    val button = new Button("Load preset")
    button.getStyleClass.add("button-raised")

    button.setOnMouseClicked(_ => {
      val file = chooseFile()
      val preset = Presets.loadPresetFromJSON(file.getAbsolutePath)
      preset match {
        case Some(preset) =>
          this.preset = Some(preset)
          notifyObservers(UIActionType.PresetLoaded)
          if(this.game.isPaused) this.pauseButtonAction()
        case None => println("Cannot load preset")
      }
    })

    this.buttonBar.getChildren.add(button)
  }

  def addSavePresetButton(): Unit = {
    val button = new Button("Save preset")
    button.getStyleClass.add("button-raised")

    button.setOnMouseClicked(_ => {
      val file = chooseFile()

      Presets.savePresetToJSON(file.getAbsolutePath, file.getName, game.getInitialVectorsPreset, game.getMultiplier, game.canReselectVertex)
    })

    this.buttonBar.getChildren.add(button)
  }

  def updateVertexChoiceBox(): Unit = {
    vertexChoiceBox.getItems.clear()
    for(vertex <- this.game.getInitialPoints) {
      vertexChoiceBox.getItems.add(vertex)
    }
  }

  def addVertexInfo(): Unit = {
    vertexChoiceBox.onActionProperty().setValue((t: Event) => {
      editedVertex = Some(t.getTarget.asInstanceOf[ChoiceBox[Vector2D]].getValue)
    })

    this.vertexInfo.getChildren.add(vertexChoiceBox)
  }

  def addDrawRadioButtons(): Unit = {
    val rbGroup = new ToggleGroup()

    val startingPointButton = new RadioButton("Adding starting point")
    startingPointButton.setToggleGroup(rbGroup)
    startingPointButton.setOnMouseClicked(_ => {
      this.notifyObservers(UIActionType.AddingStartingPoint)
    })

    val vertexButton = new RadioButton("Adding vertex")
    vertexButton.setToggleGroup(rbGroup)
    vertexButton.setOnMouseClicked(_ => {
      this.notifyObservers(UIActionType.AddingVertex)
    })

    vertexButton.setSelected(true)
    this.buttonBar.getChildren.add(startingPointButton)
    this.buttonBar.getChildren.add(vertexButton)
  }

  def receiveUpdate(game: Game, actionType: GameActionType): Unit = actionType match {
    case GameActionType.PointAdded => this.updateVertexChoiceBox()
    case GameActionType.NextStep =>
  }
}

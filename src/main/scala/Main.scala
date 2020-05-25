import javafx.application.{Application, Platform}
import javafx.stage.{Stage, WindowEvent}
import javafx.scene.Scene
import javafx.scene.layout._

object Main {
  def main(args: Array[String]): Unit = {
    Application.launch(classOf[Main], args: _*)
  }
}

class Main extends javafx.application.Application {
  val boardWidth = 800
  val sidebarWidth = 300
  val boardHeight = 800
  val game = new Game(boardWidth, boardHeight)
  val boardPane = new BoardPane(boardWidth, boardHeight, game)
  val sidePane = new SidePane(sidebarWidth, boardHeight, game, boardPane)

  game.addObserver(boardPane.receiveUpdate)
  game.addObserver(sidePane.receiveUpdate)

  sidePane.addObserver(game.receiveUpdate)
  sidePane.addObserver(boardPane.receiveUpdate)

  override def start(primaryStage: Stage): Unit = {
    game.setStartingPoint(Vector2D(200, 200))
    game.addPoint(Vector2D(100, 500))
    game.addPoint(Vector2D(500, 100))
    game.addPoint(Vector2D(100, 100))
    val root = new HBox(boardPane, sidePane)

    primaryStage.setOnCloseRequest((_: WindowEvent) => {
      Platform.exit()
      System.exit(0)
    })

    primaryStage.setTitle("Chaos Game")
    val primaryScene = new Scene(root, this.boardWidth + this.sidebarWidth, this.boardHeight)
    primaryStage.setScene(primaryScene)
    primaryStage.show()
    this.run()
  }

  def run(): Unit = {
    val thread = new Thread {
      override def run(): Unit = {
        while(true) {
          if(!game.isPaused){
            game.nextStep()
            Thread.sleep(1)}
          }
      }
    }

    thread.start()
  }


}
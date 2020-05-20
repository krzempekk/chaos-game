import javafx.application.Application
import javafx.stage.Stage
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
  val game = new Game
  val boardPane = new BoardPane(boardWidth, boardHeight, game)
  val sidePane = new SidePane(sidebarWidth, boardHeight,game, boardPane)

  override def start(primaryStage: Stage): Unit = {
    game.setStartingPoint(new Vector2D(200, 200))
    game.addPoint(new Vector2D(100, 500))
    game.addPoint(new Vector2D(500, 100))
    game.addPoint(new Vector2D(300, 300))
    val root = new HBox(boardPane, sidePane)

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
          boardPane.update()
          Thread.sleep(30)}
        }
      }
    }

    thread.start()
  }


}
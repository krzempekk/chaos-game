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

  override def start(primaryStage: Stage): Unit = {
    val game = new Game

    val boardPane = new BoardPane(boardWidth, boardHeight, game)
    val sidePane = new SidePane
    val root = new HBox(boardPane, sidePane)

    primaryStage.setTitle("Chaos Game")
    val primaryScene = new Scene(root, this.boardWidth + this.sidebarWidth, this.boardHeight)
    primaryStage.setScene(primaryScene)
    primaryStage.show()
  }
}
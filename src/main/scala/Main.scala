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
    game.setStartingPoint(new Vector2D(100, 100))
    game.addPoint(new Vector2D(10, 10))
    game.addPoint(new Vector2D(300, 10))
    game.addPoint(new Vector2D(10, 300))
    val boardPane = new BoardPane(boardWidth, boardHeight, game)
    val sidePane = new SidePane(sidebarWidth, boardHeight, game)
    val root = new HBox(boardPane, sidePane)

    primaryStage.setTitle("Chaos Game")
    val primaryScene = new Scene(root, this.boardWidth + this.sidebarWidth, this.boardHeight)
    primaryStage.setScene(primaryScene)
    primaryStage.show()


    for (i <- Range(1, 10)){
      game.nextStep()
      println(i)
      for(vector <- game.gameVectors.getList) println(vector)
      boardPane.update()
    }
  }


}
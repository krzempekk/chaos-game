import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import javafx.application.Application
import scalafx.scene.Scene

object Main extends JFXApp {
  stage = new PrimaryStage {
    title.value = "Chaos Game"
    width = 800
    height = 600
    scene = new Scene {
      
    }
  }
}

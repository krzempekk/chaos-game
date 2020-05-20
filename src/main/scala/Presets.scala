import scala.collection.mutable.ListBuffer

class Presets(var name: String) {
    def initialParameters: (Double,GameVectors) ={
        val vectors = new GameVectors
        name match {
            case "Sierpinsky" =>
                vectors.addInitialVectors(ListBuffer(new Vector2D(600,600),new Vector2D(0,600), new Vector2D(300,0)))
                (0.5,vectors)
            case "Square" =>
                vectors.addInitialVectors(ListBuffer(new Vector2D(0,0),new Vector2D(1000,1000), new Vector2D(0,1000), new Vector2D(1000,0)))
                (0.5,vectors)
            case "Pentagon"=>
                vectors.addInitialVectors(ListBuffer(new Vector2D(211,0), new Vector2D(789,0),new Vector2D(0,538),
                    new Vector2D(1000,538),new Vector2D(500,1000)))
                (0.5,vectors)
        }
    }
}

import scala.collection.mutable.ListBuffer

class Presets(var name: String, var width: Int, var height: Int) {
    def initialParameters: (Double,GameVectors,Boolean) ={
        val vectors = new GameVectors
        name match {
            case "Sierpinsky" =>
                vectors.addInitialVectors(ListBuffer(new Vector2D(width-10,height-10),new Vector2D(10,height-10), new Vector2D((width-20)/2,10)))
                (0.5,vectors,true)
            case "Rectangular" =>
                vectors.addInitialVectors(ListBuffer(new Vector2D(width-10,height-10),new Vector2D(10,height-10),
                  new Vector2D(10,10), new Vector2D(width-10,10)))
                (0.5,vectors,false)
            case "Pentagon"=>
                vectors.addInitialVectors(ListBuffer(new Vector2D((width-20)/5,height-10), new Vector2D(10,(height-20)/2)
                  ,new Vector2D((width-20)/2,10), new Vector2D(width-10,(height-20)/2),new Vector2D((width-20)*4/5,height-10)))
                (0.5,vectors,false)
            case "Vicsek"=>
                vectors.addInitialVectors(ListBuffer(new Vector2D(width-10,height-10),new Vector2D(10,height-10),
                    new Vector2D(10,10), new Vector2D(width-10,10), new Vector2D((width-20)/2,(height-20)/2)))
                (0.66,vectors,true)
        }
    }
}

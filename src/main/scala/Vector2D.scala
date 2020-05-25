class Vector2D private(val x: Int, val y: Int) {
  def +(other: Vector2D) = Vector2D(x + other.x, y + other.y)

  def unary_- = Vector2D(-x, -y)

  def -(other: Vector2D): Vector2D = this + (-other)

  def *(other: Vector2D): Vector2D = Vector2D(x * other.x, y + other.y)

  def <(other: Vector2D): Boolean = x < other.x && y < other.y

  def >(other: Vector2D): Boolean = x > other.x && y > other.y

  def <=(other: Vector2D): Boolean = this < other || this == other

  def length(): Double = math.sqrt(this.x * this.x + this.y * this.y)

  def distanceFrom(other: Vector2D): Int =
    math.sqrt(math.pow(this.x - other.x, 2)  + math.pow(this.y - other.y, 2)).toInt

  def getNextVector(other: Vector2D, fraction: Double): Vector2D = {
    Vector2D((this.x + (other.x - this.x) * fraction).toInt, (this.y + (other.y - this.y) * fraction).toInt)
  }

  def rotate(ang: Double): Vector2D =
    Vector2D((this.x * math.cos(ang) - this.y * math.sin(ang)).toInt, (this.x * math.sin(ang) + this.y * math.cos(ang)).toInt)

  override def equals(obj: Any): Boolean = obj match {
    case other: Vector2D => this.x == other.x && this.y == other.y
    case _ => false
  }

  override def hashCode(): Int = this.x.hashCode() * 2137 + this.y * 13

  override def toString: String = "(" + this.x.toString + "," + this.y.toString +")"

}

object Vector2D{
  def apply(x: Int, y: Int): Vector2D = new Vector2D(x, y)
}
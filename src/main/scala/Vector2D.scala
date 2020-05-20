class Vector2D(val x: Int, val y: Int) {
  def +(other: Vector2D) = new Vector2D(x + other.x, y + other.y)
  def unary_- = new Vector2D(-x, -y)
  def -(other: Vector2D): Vector2D = this + (-other)
  def *(other: Vector2D) = new Vector2D(x * other.x, y + other.y)
  def <(other: Vector2D): Boolean = x < other.x && y < other.y
  def >(other: Vector2D): Boolean = x > other.x && y > other.y
  def <=(other: Vector2D): Boolean = this < other || this == other

  override def equals(obj: Any): Boolean = obj match {
    case other: Vector2D => this.x == other.y && this.y == other.y
    case _ => false
  }

  override def hashCode(): Int = this.x.hashCode() * 2137 + this.y * 13

  override def toString: String = "(" + this.x.toString + "," + this.y.toString +")"
}

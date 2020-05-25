trait Subject[S, AT] {
  this: S =>
  private var observers: List[(S, AT) => Unit] = Nil
  def addObserver(observer: (S, AT) => Unit): Unit = observers = observer :: observers
  def notifyObservers(actionType: AT): Unit = observers.foreach(observer => observer(this, actionType))
}

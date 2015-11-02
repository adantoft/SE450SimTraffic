package timeserver;

public final class TimeServerLinked implements TimeServer {
  private static final class Node {
    final long waketime;
    final Agent agent;
    Node next;
  
    public Node(long waketime, Agent agent, Node next) {
      this.waketime = waketime;
      this.agent = agent;
      this.next = next;
    }
  }
  private long _currentTime;
  private int _size;
  private Node _head;

  /*
  * Invariant: _head != null
  * Invariant: _head.agent == null
  * Invariant: (_size == 0) iff (_head.next == null)
  */
  public TimeServerLinked() {
    _size = 0;
    _head = new Node(0, null, null);
  }

  public String toString() {
    StringBuilder sb = new StringBuilder("[");
    Node node = _head.next;
    String sep = "";
    while (node != null) {
      sb.append(sep).append("(").append(node.waketime).append(",")
        .append(node.agent).append(")");
      node = node.next;
      sep = ";";
    }
    sb.append("]");
    return (sb.toString());
  }

  public long currentTime() {
    return _currentTime;
  }

  public void enqueue(long waketime, Agent agent)
    throws IllegalArgumentException
  {
    if (waketime < _currentTime)
      throw new IllegalArgumentException();
    Node prevElement = _head;
    while ((prevElement.next != null) &&
        (prevElement.next.waketime <= waketime)) {
      prevElement = prevElement.next;
    }
    Node newElement = new Node(waketime, agent, prevElement.next);
    prevElement.next = newElement;
    _size++;
  }

  Agent dequeue()
  {
    if (_size < 1)
      throw new java.util.NoSuchElementException();
    Agent rval = _head.next.agent;
    _head.next = _head.next.next;
    _size--;
    return rval;
  }

  int size() {
    return _size;
  }

  boolean empty() {
    return size() == 0;
  }

  public void run(int duration) {
    long endtime = _currentTime + duration;
    while ((!empty()) && (_head.next.waketime <= endtime)) {
      _currentTime = _head.next.waketime;
      dequeue().run();
    }
    _currentTime = endtime;
  }
}




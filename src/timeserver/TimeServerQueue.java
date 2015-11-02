package timeserver;

import java.util.PriorityQueue;

public final class TimeServerQueue implements TimeServer {
  private static final class Node implements Comparable<Node> {
    final long waketime;
    final Agent agent;
    public Node(long waketime, Agent agent) {
      this.waketime = waketime;
      this.agent = agent;
    }
    public int compareTo(Node that) {
      return Long.signum(this.waketime - that.waketime);
    }
  }
  private long _currentTime;
  private PriorityQueue<Node> _queue;

  public TimeServerQueue() {
    _queue = new PriorityQueue<Node>();
  }

  public String toString() {
    StringBuilder sb = new StringBuilder("[");
    String sep = "";
    Node[] nodes = _queue.toArray(new Node[0]);
    java.util.Arrays.sort(nodes);
    for (Node node : nodes) {
      sb.append(sep).append("(").append(node.waketime).append(",")
        .append(node.agent).append(")");
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
    _queue.add(new Node(waketime, agent));
  }

  Agent dequeue()
  {
    return _queue.remove().agent;
  }

  int size() {
    return _queue.size();
  }

  boolean empty() {
    return _queue.isEmpty();
  }

  public void run(int duration) {
    long endtime = _currentTime + duration;
    while ((!empty()) && (_queue.peek().waketime <= endtime)) {
      _currentTime = _queue.peek().waketime;
      dequeue().run();
    }
    _currentTime = endtime;
  }
}




package timeserver;

import model.Agent;

public interface TimeServer {
    public double currentTime();
    public void enqueue(double waketime, Agent thing);
    public void run(double duration);
}

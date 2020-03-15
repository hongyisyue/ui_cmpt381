package sample;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by homeyxue on 2018-02-06.
 */
public class GraphModel {
    ArrayList<Vertex> vertexList;
    ArrayList<Edge> edgeList;
    ArrayList<listener> subscribers;

    public double portX = 0.0, portY=0.0;

    int nextID;

    public GraphModel(){
        vertexList = new ArrayList<>();
        edgeList = new ArrayList<>();
        subscribers = new ArrayList<>();
        nextID = 0;
    }

    public void addEdge(Vertex start, Vertex end){
        edgeList.add(new Edge(start, end));
        notifySubscribers();
    }

    public void addVertex(double x, double y){
        vertexList.add(new Vertex(x, y, nextID));
        nextID++;
        notifySubscribers();
    }

    public void moveVertex(Vertex v, double dx, double dy){
        if(v != null){
            v.x += dx;
            v.y += dy;
        }
        notifySubscribers();
        //notifySubscribersMove(dx, dy);
    }

    public void moveViewport(double dx, double dy) {
        //viewport moves happen in view coordinates, not model coordinates
        portX -= dx;
        portY -= dy;
        if (portX < 0.0) portX=0;
        if (portY < 0.0) portY=0;
        if (portX > 1000 - 500) portX = 1000-500;
        if (portY > 1000 - 500) portY = 1000-500;

        notifySubscribers();
    }

    public Optional<Vertex> findClickedVertex(double x, double y){
        return vertexList.stream()
                .filter(vertex -> vertex.contains(x, y))
                .reduce((b1, b2) -> b2);        //idk what this does
    }

    public boolean contains(double x, double y) {
        return vertexList.stream()
                .anyMatch(vertex -> vertex.contains(x, y));
    }




    public void addSubscriber(listener newSubscriber){
        subscribers.add(newSubscriber);
    }

    private void notifySubscribersMove(double dx, double dy) {
        subscribers.forEach(sub -> sub.modelChanged(dx,dy));
    }

    private void notifySubscribers(){
        subscribers.forEach(sub -> sub.modelChanged());
    }
}

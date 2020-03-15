package com.example.homeyxue.graphdemo;

/**
 * Created by homeyxue on 2018-02-06.
 */
import java.util.ArrayList;


    public class GraphModel {
        ArrayList<Vertex> vertices;
        ArrayList<Edge> edges;
        ArrayList<GraphModelListener> subscribers;
        int nextID;

        public GraphModel() {
            vertices = new ArrayList<Vertex>();
            edges = new ArrayList<Edge>();
            subscribers = new ArrayList<GraphModelListener>();
            nextID = 0;
        }

        public void createVertex(float x, float y) {
            vertices.add(new Vertex(x, y, nextID));
            nextID++;
            notifySubscribers();
        }

        public void createEdge(Vertex start, Vertex end) {
            edges.add(new Edge(start,end));
            notifySubscribers();
        }

        public void moveVertex(Vertex v, float dx, float dy) {
            v.move(dx, dy);
            notifySubscribers();
        }

        public Vertex findClick(float x, float y) {
            Vertex target = null;
            for (Vertex v : vertices) {
                if (v.contains(x, y)) {
                    target = v;
                }
            }
            return target;
        }

        public boolean contains(float x, float y) {
            boolean found = false;
            for (Vertex v : vertices) {
                if (v.contains(x, y)) {
                    found = true;
                }
            }
            return found;
        }

        // deletion is currently not required for Assignment 3
        public void deleteVertex(Vertex v) {
            ArrayList<Edge> remove = new ArrayList<Edge>();
            for (Edge e : edges) {
                if (e.start == v || e.end == v) {
                    remove.add(e);
                }
            }
            for (Edge e : remove) {
                edges.remove(e);
            }
            vertices.remove(v);
            notifySubscribers();
        }

        public void addSubscriber(GraphModelListener aSubscriber) {
            subscribers.add(aSubscriber);
        }

        public void removeSubscriber(GraphModelListener aSubscriber) {
            subscribers.remove(aSubscriber);
        }

        private void notifySubscribers() {
            for (GraphModelListener gml : subscribers) {
                gml.modelChanged();
            }
        }

        public static float dist(float x1, float y1, float x2, float y2) {
            return (float)Math.sqrt(Math.pow((double)x2-(double)x1,2) + Math.pow((double)y2-(double)y1,2));
        }
}

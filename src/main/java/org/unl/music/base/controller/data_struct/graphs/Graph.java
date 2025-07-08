package org.unl.music.base.controller.data_struct.graphs;

import org.unl.music.base.controller.data_struct.list.LinkedList;

public abstract class Graph {
    public abstract Integer nro_vertex();
    public abstract Integer nro_edge();
    public abstract Adjacency exist_edge(Integer o, Integer d);
    public abstract float weight_edge(Integer o, Integer d);
    public abstract void insert(Integer o, Integer d);
    public abstract void insert(Integer o, Integer d, float weight);
    public abstract LinkedList<Adjacency> adjacencies(Integer o);
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= nro_vertex(); i++){
            sb.append("Vertex = ").append(String.valueOf(i)).append(" \n");
            LinkedList<Adjacency> list = adjacencies(i);
            if (!list.isEmpty()) {
                Adjacency[] matrix = list.toArray();
                for (Adjacency adjacency : matrix) {
                    sb.append("\tAdjacency").append("\n").append(" \tVertex = ")
                    .append(String.valueOf(adjacency.getDestiny()));
                    if (!adjacency.getWeight().isNaN()) {
                        sb.append("weight ="+ adjacency.getWeight().toString()).append("\n");
                    }
                }
            }
        }
        return sb.toString();
    }
}
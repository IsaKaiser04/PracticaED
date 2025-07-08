package org.unl.music.base.controller.data_struct.graphs;

import org.unl.music.base.controller.data_struct.list.LinkedList;

import java.lang.reflect.Array;

public class DirectLabelGraph<E> extends DirectGraph {
    protected E labels[];
    protected LinkedList<VertexPair<E>> vertexDictionary;
    private Class clazz;

    public DirectLabelGraph(Integer nro_vertex, Class clazz) {
        super(nro_vertex);
        this.clazz = clazz;
        this.labels = (E[]) Array.newInstance(this.clazz, nro_vertex + 1);
        this.vertexDictionary = new LinkedList<>();
    }

    private static class VertexPair<E> {
        E label;
        Integer vertex;

        public VertexPair(E label, Integer vertex) {
            this.label = label;
            this.vertex = vertex;
        }
    }

    public Adjacency exist_edge_label(E o, E d) {
        return isLabelGraph() ? exist_edge(getVertex(o), getVertex(d)) : null;
    }

    public void insert_label(E o, E d, Float weight) {
        if (isLabelGraph()) {
            insert(getVertex(o), getVertex(d), weight);
        }
    }

    public void insert_label(E o, E d) {
        if (isLabelGraph()) {
            insert(getVertex(o), getVertex(d), Float.NaN);
        }
    }

    public LinkedList<Adjacency> adjacencies_label(E o) {
        return isLabelGraph() ? adjacencies(getVertex(o)) : new LinkedList<>();
    }

    public void label_vertex(Integer vertex, E data) {
        labels[vertex] = data;
        vertexDictionary.add(new VertexPair<>(data, vertex));
    }

    public Boolean isLabelGraph() {
        for (int i = 1; i <= nro_vertex(); i++) {
            if (labels[i] == null) {
                return false;
            }
        }
        return true;
    }

    public Integer getVertex(E label) {
        VertexPair<E>[] pairs = vertexDictionary.toArray();
        for (VertexPair<E> pair : pairs) {
            if (pair.label.equals(label)) {
                return pair.vertex;
            }
        }
        return null;
    }

    public E getLabel(Integer i) {
        return labels[i];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= nro_vertex(); i++) {
            sb.append("Vertex = ").append(getLabel(i)).append(" \n");
            LinkedList<Adjacency> list = adjacencies(i);
            if (!list.isEmpty()) {
                Adjacency[] matrix = list.toArray();
                for (Adjacency adjacency : matrix) {
                    sb.append(" \tAdjacency ").append("\n").append(" \tVertex = ")
                      .append(String.valueOf(getLabel(adjacency.getDestiny())));
                    if (!adjacency.getWeight().isNaN()) {
                        sb.append("weight =").append(adjacency.getWeight().toString()).append("\n");
                    }
                }
            }
        }
        return sb.toString();
    }
}
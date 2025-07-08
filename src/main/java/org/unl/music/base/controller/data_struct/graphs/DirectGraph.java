package org.unl.music.base.controller.data_struct.graphs;

import org.unl.music.base.controller.data_struct.list.LinkedList;

public class DirectGraph extends Graph {
    private Integer nro_vertex;
    private Integer nro_edge;
    private LinkedList<Adjacency> list_adjacencies[];

    public DirectGraph(Integer nro_vertex) {
        this.nro_vertex = nro_vertex;
        this.nro_edge = 0;
        this.list_adjacencies = new LinkedList[nro_vertex + 1];
        for (int i = 1; i <= nro_vertex; i++) {
            list_adjacencies[i] = new LinkedList<>();
        }
    }

    @Override
    public Integer nro_vertex() {
        return this.nro_vertex;
    }

    @Override
    public Integer nro_edge() {
        return this.nro_edge;
    }

    @Override
    public Adjacency exist_edge(Integer o, Integer d) {
        if (o <= nro_vertex && d <= nro_vertex) {
            LinkedList<Adjacency> list = list_adjacencies[o];
            if (!list.isEmpty()) {
                Adjacency[] matrix = list.toArray();
                for (Adjacency adj : matrix) {
                    if (adj.getDestiny().equals(d)) {
                        return adj;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public float weight_edge(Integer o, Integer d) {
        Adjacency adj = exist_edge(o, d);
        return adj != null ? adj.getWeight() : Float.NaN;
    }

    @Override
    public void insert(Integer o, Integer d) {
        insert(o, d, Float.NaN);
    }

    @Override
    public void insert(Integer o, Integer d, float weight) {
        if (o <= nro_vertex && d <= nro_vertex) {
            if (exist_edge(o, d) == null) {
                nro_edge++;
                Adjacency aux = new Adjacency(d, weight);
                list_adjacencies[o].add(aux);
            }
        } else {
            throw new ArrayIndexOutOfBoundsException("Vertex origin or destiny index out");
        }
    }

    @Override
    public LinkedList<Adjacency> adjacencies(Integer o) {
        return list_adjacencies[o];
    }

    public LinkedList<Adjacency>[] getList_adjacencies() {
        return list_adjacencies;
    }

    public void setNro_edge(Integer nro_edge) {
        this.nro_edge = nro_edge;
    }
}
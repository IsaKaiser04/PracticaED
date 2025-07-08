package org.unl.music.base.controller.data_struct.graphs;

public class UndirectedGraph extends DirectGraph {
    public UndirectedGraph(Integer nro_vertex) {
        super(nro_vertex);
    }

    @Override
    public void insert(Integer o, Integer d, float weight) {
        if (o <= nro_vertex() && d <= nro_vertex()) {
            if (exist_edge(o, d) == null) {
                setNro_edge(nro_edge() + 1); // Solo incrementar una vez
                
                // Insertar en ambas direcciones
                Adjacency aux1 = new Adjacency(d, weight);
                getList_adjacencies()[o].add(aux1);
                
                Adjacency aux2 = new Adjacency(o, weight);
                getList_adjacencies()[d].add(aux2);
            }
        } else {
            throw new ArrayIndexOutOfBoundsException("Vertex origin or destiny index out");
        }
    }
}
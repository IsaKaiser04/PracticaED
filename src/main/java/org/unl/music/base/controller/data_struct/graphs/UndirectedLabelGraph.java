package org.unl.music.base.controller.data_struct.graphs;

import org.unl.music.base.controller.data_struct.list.LinkedList;

public class UndirectedLabelGraph<E> extends DirectLabelGraph<E> {

    public UndirectedLabelGraph(Integer nro_vertex, Class clazz) {
        super(nro_vertex, clazz);
    }

    @Override
    public void insert_label(E o, E d, Float weight) {
        if (isLabelGraph()) {
            Integer vo = getVertex(o);
            Integer vd = getVertex(d);
            
            if (vo == null || vd == null) {
                throw new IllegalArgumentException("Vértices no encontrados");
            }
            
            if (exist_edge(vo, vd) == null) {
                setNro_edge(nro_edge() + 1);
                
                Adjacency aux1 = new Adjacency(vd, weight);
                getList_adjacencies()[vo].add(aux1);
                
                Adjacency aux2 = new Adjacency(vo, weight);
                getList_adjacencies()[vd].add(aux2);
            }
        }
    }

    public LinkedList<LinkedList<Adjacency>> getAdjacencyList() {
        LinkedList<LinkedList<Adjacency>> adjList = new LinkedList<>();
        for (int i = 1; i <= nro_vertex(); i++) {
            adjList.add(adjacencies(i));
        }
        return adjList;
    }

    public static class DijkstraSolver {
        public static class ResultadoDijkstra {
            public final int[] distancias;
            public final int[] padres;

            public ResultadoDijkstra(int[] distancias, int[] padres) {
                this.distancias = distancias;
                this.padres = padres;
            }
        }

        public static ResultadoDijkstra solve(LinkedList<LinkedList<Adjacency>> adj, int origen, int V) throws Exception {
            int[] dist = new int[V];
            int[] padres = new int[V];
            boolean[] visited = new boolean[V];
            
            for (int i = 0; i < V; i++) {
                dist[i] = Integer.MAX_VALUE;
                padres[i] = -1;
            }
            dist[origen] = 0;

            for (int count = 0; count < V; count++) {
                int u = findMinDistanceVertex(dist, visited);
                if (u == -1 || dist[u] == Integer.MAX_VALUE) break;
                
                visited[u] = true;
                LinkedList<Adjacency> neighbors = adj.get(u);
                
                for (int i = 0; i < neighbors.getLength(); i++) {
                    Adjacency v = neighbors.get(i);
                    int dest = v.getDestiny() - 1; // Ajuste a índice base 0
                    float weight = v.getWeight();
                    
                    if (!visited[dest] && dist[dest] > dist[u] + weight) {
                        dist[dest] = (int)(dist[u] + weight);
                        padres[dest] = u;
                    }
                }
            }
            return new ResultadoDijkstra(dist, padres);
        }

        private static int findMinDistanceVertex(int[] dist, boolean[] visited) {
            int minDist = Integer.MAX_VALUE;
            int minVertex = -1;
            
            for (int v = 0; v < dist.length; v++) {
                if (!visited[v] && dist[v] < minDist) {
                    minDist = dist[v];
                    minVertex = v;
                }
            }
            return minVertex;
        }
    }
}
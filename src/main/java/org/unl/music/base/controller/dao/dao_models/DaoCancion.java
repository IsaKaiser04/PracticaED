package org.unl.music.base.controller.dao.dao_models;
import org.unl.music.base.controller.dao.AdapterDao;
import org.unl.music.base.controller.data_struct.list.LinkedList;

import org.unl.music.base.models.Artista;
import org.unl.music.base.models.Cancion;
import org.unl.music.base.models.Utiles;


import java.util.HashMap;

public class DaoCancion extends AdapterDao<Cancion> {
    private Cancion obj; // instacia de cancion

    public DaoCancion() {
        super(Cancion.class);
        // constructor de la clase
    }

    public Cancion getObj() {// devuelve la instancia de cancion
        if (obj == null)
            this.obj = new Cancion();
        return this.obj;
    }

    public void setObj(Cancion obj) {// setea la instancia de cancion
        this.obj = obj;
    }

    public Boolean save() {
        try {
            if (obj.getId() == null) {
                obj.setId(listAll().getLength() + 1);
            }
            obj.setId(listAll().getLength() + 1);
            this.persist(obj);
            return true;
        } catch (Exception e) {
            //TODO
            return false;
        }
    }

    public Boolean update(Integer pos) {
        try {
            this.update(obj, pos);
            return true;
        } catch (Exception e) {
            //TODO
            return false;
        }
    }
/// //////////////////////LISTAR///////////////////////////////////////////////////////////////
    public LinkedList<HashMap<String, String>> all() throws Exception {
        LinkedList<HashMap<String, String>> lista = new LinkedList<>();
        if (!this.listAll().isEmpty()) {
            Cancion[] arreglo = this.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {

                lista.add(toDict(arreglo[i]));
            }
        }
        return lista;
    }


    private HashMap<String, String> toDict(Cancion arreglo) throws Exception {
        DaoCancion dc = new DaoCancion();
        HashMap<String, String> aux = new HashMap<>();
        aux.put("id", arreglo.getId().toString());
        aux.put("nombre", arreglo.getNombre());
        aux.put("genero", new DaoGenero().listAll().get(arreglo.getId_album()-1).getNombre());
        aux.put("duracion", arreglo.getDuracion().toString());
        aux.put("tipo", arreglo.getTipo().toString());
        aux.put("url", arreglo.getUrl());
        aux.put("album",new DaoAlbum().listAll().get(arreglo.getId_album()-1).getNombre()) ;
        return aux;
    }


    public LinkedList<HashMap<String, String>> orderByCancion(Integer type, String attribute ) throws Exception {
        LinkedList<HashMap<String, String>> lista = all();
        if (!lista.isEmpty()) {
            HashMap arr[] = lista.toArray();
            int n = arr.length;
            if (type == Utiles.ASCENDENTE) {
                for (int i = 0; i < n - 1; i++) {
                    int min_idx = i;
                    for (int j = i + 1; j < n; j++)
                        if (arr[j].get(attribute).toString().toLowerCase()
                                .compareTo(arr[min_idx].get(attribute).toString().toLowerCase()) < 0) {
                            min_idx = j;
                        }

                    HashMap temp = arr[min_idx];
                    arr[min_idx] = arr[i];
                    arr[i] = temp;
                }
            } else {
                for (int i = 0; i < n - 1; i++) {
                    int min_idx = i;
                    for (int j = i + 1; j < n; j++)
                        if (arr[j].get(attribute).toString().toLowerCase()
                                .compareTo(arr[min_idx].get(attribute).toString().toLowerCase()) > 0) {
                            min_idx = j;
                        }

                    HashMap temp = arr[min_idx];
                    arr[min_idx] = arr[i];
                    arr[i] = temp;
                }
            }
        }
        return lista;
    }
    /// ////////////////////// QUICK SORT ///////////////////////////////////
    /**
     * Metodo que ayuda a la particion de quick sort
     * @param arr arreglo de tipo Hashmaporden
     * @param begin indice del principio del arreglo
     * @param end indice del fin del arreglo
     * @param type orden ascendente o descendente
     * @param attribute atributo con el que se desea ordenar
     * @return entero
     */
    private int partition(HashMap<String, String> arr[], int begin, int end, Integer type, String attribute) {
        HashMap<String, String> pivot = arr[end];
        int i = (begin - 1);

        if (type == Utiles.ASCENDENTE) {
            for (int j = begin; j < end; j++) {
                if (arr[j].get(attribute).toString().toLowerCase().
                        compareTo(pivot.get(attribute).toString().toLowerCase()) < 0) {
                    i++;
                    HashMap<String, String> swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }

        } else {
            for (int j = begin; j < end; j++) {
                if (arr[j].get(attribute).toString().toLowerCase().
                        compareTo(pivot.get(attribute).toString().toLowerCase()) > 0) {
                    i++;
                    HashMap<String, String> swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        }
        HashMap<String, String> swapTemp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = swapTemp;

        return i + 1;
    }

    /**
     * Metodo que ayuda a la particion de quick sort
     * @param arr arreglo de tipo Hashmaporden
     * @param begin indice del principio del arreglo
     * @param end indice del fin del arreglo
     * @param type orden ascendente o descendente
     * @param attribute atributo con el que se desea ordenar
     */
    private void quicksort(HashMap<String, String> arr[], int begin, int end, Integer type, String attribute) {
        if(begin<end) {
            int partitionindex = partition(arr, begin, end, type, attribute);
            quicksort(arr, begin, partitionindex - 1, type, attribute);
            quicksort(arr, partitionindex + 1, end, type, attribute);
        }
    }

    /**
     * Metod encargado de llamar al quick sort
     * @param type determina el orden para ver si es ascendente o descendente
     * @param attribute atributo con el que se desea ordenar
     * @return retorna una lista de tipo LinkedList<HashMap<String,String>>
     * @throws Exception
     */
    public LinkedList<HashMap<String, String>> orderQuickCancion(Integer type, String attribute) throws Exception {
        LinkedList<HashMap<String, String>> lista = new LinkedList<>();
        if(!all().isEmpty()) {
            HashMap<String, String> arr[] = all().toArray();
            quicksort(arr, 0, arr.length - 1, type, attribute);
            lista.toList(arr);
        }
        return lista;
    }


    }





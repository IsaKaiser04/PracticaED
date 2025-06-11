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
        aux.put("genero", arreglo.getId_genero().toString());
        aux.put("duracion", arreglo.getDuracion().toString());
        aux.put("tipo", arreglo.getTipo().toString());
        aux.put("url", arreglo.getUrl());
        aux.put("album", arreglo.getId_album().toString());
        return aux;
    }


    public LinkedList<HashMap<String, String>> orderByCancion(Integer type, String attribute) throws Exception {
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

    private void quicksort(HashMap<String, String> arr[], int begin, int end, Integer type, String attribute) {
        if(begin<end) {
            int partitionindex = partition(arr, begin, end, type, attribute);
            quicksort(arr, begin, partitionindex - 1, type, attribute);
            quicksort(arr, partitionindex + 1, end, type, attribute);
        }
    }

    public LinkedList<HashMap<String, String>> orderQuickCancion(Integer type, String attribute) throws Exception {
        LinkedList<HashMap<String, String>> lista = new LinkedList<>();
        if(!all().isEmpty()) {
            HashMap<String, String> arr[] = all().toArray();
            quicksort(arr, 0, arr.length - 1, type, attribute);
            lista.toList(arr);
        }
        return lista;
    }
    ////////////////////////////SEARCH////////////////////////////////////////////////////////////
    public LinkedList<HashMap<String, String>> search(String attribute, String text, Integer type) throws Exception {
        LinkedList<HashMap<String, String>> lista = all();
        LinkedList<HashMap<String, String>> resp = new LinkedList<>();

        if (!lista.isEmpty()) {
            lista = orderQuickCancion(Utiles.ASCENDENTE, attribute);
            HashMap<String,String>[]arr = lista.toArray();
            Integer n = bynaryLineal(arr, attribute , text , type);
            System.out.println("La N de la Mitad"+n);
            switch (type) {
                case 1:
                    if(n > 0){
                        for (int i = 0; i < arr.length; i++) {
                            if(arr[i].get(attribute).toString().toLowerCase().startsWith(text.toLowerCase())){
                                resp.add(arr[i]);
                            }
                        }
                    }else if(n>0){
                        n *= -1;
                        for (int i = 0; i < n; i++) {
                            if(arr[i].get(attribute).toString().toLowerCase().startsWith(text.toLowerCase())){
                                resp.add(arr[i]);
                            }
                        }
                    }else{
                        for (int i = 0; i < arr.length; i++) {
                            if(arr[i].get(attribute).toString().toLowerCase().contains(text.toLowerCase())){
                                resp.add(arr[i]);
                            }
                        }

                    }
                    break;
                default:
                    System.out.println(attribute+""+text+"TRES"+n);
                    /*if(n>0){
                        for (int i = 0; i < arr.length; i++) {
                            if(arr[i].get(attribute).toString().toLowerCase().contains(text.toLowerCase())){
                                resp.add(arr[i]);
                            }
                        }
                    }else if(n>0){
                        n *= -1;
                        for (int i = 0; i < n; i++) {
                            if(arr[i].get(attribute).toString().toLowerCase().contains(text.toLowerCase())){
                                resp.add(arr[i]);
                            }
                        }

                    }else{
                        for (int i = 0; i < arr.length; i++) {
                            if(arr[i].get(attribute).toString().toLowerCase().contains(text.toLowerCase())){
                                resp.add(arr[i]);
                            }
                        }
                    }*/
                    break;
           }
        }
        return resp;

    }

    ////////////////////////////////////////////////BUSQUEDA BINARIA///////////////////////////////////////////////////////
    private Integer busquedaBinaria(HashMap<String, String>[] array, String attribute, String text, Integer type) {
        int left = 0;
        int right = array.length - 1;
        text = text.trim().toLowerCase();

        while (left <= right) {
            int mid = left + (right - left) / 2;
            String midValue = array[mid].get(attribute).toString().trim().toLowerCase();

            System.out.println("Comparando: " + text + " con " + midValue);

            if (text.equals(midValue)) {
                return mid;
            } else if (text.compareTo(midValue) < 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return -(left);
    }


    /////////////////////////////////////////////////BUSQUEDA BINARIA LINEAL///////////////////////////////////////////////////////

    private Integer bynaryLineal(HashMap<String, String>[] array, String attribute, String text, Integer type) {
            Integer half = 0;
        if (!(array.length == 0) && !text.isEmpty()){
            half = array.length / 2;
            int aux = 0;
            System.out.println(text.trim().toLowerCase().charAt(0)+"******* **"+half +" "+array[half].get(attribute). toString().trim().toLowerCase().charAt(0));
                if (text.trim().toLowerCase().charAt(0) > array[half].get(attribute).toString().toLowerCase().charAt(0))
                    aux = 1;
                else if (text.trim().toLowerCase().charAt(0) < array[half].get(attribute).toString().trim().toLowerCase().charAt(0))
                    aux = -1;
                    half = half * aux;
                }
        return half;
    }


    }





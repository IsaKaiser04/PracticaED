package org.unl.music.base.controller.dao.dao_models;


import org.unl.music.base.controller.dao.AdapterDao;
import org.unl.music.base.controller.data_struct.list.LinkedList;
import org.unl.music.base.models.Artista;
import org.unl.music.base.models.Utiles;



public class DaoArtista extends AdapterDao<Artista> {
    private Artista obj;
    
    public DaoArtista() {
        super(Artista.class);
        // TODO Auto-generated constructor stub
    }

    public Artista getObj() {
        if (obj == null)
            this.obj = new Artista();
        return this.obj;
    }

    public void setObj(Artista obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
            obj.setId(listAll().getLength()+1);
            this.persist(obj);
            return true;
        } catch (Exception e) {
            //TODO
            return false;
            // TODO: handle exception
        }
    }

    public Boolean update(Integer pos) {
        try {
            this.update(obj, pos);
            return true;
        } catch (Exception e) {
            //TODO
            return false;
            // TODO: handle exception
        }
    }

    
    public LinkedList<Artista> orderArtista(Integer type){
        LinkedList<Artista> lista = new LinkedList<>();
        if(!listAll().isEmpty()){
            Artista[] arr = listAll().toArray();
            int n = arr.length;

            for (int i = 0; i < n - 1; ++i) {
                int min_idx = i;

                for (int j = i + 1; j < n; ++j) {
                    if (arr[j].getNacionidad().toLowerCase()
                            .compareTo(arr[min_idx].getNacionidad().toLowerCase()) < 0) {

                        min_idx = j;
                    }
                    Artista temp = arr[min_idx];
                    arr[min_idx] = arr[i];
                    arr[i] = temp;
                }
            }
            lista.toList(arr);
        }
        return lista;
    }

    public LinkedList<Artista> orderArtistaName(Integer type){
        LinkedList<Artista> lista = new LinkedList<>();
        if(!listAll().isEmpty()){
            Artista[] arr = listAll().toArray();
            int n = arr.length;
        if(type == Utiles.ASCENDENTE){ 
            for (int i = 0; i < n - 1; ++i) {
                int min_idx = i;

                for (int j = i + 1; j < n; ++j) {
                    if (arr[j].getNombres().toLowerCase()
                            .compareTo(arr[min_idx].getNombres().toLowerCase()) < 0) {

                        min_idx = j;
                    }
                    Artista temp = arr[min_idx];
                    arr[min_idx] = arr[i];
                    arr[i] = temp;
                }
            }
            lista.toList(arr);
        }
    }
        
        return lista;
    }   
////////////////////////     SHELL    ///////////////////////////////////
    public LinkedList<Artista> shellArtistaName(Integer type){
    LinkedList<Artista> lista = new LinkedList<>();
    
    if (!listAll().isEmpty()) {
        Artista[] arr = listAll().toArray();
        int n = arr.length;

        if (type == Utiles.ASCENDENTE) {
            for (int gap = n / 2; gap > 0; gap /= 2) {
                for (int i = gap; i < n; i++) {
                    Artista temp = arr[i];
                    int j = i;

                    while (j >= gap && arr[j - gap].getNombres().toLowerCase()
                            .compareTo(temp.getNombres().toLowerCase()) < 0) {
                        arr[j] = arr[j - gap];
                        j -= gap;
                    }
                    arr[j] = temp;
                }
            }
        }

        lista.toList(arr);  // Asegúrate de que este método exista en tu LinkedList
    }

    return lista;
}

///////////////////////// QUICK SORT ///////////////////////////////////

public LinkedList<Artista> quicksortArtistaName() {
    Artista[] arr = listAll().toArray();  // Copia el array original
    quicksort(arr, 0, arr.length - 1);    // Ordena el array

    LinkedList<Artista> resultado = new LinkedList<>();
    resultado.toList(arr);  // Convierte el array ordenado en LinkedList
    return resultado;       // Retorna la lista lista 😄
}

private void quicksort(Artista[] A, int izq, int der) {
    if (izq < der) {
        int i = izq;
        int j = der;
        Artista pivote = A[izq];

        while (i < j) {
            while (i < j && A[j].getNombres().compareToIgnoreCase(pivote.getNombres()) >= 0) {
                j--;
            }
            if (i < j) {
                A[i] = A[j];
                i++;
            }

            while (i < j && A[i].getNombres().compareToIgnoreCase(pivote.getNombres()) < 0) {
                i++;
            }
            if (i < j) {
                A[j] = A[i];
                j--;
            }
        }

        A[i] = pivote;

        quicksort(A, izq, i - 1);
        quicksort(A, i + 1, der);
    }
}


    
    public static void main(String[] args) {
        DaoArtista da = new DaoArtista();
        System.out.println(da.quicksortArtistaName().print());

    }
    

}

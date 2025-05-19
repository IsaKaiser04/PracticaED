package org.unl.music.base.controller.dao.dao_models;
import org.unl.music.base.controller.dao.AdapterDao;
import org.unl.music.base.models.Cancion;

public class DaoCancion extends AdapterDao<Cancion>{
    private Cancion obj; // instacia de cancion

    public DaoCancion(){
        super(Cancion.class);
        // constructor de la clase
    }

    public Cancion getObj(){// devuelve la instancia de cancion
        if (obj == null)
            this.obj = new Cancion();
        return this.obj;
    }

    public void setObj(Cancion obj){// setea la instancia de cancion
        this.obj = obj;
    }

    public Boolean save(){
        try {
            if(obj.getId()== null){
                obj.setId(listAll().getLength()+1);
            }
            obj.setId(listAll().getLength()+1);
            this.persist(obj);
            return true;
        } catch (Exception e) {
            //TODO
            return false;
        }
    }

    public Boolean update(Integer pos){
        try {
            this.update(obj, pos);
            return true;
        } catch (Exception e) {
            //TODO
            return false;
        }
    }

}

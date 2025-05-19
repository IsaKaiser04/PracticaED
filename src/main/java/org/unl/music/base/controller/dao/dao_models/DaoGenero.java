package org.unl.music.base.controller.dao.dao_models;
import org.unl.music.base.controller.dao.AdapterDao;
import org.unl.music.base.models.Genero;

public class DaoGenero extends AdapterDao<Genero>{
    private Genero obj; // instacia de cancion

    public DaoGenero(){
        super(Genero.class);
        // constructor de la clase
    }

    public Genero getObj(){// devuelve la instancia de cancion
        if (obj == null)
            this.obj = new Genero();
        return this.obj;
    }

    public void setObj(Genero obj){// setea la instancia de cancion
        this.obj = obj;
    }

    public Boolean save(){
        try {
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
    
     public static void main(String[] args) {
        DaoGenero dg = new DaoGenero();
        dg.getObj().setId(dg.listAll().getLength() + 1);
        dg.getObj().setNombre("Balada");
        if (dg.save())
            System.out.println("GUARDADO");
        else
            System.out.println("Hubo un error");
        dg.getObj().setId(dg.listAll().getLength() + 1);
        dg.getObj().setNombre("Pop");
        if (dg.save())
            System.out.println("GUARDADO");
        else
            System.out.println("Hubo un error");
       
    }

}

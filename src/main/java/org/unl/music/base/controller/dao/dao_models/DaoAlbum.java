package org.unl.music.base.controller.dao.dao_models;
import org.unl.music.base.controller.dao.AdapterDao;
import org.unl.music.base.models.Album;

public class DaoAlbum extends AdapterDao<Album>{
    private Album obj; // instacia de cancion

    public DaoAlbum(){
        super(Album.class);
        // constructor de la clase
    }

    public Album getObj(){// devuelve la instancia de cancion
        if (obj == null)
            this.obj = new Album();
        return this.obj;
    }

    public void setObj(Album obj){// setea la instancia de cancion
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
        DaoAlbum dal = new DaoAlbum();
        dal.getObj().setId(dal.listAll().getLength() + 1);
        dal.getObj().setNombre("Idon");
        if (dal.save())
            System.out.println("GUARDADO");
        else
            System.out.println("Hubo un error");
        dal.getObj().setId(dal.listAll().getLength() + 1);
        dal.getObj().setNombre("thriller");
        if (dal.save())
            System.out.println("GUARDADO");
        else
            System.out.println("Hubo un error");
       
    }

}

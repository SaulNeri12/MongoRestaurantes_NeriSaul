/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pojos;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author Saul Neri
 */
public class Restaurante implements Documentable {

    private ObjectId _id;
    private String nombre;
    private int rating;
    private Instant fechainauguracion;
    private List<String> categorias;

    /**
     * 
     */
    public Restaurante() {
    }
    
    /**
     * 
     * @param _id
     * @param nombre
     * @param rating
     * @param fechainauguracion
     * @param categorias 
     */
    public Restaurante(ObjectId _id, String nombre, int rating, Instant fechainauguracion, List<String> categorias) {
        this._id = _id;
        this.nombre = nombre;
        this.rating = rating;
        this.fechainauguracion = fechainauguracion;
        this.categorias = categorias;
    }
    
    /**
     * 
     * @param _id 
     */
    public Restaurante(ObjectId _id) {
        this._id = _id;
    }

    /**
     * Obtiene el ID de mongo
     * @return 
     */
    public ObjectId getId() {
        return _id;
    }

    /**
     * Asigna el ID de mongo
     * @param _id 
     */
    public void setId(ObjectId _id) {
        this._id = _id;
    }

    /**
     * Obtiene el nombre del restaurante
     * @return 
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nombre del restaurante
     * @param nombre 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el rating el restaurante
     * @return 
     */
    public int getRating() {
        return rating;
    }

    /**
     * Asigna el rating del restaurante 
     * @param rating 
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Obtiene la fecha de inauguracion
     * @return 
     */
    public Instant getFechainauguracion() {
        return fechainauguracion;
    }

    /**
     * Asigna la fecha de inauguracion del restaurante
     * @param fechainauguracion 
     */
    public void setFechainauguracion(Instant fechainauguracion) {
        this.fechainauguracion = fechainauguracion;
    }

    /**
     * Obtiene las categorias del restaurante
     * @return 
     */
    public List<String> getCategorias() {
        return categorias;
    }

    /**
     * Asigna las categorias al restaurante
     * @param categorias 
     */
    public void setCategorias(List<String> categorias) {
        this.categorias = categorias;
    }

    @Override
    public String toString() {
        return "Restaurante{"
                + "_id=" + _id
                + ", nombre='" + nombre + '\''
                + ", rating=" + rating
                + ", fechainauguracion=" + fechainauguracion
                + ", categorias=" + categorias
                + '}';
    }

    @Override
    public Document toDocument() {
        Document doc = new Document();

        if (_id != null) {
            doc.append("_id", this._id);
        }

        doc.append("nombre", this.nombre);
        doc.append("rating", this.rating);
        doc.append("fechaInauguracion", this.fechainauguracion);
        doc.append("categorias", this.categorias);
        
        return doc;
    }
}

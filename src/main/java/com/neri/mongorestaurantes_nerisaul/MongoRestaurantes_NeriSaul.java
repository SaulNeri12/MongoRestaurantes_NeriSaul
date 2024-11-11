/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.neri.mongorestaurantes_nerisaul;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;
import pojos.Restaurante;

import javax.print.Doc;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Filter;
import java.util.regex.Pattern;

/**
 *
 * @author neri
 */
public class MongoRestaurantes_NeriSaul {

    public static void main(String[] args) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase  restaurantesBD = mongoClient.getDatabase("restaurantes");
            MongoCollection<Document> restaurantes = restaurantesBD.getCollection("restaurantes");


            System.out.println("Consulta de PRUEBA");
            try (MongoCursor<Document> cursor = restaurantes.find().iterator()) {
                // muestro cuantos registros tengo en la coleccion..
                while (cursor.hasNext()) {
                    Document doc = cursor.next();

                    Restaurante restaurante = new Restaurante();
                    restaurante.setId((ObjectId) doc.get("_id"));
                    restaurante.setNombre((doc.getString("nombre")));
                    restaurante.setRating(doc.getInteger("rating"));

                    List<String> categorias = doc.getList("categorias", String.class);

                    if (categorias != null) {
                        restaurante.setCategorias(categorias);
                    } else {
                        restaurante.setCategorias(Arrays.asList());
                        System.out.println("El campo 'categorias' no tiene el formato esperado");
                    }

                    System.out.println(restaurante);

                    System.out.println(doc.toJson()); // Imprime el documento en formato JSON
                }
            }


            /*
               1. Insertar 3 documentos (restaurantes) más con al menos 2 categorías cada uno.
               2. Consultar los restaurantes con más de 4 estrellas de rating.
               3. Consultar los restaurantes que incluyan la categoría pizza.
               4. Consultar los restaurantes que incluyan sushi en su nombre.
               5. Agregar una categoría extra al restaurant sushilito.
               6. Eliminar un restaurante por su identificador.
               7. Eliminar los restaurantes con 3 estrellas o menos.
           */

            // 1. Insertar 3 documentos (restaurantes) más con al menos 2 categorías cada uno.

            Restaurante restaurante = new Restaurante();
            restaurante.setNombre("Wiki Wiki Pizza");
            restaurante.setRating(3);
            restaurante.setCategorias(Arrays.asList("comida italiana", "boneless", "pizza"));
            restaurante.setFechainauguracion(Instant.now());

            restaurantes.insertOne(restaurante.toDocument());

            System.out.println("Se agrego: %s".formatted(restaurante.getNombre()));

            restaurante.setNombre("Chango's Pizza");
            restaurante.setRating(4);
            restaurante.setCategorias(Arrays.asList("comida italiana", "boneless", "pizza"));

            restaurantes.insertOne(restaurante.toDocument());

            System.out.println("Se agrego: %s".formatted(restaurante.getNombre()));

            restaurante.setNombre("Sushilito");
            restaurante.setRating(5);
            restaurante.setCategorias(Arrays.asList("sushi", "comida japonesa"));

            restaurantes.insertOne(restaurante.toDocument());

            System.out.println("Se agrego: %s".formatted(restaurante.getNombre()));

            // TERMINAN INSERCIONES...


            // 2. Consultar los restaurantes con más de 4 estrellas de rating
            try (MongoCursor<Document> cursor = restaurantes.find(Filters.gt("rating", 4)).iterator()) {
                // muestro cuantos registros tengo en la coleccion..
                System.out.println("#2 - RESTAURANTES CON RATING > 4");
                while (cursor.hasNext()) {
                    Document doc = cursor.next();
                    System.out.println(doc.toJson()); // Imprime el documento en formato JSON
                }
            }

            // 3. Consultar los restaurantes que incluyan la categoría pizza.
            try (MongoCursor<Document> cursor = restaurantes.find(Filters.in("categorias", "pizza")).iterator()) {
                // muestro cuantos registros tengo en la coleccion..
                System.out.println("#3 - RESTAURANTES CON CATEGORIA DE PIZZA");
                while (cursor.hasNext()) {
                    Document doc = cursor.next();
                    System.out.println(doc.toJson()); // Imprime el documento en formato JSON
                }
            }

            // 4. Consultar los restaurantes con sushi en su nombre
            try (MongoCursor<Document> cursor = restaurantes.find(Filters.regex("nombre", Pattern.compile("sushi", Pattern.CASE_INSENSITIVE))).iterator()) {
                // muestro cuantos registros tengo en la coleccion..
                System.out.println("#4. - RESTAURANTES CON \"SUSHI\" EN SU NOMBRE");
                while (cursor.hasNext()) {
                    Document doc = cursor.next();
                    System.out.println(doc.toJson()); // Imprime el documento en formato JSON
                }
            }



            // 5. Agregar una categoría extra al restaurant sushilito.
            restaurantes.updateOne(
                    Filters.eq(
                            "nombre",
                            "Sushilito"
                    ),
                    Updates.addToSet(
                            "categorias",
                            "boneless")
            );

            System.out.println("#5. Se actualizo la categoria de sushilito restaurante");

            // 6. Eliminar un restaurante por su identificador.
            Document sushilito = restaurantes.find(Filters.eq("nombre", "Sushilito")).first();
            restaurantes.deleteOne(Filters.eq("_id", sushilito.get("_id")));

            System.out.println("#6. Se elimino sushilito!!!");

            // 7. Eliminar los restaurantes con 3 estrellas o menos.
            restaurantes.deleteMany(Filters.lte("rating", 3));
            System.out.println("#7. Se eliminaron los restaurantes mal calificados!!!");

        }
    }
}

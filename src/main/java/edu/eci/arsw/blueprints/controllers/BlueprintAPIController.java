/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.service.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.PageRanges;

/**
 *
 * @author hcadavid
 */
@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {

    @Autowired
    BlueprintsServices blueprintsServices;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBluePrint() {
        try {
            Set<Blueprint> blueprints = blueprintsServices.getAllBlueprints();
            Gson gson = new Gson();
            return new ResponseEntity<>(gson.toJson(blueprints), HttpStatus.ACCEPTED);
        } catch (BlueprintPersistenceException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error en el metodo getAllBluePrints", HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(path = "/{author}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBlueprintByAuthor(@PathVariable String author) {
        try {
            Set<Blueprint> blueprints = blueprintsServices.getBlueprintsByAuthor(author);
            Gson gson = new Gson();
            return new ResponseEntity<>(gson.toJson(blueprints), HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error, No se encontro el Author", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/{author}/{bpname}" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBlueprintByAuthorName(@PathVariable String author, @PathVariable String bpname) {
        try {
            Blueprint blueprint = blueprintsServices.getBlueprint(author, bpname);
            Gson gson = new Gson();
            return new ResponseEntity<>(gson.toJson(blueprint), HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error, No se encontro el Author o el Blueprint", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postBlueprint(@RequestBody Blueprint blueprint) {
        try {
            blueprintsServices.addNewBlueprint(blueprint);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (BlueprintPersistenceException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("No se pudo Añadir el Blueprint",HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(path = "/{author}/{bpname}",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> putBlueprint(@PathVariable String author, @PathVariable String bpname, @RequestBody Blueprint blueprint) {
        try {
            blueprintsServices.updateBlueprint(author, bpname, blueprint);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException | BlueprintPersistenceException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>( "No se pudo actualizar el Blueprint", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/{author}/{bpname}", method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteBlueprint(@PathVariable String author, @PathVariable String bpname) throws BlueprintNotFoundException {
        blueprintsServices.deleteBlueprint(author, bpname);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


}


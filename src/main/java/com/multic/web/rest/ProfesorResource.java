package com.multic.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.multic.domain.Profesor;

import com.multic.repository.ProfesorRepository;
import com.multic.web.rest.util.HeaderUtil;
import com.multic.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Profesor.
 */
@RestController
@RequestMapping("/api")
public class ProfesorResource {

    private final Logger log = LoggerFactory.getLogger(ProfesorResource.class);

    private static final String ENTITY_NAME = "profesor";

    private final ProfesorRepository profesorRepository;

    public ProfesorResource(ProfesorRepository profesorRepository) {
        this.profesorRepository = profesorRepository;
    }

    /**
     * POST  /profesors : Create a new profesor.
     *
     * @param profesor the profesor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new profesor, or with status 400 (Bad Request) if the profesor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/profesors")
    @Timed
    public ResponseEntity<Profesor> createProfesor(@RequestBody Profesor profesor) throws URISyntaxException {
        log.debug("REST request to save Profesor : {}", profesor);
        if (profesor.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new profesor cannot already have an ID")).body(null);
        }
        Profesor result = profesorRepository.save(profesor);
        return ResponseEntity.created(new URI("/api/profesors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /profesors : Updates an existing profesor.
     *
     * @param profesor the profesor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated profesor,
     * or with status 400 (Bad Request) if the profesor is not valid,
     * or with status 500 (Internal Server Error) if the profesor couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/profesors")
    @Timed
    public ResponseEntity<Profesor> updateProfesor(@RequestBody Profesor profesor) throws URISyntaxException {
        log.debug("REST request to update Profesor : {}", profesor);
        if (profesor.getId() == null) {
            return createProfesor(profesor);
        }
        Profesor result = profesorRepository.save(profesor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, profesor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /profesors : get all the profesors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of profesors in body
     */
    @GetMapping("/profesors")
    @Timed
    public ResponseEntity<List<Profesor>> getAllProfesors(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Profesors");
        Page<Profesor> page = profesorRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/profesors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /profesors/:id : get the "id" profesor.
     *
     * @param id the id of the profesor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the profesor, or with status 404 (Not Found)
     */
    @GetMapping("/profesors/{id}")
    @Timed
    public ResponseEntity<Profesor> getProfesor(@PathVariable Long id) {
        log.debug("REST request to get Profesor : {}", id);
        Profesor profesor = profesorRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(profesor));
    }

    /**
     * DELETE  /profesors/:id : delete the "id" profesor.
     *
     * @param id the id of the profesor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/profesors/{id}")
    @Timed
    public ResponseEntity<Void> deleteProfesor(@PathVariable Long id) {
        log.debug("REST request to delete Profesor : {}", id);
        profesorRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

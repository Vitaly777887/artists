package amm.web.rest;

import com.codahale.metrics.annotation.Timed;
import amm.domain.Group0;
import amm.repository.Group0Repository;
import amm.web.rest.errors.BadRequestAlertException;
import amm.web.rest.util.HeaderUtil;
import amm.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Group0.
 */
@RestController
@RequestMapping("/api")
public class Group0Resource {

    private final Logger log = LoggerFactory.getLogger(Group0Resource.class);

    private static final String ENTITY_NAME = "group0";

    private final Group0Repository group0Repository;

    public Group0Resource(Group0Repository group0Repository) {
        this.group0Repository = group0Repository;
    }

    /**
     * POST  /group-0-s : Create a new group0.
     *
     * @param group0 the group0 to create
     * @return the ResponseEntity with status 201 (Created) and with body the new group0, or with status 400 (Bad Request) if the group0 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/group-0-s")
    @Timed
    public ResponseEntity<Group0> createGroup0(@Valid @RequestBody Group0 group0) throws URISyntaxException {
        log.debug("REST request to save Group0 : {}", group0);
        if (group0.getId() != null) {
            throw new BadRequestAlertException("A new group0 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Group0 result = group0Repository.save(group0);
        return ResponseEntity.created(new URI("/api/group-0-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /group-0-s : Updates an existing group0.
     *
     * @param group0 the group0 to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated group0,
     * or with status 400 (Bad Request) if the group0 is not valid,
     * or with status 500 (Internal Server Error) if the group0 couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/group-0-s")
    @Timed
    public ResponseEntity<Group0> updateGroup0(@Valid @RequestBody Group0 group0) throws URISyntaxException {
        log.debug("REST request to update Group0 : {}", group0);
        if (group0.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Group0 result = group0Repository.save(group0);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, group0.getId().toString()))
            .body(result);
    }

    /**
     * GET  /group-0-s : get all the group0S.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of group0S in body
     */
    @GetMapping("/group-0-s")
    @Timed
    public ResponseEntity<List<Group0>> getAllGroup0S(Pageable pageable) {
        log.debug("REST request to get a page of Group0S");
        Page<Group0> page = group0Repository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/group-0-s");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /group-0-s/:id : get the "id" group0.
     *
     * @param id the id of the group0 to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the group0, or with status 404 (Not Found)
     */
    @GetMapping("/group-0-s/{id}")
    @Timed
    public ResponseEntity<Group0> getGroup0(@PathVariable Long id) {
        log.debug("REST request to get Group0 : {}", id);
        Optional<Group0> group0 = group0Repository.findById(id);
        return ResponseUtil.wrapOrNotFound(group0);
    }

    /**
     * DELETE  /group-0-s/:id : delete the "id" group0.
     *
     * @param id the id of the group0 to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/group-0-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteGroup0(@PathVariable Long id) {
        log.debug("REST request to delete Group0 : {}", id);

        group0Repository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

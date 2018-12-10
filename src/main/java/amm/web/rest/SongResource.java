package amm.web.rest;

import com.codahale.metrics.annotation.Timed;
import amm.domain.Song;
import amm.repository.SongRepository;
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
 * REST controller for managing Song.
 */
@RestController
@RequestMapping("/api")
public class SongResource {

    private final Logger log = LoggerFactory.getLogger(SongResource.class);

    private static final String ENTITY_NAME = "song";

    private final SongRepository songRepository;

    public SongResource(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    /**
     * POST  /songs : Create a new song.
     *
     * @param song the song to create
     * @return the ResponseEntity with status 201 (Created) and with body the new song, or with status 400 (Bad Request) if the song has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/songs")
    @Timed
    public ResponseEntity<Song> createSong(@Valid @RequestBody Song song) throws URISyntaxException {
        log.debug("REST request to save Song : {}", song);
        if (song.getId() != null) {
            throw new BadRequestAlertException("A new song cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Song result = songRepository.save(song);
        return ResponseEntity.created(new URI("/api/songs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /songs : Updates an existing song.
     *
     * @param song the song to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated song,
     * or with status 400 (Bad Request) if the song is not valid,
     * or with status 500 (Internal Server Error) if the song couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/songs")
    @Timed
    public ResponseEntity<Song> updateSong(@Valid @RequestBody Song song) throws URISyntaxException {
        log.debug("REST request to update Song : {}", song);
        if (song.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Song result = songRepository.save(song);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, song.getId().toString()))
            .body(result);
    }

    /**
     * GET  /songs : get all the songs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of songs in body
     */
    @GetMapping("/songs")
    @Timed
    public ResponseEntity<List<Song>> getAllSongs(Pageable pageable) {
        log.debug("REST request to get a page of Songs");
        Page<Song> page = songRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/songs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /songs/:id : get the "id" song.
     *
     * @param id the id of the song to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the song, or with status 404 (Not Found)
     */
    @GetMapping("/songs/{id}")
    @Timed
    public ResponseEntity<Song> getSong(@PathVariable Long id) {
        log.debug("REST request to get Song : {}", id);
        Optional<Song> song = songRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(song);
    }

    /**
     * DELETE  /songs/:id : delete the "id" song.
     *
     * @param id the id of the song to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/songs/{id}")
    @Timed
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        log.debug("REST request to delete Song : {}", id);

        songRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

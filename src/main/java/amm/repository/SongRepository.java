package amm.repository;

import amm.domain.Song;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Song entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

}

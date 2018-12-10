package amm.repository;

import amm.domain.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Artist entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    @Query(value = "select distinct artist from Artist artist left join fetch artist.albums",
        countQuery = "select count(distinct artist) from Artist artist")
    Page<Artist> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct artist from Artist artist left join fetch artist.albums")
    List<Artist> findAllWithEagerRelationships();

    @Query("select artist from Artist artist left join fetch artist.albums where artist.id =:id")
    Optional<Artist> findOneWithEagerRelationships(@Param("id") Long id);

}

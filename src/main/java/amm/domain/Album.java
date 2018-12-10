package amm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Album.
 */
@Entity
@Table(name = "album")
public class Album implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "jhi_year")
    private Integer year;

    @NotNull
    @Column(name = "count_songs", nullable = false)
    private Integer countSongs;

    @OneToMany(mappedBy = "album")
    private Set<Song> songs = new HashSet<>();
    @OneToMany(mappedBy = "album")
    private Set<Group0> groups = new HashSet<>();
    @ManyToMany(mappedBy = "albums")
    @JsonIgnore
    private Set<Artist> artists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Album title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public Album year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCountSongs() {
        return countSongs;
    }

    public Album countSongs(Integer countSongs) {
        this.countSongs = countSongs;
        return this;
    }

    public void setCountSongs(Integer countSongs) {
        this.countSongs = countSongs;
    }

    public Set<Song> getSongs() {
        return songs;
    }

    public Album songs(Set<Song> songs) {
        this.songs = songs;
        return this;
    }

    public Album addSongs(Song song) {
        this.songs.add(song);
        song.setAlbum(this);
        return this;
    }

    public Album removeSongs(Song song) {
        this.songs.remove(song);
        song.setAlbum(null);
        return this;
    }

    public void setSongs(Set<Song> songs) {
        this.songs = songs;
    }

    public Set<Group0> getGroups() {
        return groups;
    }

    public Album groups(Set<Group0> group0S) {
        this.groups = group0S;
        return this;
    }

    public Album addGroups(Group0 group0) {
        this.groups.add(group0);
        group0.setAlbum(this);
        return this;
    }

    public Album removeGroups(Group0 group0) {
        this.groups.remove(group0);
        group0.setAlbum(null);
        return this;
    }

    public void setGroups(Set<Group0> group0S) {
        this.groups = group0S;
    }

    public Set<Artist> getArtists() {
        return artists;
    }

    public Album artists(Set<Artist> artists) {
        this.artists = artists;
        return this;
    }

    public Album addArtists(Artist artist) {
        this.artists.add(artist);
        artist.getAlbums().add(this);
        return this;
    }

    public Album removeArtists(Artist artist) {
        this.artists.remove(artist);
        artist.getAlbums().remove(this);
        return this;
    }

    public void setArtists(Set<Artist> artists) {
        this.artists = artists;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Album album = (Album) o;
        if (album.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), album.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Album{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", year=" + getYear() +
            ", countSongs=" + getCountSongs() +
            "}";
    }
}

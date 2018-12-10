package amm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import amm.domain.enumeration.Sex;

/**
 * A Artist.
 */
@Entity
@Table(name = "artist")
public class Artist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nickname", nullable = false)
    private String nickname;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "birthday")
    private LocalDate birthday;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false)
    private Sex sex;

    @Column(name = "site_url")
    private String siteUrl;

    @ManyToMany
    @JoinTable(name = "artist_albums",
               joinColumns = @JoinColumn(name = "artists_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "albums_id", referencedColumnName = "id"))
    private Set<Album> albums = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("artists")
    private Genre genre;

    @ManyToOne
    @JsonIgnoreProperties("artists")
    private Country country;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public Artist nickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public Artist name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public Artist surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public Artist birthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Sex getSex() {
        return sex;
    }

    public Artist sex(Sex sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public Artist siteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
        return this;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public Artist albums(Set<Album> albums) {
        this.albums = albums;
        return this;
    }

    public Artist addAlbums(Album album) {
        this.albums.add(album);
        album.getArtists().add(this);
        return this;
    }

    public Artist removeAlbums(Album album) {
        this.albums.remove(album);
        album.getArtists().remove(this);
        return this;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public Genre getGenre() {
        return genre;
    }

    public Artist genre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Country getCountry() {
        return country;
    }

    public Artist country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
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
        Artist artist = (Artist) o;
        if (artist.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), artist.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Artist{" +
            "id=" + getId() +
            ", nickname='" + getNickname() + "'" +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", sex='" + getSex() + "'" +
            ", siteUrl='" + getSiteUrl() + "'" +
            "}";
    }
}

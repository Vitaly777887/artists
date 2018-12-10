package amm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Group0.
 */
@Entity
@Table(name = "group_0")
public class Group0 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nickname", nullable = false)
    private String nickname;

    @ManyToOne
    @JsonIgnoreProperties("groups")
    private Album album;

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

    public Group0 nickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Album getAlbum() {
        return album;
    }

    public Group0 album(Album album) {
        this.album = album;
        return this;
    }

    public void setAlbum(Album album) {
        this.album = album;
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
        Group0 group0 = (Group0) o;
        if (group0.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), group0.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Group0{" +
            "id=" + getId() +
            ", nickname='" + getNickname() + "'" +
            "}";
    }
}

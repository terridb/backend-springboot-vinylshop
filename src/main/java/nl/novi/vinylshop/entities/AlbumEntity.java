package nl.novi.vinylshop.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "albums")
public class AlbumEntity extends BaseEntity {
    private String title;
    private int releaseYear;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private PublisherEntity publisher;

    @OneToMany(mappedBy = "album")
    private List<StockEntity> stock = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "album_artist",
            joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    private Set<ArtistEntity> artists = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "genre_id")
    private GenreEntity genre;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public PublisherEntity getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherEntity publisher) {
        this.publisher = publisher;
    }

    public List<StockEntity> getStock() {
        return stock;
    }

    public void setStock(List<StockEntity> stock) {
        this.stock = stock;
    }

    public Set<ArtistEntity> getArtists() {
        return artists;
    }

    public void setArtists(Set<ArtistEntity> artists) {
        this.artists = artists;
    }

    public GenreEntity getGenre() {
        return genre;
    }

    public void setGenre(GenreEntity genre) {
        this.genre = genre;
    }
}

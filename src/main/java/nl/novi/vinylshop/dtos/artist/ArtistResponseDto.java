package nl.novi.vinylshop.dtos.artist;

import nl.novi.vinylshop.dtos.album.AlbumResponseDto;

import java.util.List;

public class ArtistResponseDto {
    private Long id;
    private String name;
    private String biography;
    private List<AlbumResponseDto> albums;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public List<AlbumResponseDto> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumResponseDto> albums) {
        this.albums = albums;
    }
}

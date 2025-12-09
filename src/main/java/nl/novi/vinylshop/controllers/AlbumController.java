package nl.novi.vinylshop.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import nl.novi.vinylshop.dtos.album.AlbumExtendedResponseDto;
import nl.novi.vinylshop.dtos.album.AlbumRequestDto;
import nl.novi.vinylshop.dtos.album.AlbumResponseDto;
import nl.novi.vinylshop.dtos.artist.ArtistResponseDto;
import nl.novi.vinylshop.dtos.genre.GenreRequestDTO;
import nl.novi.vinylshop.dtos.genre.GenreResponseDTO;
import nl.novi.vinylshop.helpers.UrlHelper;
import nl.novi.vinylshop.services.AlbumService;
import nl.novi.vinylshop.services.ArtistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    private final AlbumService albumService;
    private final UrlHelper urlHelper;
    private final ArtistService artistService;

    public AlbumController(AlbumService albumService, UrlHelper urlHelper, ArtistService artistService) {
        this.albumService = albumService;
        this.urlHelper = urlHelper;
        this.artistService = artistService;
    }

    @GetMapping
    public ResponseEntity<List<AlbumResponseDto>> getAllAlbums(@RequestParam(required = false) Boolean stock) {
        List<AlbumResponseDto> albums;
        if(stock == null) {
            albums = albumService.findAllAlbums();
        } else {
            albums = albumService.getAlbumsWithStock(stock);
        }
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumExtendedResponseDto> getAlbumById(@PathVariable Long id) throws EntityNotFoundException {
        AlbumExtendedResponseDto album = albumService.findAlbumById(id);
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AlbumResponseDto> createAlbum(@RequestBody @Valid AlbumRequestDto albumModel) {
        AlbumResponseDto newAlbum = albumService.createAlbum(albumModel);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(newAlbum.getId())).body(newAlbum);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlbumResponseDto> updateAlbum(@PathVariable Long id, @RequestBody @Valid AlbumRequestDto albumModel) throws EntityNotFoundException {
        AlbumResponseDto updatedAlbum = albumService.updateAlbum(id, albumModel);
        return new ResponseEntity<>(updatedAlbum, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long id) {
        albumService.deleteAlbum(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{albumId}/artists/{artistId}")
    public ResponseEntity<Void> linkArtist(@PathVariable Long albumId, @PathVariable Long artistId) {
        albumService.linkArtist(albumId, artistId);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{albumId}/artists/{artistId}")
    public ResponseEntity<Void> unlinkArtist(@PathVariable Long albumId, @PathVariable Long artistId) {
        albumService.unlinkArtist(albumId, artistId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/artists")
    public ResponseEntity<List<ArtistResponseDto>> linkArtist(@PathVariable Long id){
        List<ArtistResponseDto> artists = artistService.getArtistsForAlbum(id);
        return ResponseEntity.ok(artists);
    }

}

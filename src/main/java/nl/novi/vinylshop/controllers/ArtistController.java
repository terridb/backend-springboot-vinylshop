package nl.novi.vinylshop.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import nl.novi.vinylshop.dtos.artist.ArtistRequestDto;
import nl.novi.vinylshop.dtos.artist.ArtistResponseDto;
import nl.novi.vinylshop.helpers.UrlHelper;
import nl.novi.vinylshop.services.ArtistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService artistService;
    private final UrlHelper urlHelper;

    public ArtistController(ArtistService artistService, UrlHelper urlHelper) {
        this.artistService = artistService;
        this.urlHelper = urlHelper;
    }

    @GetMapping
    public ResponseEntity<List<ArtistResponseDto>> getAllArtists() {
        List<ArtistResponseDto> artists = artistService.findAllArtists();
        return new ResponseEntity<>(artists, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponseDto> getArtistDto(@PathVariable Long id) throws EntityNotFoundException {
        ArtistResponseDto artist = artistService.findArtistById(id);
        return new ResponseEntity<>(artist, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ArtistResponseDto> createArtist(@RequestBody @Valid ArtistRequestDto artistModel) {
        ArtistResponseDto newArtist = artistService.createArtist(artistModel);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(newArtist.getId())).body(newArtist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistResponseDto> updateArtist(@PathVariable Long id, @RequestBody @Valid ArtistRequestDto artistModel) throws EntityNotFoundException {
        ArtistResponseDto updatedArtist = artistService.updateArtist(id, artistModel);
        return new ResponseEntity<>(updatedArtist, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

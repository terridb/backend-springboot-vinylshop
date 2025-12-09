package nl.novi.vinylshop.services;

import jakarta.persistence.EntityNotFoundException;
import nl.novi.vinylshop.dtos.album.AlbumExtendedResponseDto;
import nl.novi.vinylshop.dtos.album.AlbumRequestDto;
import nl.novi.vinylshop.dtos.album.AlbumResponseDto;
import nl.novi.vinylshop.entities.AlbumEntity;
import nl.novi.vinylshop.entities.ArtistEntity;
import nl.novi.vinylshop.entities.GenreEntity;
import nl.novi.vinylshop.entities.PublisherEntity;
import nl.novi.vinylshop.mappers.AlbumDTOMapper;
import nl.novi.vinylshop.mappers.AlbumExtendedDTOMapper;
import nl.novi.vinylshop.repositories.AlbumRepository;
import nl.novi.vinylshop.repositories.ArtistRepository;
import nl.novi.vinylshop.repositories.GenreRepository;
import nl.novi.vinylshop.repositories.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final AlbumDTOMapper albumDTOMapper;
    private final GenreRepository genreRepository;
    private final PublisherRepository publisherRepository;
    private final AlbumExtendedDTOMapper albumExtendedDTOMapper;
    private final ArtistRepository artistRepository;

    public AlbumService(AlbumRepository albumRepository, AlbumDTOMapper albumDTOMapper, GenreRepository genreRepository, PublisherRepository publisherRepository, AlbumExtendedDTOMapper albumExtendedDTOMapper, ArtistRepository artistRepository) {
        this.albumRepository = albumRepository;
        this.albumDTOMapper = albumDTOMapper;
        this.genreRepository = genreRepository;
        this.publisherRepository = publisherRepository;
        this.albumExtendedDTOMapper = albumExtendedDTOMapper;
        this.artistRepository = artistRepository;
    }

    public List<AlbumResponseDto> findAllAlbums() {
        return albumDTOMapper.mapToDto(albumRepository.findAll());
    }

    public List<AlbumResponseDto> getAlbumsWithStock(Boolean stock) {
        if (stock) {
            return albumDTOMapper.mapToDto(albumRepository.findByStockNotEmpty());
        } else {
            return albumDTOMapper.mapToDto(albumRepository.findByStockEmpty());
        }
    }

    public AlbumExtendedResponseDto findAlbumById(Long id) throws EntityNotFoundException {
        AlbumEntity albumEntity = getAlbumEntity(id);
        return albumExtendedDTOMapper.mapToDto(albumEntity);
    }

    public AlbumResponseDto createAlbum(AlbumRequestDto albumRequestDto) {
        AlbumEntity albumEntity = albumDTOMapper.mapToEntity(albumRequestDto);
        GenreEntity genreEntity = getGenreEntity(albumRequestDto.getGenreId());
        PublisherEntity publisherEntity = getPublisherEntity(albumRequestDto.getPublisherId());

        albumEntity.setGenre(genreEntity);
        albumEntity.setPublisher(publisherEntity);

        albumEntity = albumRepository.save(albumEntity);
        return albumDTOMapper.mapToDto(albumEntity);
    }

    public AlbumResponseDto updateAlbum(Long id, AlbumRequestDto requestDto) throws EntityNotFoundException {
        AlbumEntity existingAlbumEntity = getAlbumEntity(id);
        GenreEntity genreEntity = getGenreEntity(requestDto.getGenreId());
        PublisherEntity publisherEntity = getPublisherEntity(requestDto.getPublisherId());

        existingAlbumEntity.setGenre(genreEntity);
        existingAlbumEntity.setPublisher(publisherEntity);
        existingAlbumEntity.setTitle(requestDto.getTitle());
        existingAlbumEntity.setReleaseYear(requestDto.getReleaseYear());

        existingAlbumEntity = albumRepository.save(existingAlbumEntity);
        return albumDTOMapper.mapToDto(existingAlbumEntity);
    }

    public void deleteAlbum(Long id) {
        AlbumEntity album = getAlbumEntity(id);

        if (album.getStock() == null || album.getStock().isEmpty()) {
            albumRepository.delete(album);
        } else {
            throw new RuntimeException("You can't delete an album with existing stock");
        }

    }

    public void linkArtist(Long albumId, Long artistId) {
        AlbumEntity album = getAlbumEntity(albumId);
        ArtistEntity artist = getArtistEntity(artistId);

        album.getArtists().add(artist);
        artist.getAlbums().add(album);

        albumRepository.save(album);
    }

    public void unlinkArtist(Long albumId, Long artistId) {
        AlbumEntity album = getAlbumEntity(albumId);
        ArtistEntity artist = getArtistEntity(artistId);

        album.getArtists().remove(artist);
        artist.getAlbums().remove(album);

        albumRepository.save(album);
    }

    public AlbumEntity getAlbumEntity(Long id) {
        return albumRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Album " + id +" not found"));
    }

    private GenreEntity getGenreEntity(long genreId) {
        return genreRepository.findById(genreId).orElseThrow(() -> new EntityNotFoundException("Genre " + genreId + " not found"));
    }

    private ArtistEntity getArtistEntity(long artistId) {
        return artistRepository.findById(artistId).orElseThrow(() -> new EntityNotFoundException("Artist " + artistId + " not found"));
    }

    private PublisherEntity getPublisherEntity(long publisherId) {
        return publisherRepository.findById(publisherId).orElseThrow(() -> new EntityNotFoundException("Publisher " + publisherId + " not found"));
    }
}

package nl.novi.vinylshop.services;

import jakarta.persistence.EntityNotFoundException;
import nl.novi.vinylshop.dtos.album.AlbumRequestDto;
import nl.novi.vinylshop.dtos.album.AlbumResponseDto;
import nl.novi.vinylshop.entities.AlbumEntity;
import nl.novi.vinylshop.mappers.AlbumDTOMapper;
import nl.novi.vinylshop.repositories.AlbumRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final AlbumDTOMapper albumDTOMapper;

    public AlbumService(AlbumRepository albumRepository, AlbumDTOMapper albumDTOMapper) {
        this.albumRepository = albumRepository;
        this.albumDTOMapper = albumDTOMapper;
    }

    public List<AlbumResponseDto> findAllAlbums() {
        return albumDTOMapper.mapToDto(albumRepository.findAll());
    }

    public AlbumResponseDto findAlbumById(Long id) throws EntityNotFoundException {
        AlbumEntity albumEntity = getAlbumEntity(id);
        return albumDTOMapper.mapToDto(albumEntity);
    }

    public AlbumResponseDto createAlbum(AlbumRequestDto albumRequestDto) {
        AlbumEntity albumEntity = albumDTOMapper.mapToEntity(albumRequestDto);
        albumEntity = albumRepository.save(albumEntity);
        return albumDTOMapper.mapToDto(albumEntity);
    }

    public AlbumResponseDto updateAlbum(Long id, AlbumRequestDto requestDto) throws EntityNotFoundException {
        AlbumEntity existingAlbumEntity = getAlbumEntity(id);

        existingAlbumEntity.setTitle(requestDto.getTitle());
        existingAlbumEntity.setReleaseYear(requestDto.getReleaseYear());

        existingAlbumEntity = albumRepository.save(existingAlbumEntity);
        return albumDTOMapper.mapToDto(existingAlbumEntity);
    }

    public void deleteAlbum(Long id) {
        albumRepository.deleteById(id);
    }

    private AlbumEntity getAlbumEntity(Long id) {
        return albumRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Album " + id +" not found"));
    }
}

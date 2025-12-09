package nl.novi.vinylshop.services;

import jakarta.persistence.EntityNotFoundException;
import nl.novi.vinylshop.dtos.artist.ArtistRequestDto;
import nl.novi.vinylshop.dtos.artist.ArtistResponseDto;
import nl.novi.vinylshop.entities.ArtistEntity;
import nl.novi.vinylshop.mappers.ArtistDTOMapper;
import nl.novi.vinylshop.repositories.ArtistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistDTOMapper artistDTOMapper;

    public ArtistService(ArtistRepository artistRepository, ArtistDTOMapper artistDTOMapper) {
        this.artistRepository = artistRepository;
        this.artistDTOMapper = artistDTOMapper;
    }

    public List<ArtistResponseDto> findAllArtists() {
        return artistDTOMapper.mapToDto(artistRepository.findAll());
    }

    public ArtistResponseDto findArtistById(Long id) throws EntityNotFoundException {
        ArtistEntity artistEntity = getArtistEntity(id);
        return artistDTOMapper.mapToDto(artistEntity);
    }

    public ArtistResponseDto createArtist(ArtistRequestDto requestDto) {
        ArtistEntity artistEntity = artistDTOMapper.mapToEntity(requestDto);
        artistEntity = artistRepository.save(artistEntity);
        return artistDTOMapper.mapToDto(artistEntity);
    }

    public ArtistResponseDto updateArtist(Long id, ArtistRequestDto requestDto) throws EntityNotFoundException {
        ArtistEntity existingArtistEntity = getArtistEntity(id);

        existingArtistEntity.setName(requestDto.getName());
        existingArtistEntity.setBiography(requestDto.getBiography());

        existingArtistEntity = artistRepository.save(existingArtistEntity);
        return artistDTOMapper.mapToDto(existingArtistEntity);
    }

    public void deleteArtist(Long id) {
        artistRepository.deleteById(id);
    }

    public List<ArtistResponseDto> getArtistsForAlbum(Long id) {
        List<ArtistEntity> artists = artistRepository.findArtistsByAlbumsId(id);
        return artistDTOMapper.mapToDto(artists);
    }

    private ArtistEntity getArtistEntity(Long id) {
        return artistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Artist " + id +" not found"));
    }

}

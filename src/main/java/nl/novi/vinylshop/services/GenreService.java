package nl.novi.vinylshop.services;

import jakarta.persistence.EntityNotFoundException;
import nl.novi.vinylshop.dtos.genre.GenreRequestDTO;
import nl.novi.vinylshop.dtos.genre.GenreResponseDTO;
import nl.novi.vinylshop.entities.AlbumEntity;
import nl.novi.vinylshop.entities.GenreEntity;
import nl.novi.vinylshop.mappers.GenreDTOMapper;
import nl.novi.vinylshop.repositories.AlbumRepository;
import nl.novi.vinylshop.repositories.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final GenreDTOMapper genreDTOMapper;
    private final AlbumRepository albumRepository;


    public GenreService(GenreRepository genreRepository, GenreDTOMapper genreDTOMapper, AlbumRepository albumRepository) {
        this.genreRepository = genreRepository;
        this.genreDTOMapper = genreDTOMapper;
        this.albumRepository = albumRepository;
    }



    public List<GenreResponseDTO> findAllGenres() {
        return genreDTOMapper.mapToDto(genreRepository.findAll());
    }

    public GenreResponseDTO findGenreById(Long id) throws EntityNotFoundException {
        nl.novi.vinylshop.entities.GenreEntity genreEntity = getGenreEntity(id);
        return genreDTOMapper.mapToDto(genreEntity);
    }

    public GenreResponseDTO createGenre(GenreRequestDTO genreDTO) {
        GenreEntity genreEntity = genreDTOMapper.mapToEntity(genreDTO);
        genreEntity = genreRepository.save(genreEntity);
        return genreDTOMapper.mapToDto(genreEntity);
    }

    public GenreResponseDTO updateGenre(Long id, GenreRequestDTO requestDto) throws EntityNotFoundException {
        GenreEntity existingGenreEntity = getGenreEntity(id);

        existingGenreEntity.setName(requestDto.getName());
        existingGenreEntity.setDescription(requestDto.getDescription());

        existingGenreEntity = genreRepository.save(existingGenreEntity);
        return genreDTOMapper.mapToDto(existingGenreEntity);
    }

    private GenreEntity getGenreEntity(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genre " + id +" not found"));
    }

    public void deleteGenre(Long id) {
        GenreEntity genre = genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genre " + id +" not found"));

        List<AlbumEntity> albums = albumRepository.findByGenre_Id(id);
        for (AlbumEntity album : albums) {
            album.setGenre(null);
            albumRepository.save(album);
        }

        genreRepository.delete(genre);
    }

    private GenreEntity getGenreById(Long id){
        Optional<GenreEntity> genreEntityOptional = genreRepository.findById(id);

//        Een if-statement waar je expliciet de Optional.isPresent() of Optional.isEmpty() checkt, is één variant om met de optional om te gaan.
        if(genreEntityOptional.isPresent()){
            return genreEntityOptional.get();
        } else {
            return null;
        }
    }
}

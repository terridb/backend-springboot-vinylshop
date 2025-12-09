package nl.novi.vinylshop.mappers;

import nl.novi.vinylshop.dtos.album.AlbumRequestDto;
import nl.novi.vinylshop.dtos.album.AlbumResponseDto;
import nl.novi.vinylshop.entities.AlbumEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AlbumDTOMapper implements DTOMapper<AlbumResponseDto, AlbumRequestDto, AlbumEntity> {

    private final GenreDTOMapper genreDTOMapper;
    private final PublisherDTOMapper publisherDTOMapper;

    public AlbumDTOMapper(GenreDTOMapper genreDTOMapper, PublisherDTOMapper publisherDTOMapper) {
        this.genreDTOMapper = genreDTOMapper;
        this.publisherDTOMapper = publisherDTOMapper;
    }

    @Override
    public AlbumResponseDto mapToDto(AlbumEntity model) {

        var result = new AlbumResponseDto();
        result.setId(model.getId());
        result.setTitle(model.getTitle());
        result.setReleaseYear(model.getReleaseYear());

        if (model.getGenre() != null) {
            result.setGenre(genreDTOMapper.mapToDto(model.getGenre()));
        }

        if (model.getPublisher() != null) {
            result.setPublisher(publisherDTOMapper.mapToDto(model.getPublisher()));
        }

        return result;
    }

    @Override
    public List<AlbumResponseDto> mapToDto(List<AlbumEntity> models) {
        var result = new ArrayList<AlbumResponseDto>();
        for (AlbumEntity model : models) {
            result.add(mapToDto(model));
        }
        return result;
    }

    @Override
    public AlbumEntity mapToEntity(AlbumRequestDto albumModel) {
        var result = new AlbumEntity();
        result.setTitle(albumModel.getTitle());
        result.setReleaseYear(albumModel.getReleaseYear());
        return result;
    }
}

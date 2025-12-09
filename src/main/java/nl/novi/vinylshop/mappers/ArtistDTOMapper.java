package nl.novi.vinylshop.mappers;

import nl.novi.vinylshop.dtos.artist.ArtistRequestDto;

import nl.novi.vinylshop.dtos.artist.ArtistResponseDto;
import nl.novi.vinylshop.entities.ArtistEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArtistDTOMapper implements DTOMapper<ArtistResponseDto, ArtistRequestDto, ArtistEntity> {

    private final AlbumDTOMapper albumDTOMapper;

    public ArtistDTOMapper(AlbumDTOMapper albumDTOMapper) {
        this.albumDTOMapper = albumDTOMapper;
    }

    @Override
    public ArtistResponseDto mapToDto(ArtistEntity model) {

        var result = new ArtistResponseDto();
        result.setId(model.getId());
        result.setName(model.getName());
        result.setBiography(model.getBiography());

        if (!model.getAlbums().isEmpty() && model.getAlbums() != null) {
            result.setAlbums(albumDTOMapper.mapToDto(model.getAlbums()));
        }

        return result;
    }

    @Override
    public List<ArtistResponseDto> mapToDto(List<ArtistEntity> models) {
        var result = new ArrayList<ArtistResponseDto>();
        for (ArtistEntity model : models) {
            result.add(mapToDto(model));
        }
        return result;
    }

    @Override
    public ArtistEntity mapToEntity(ArtistRequestDto artistModel) {
        var result = new ArtistEntity();
        result.setName(artistModel.getName());
        result.setBiography(artistModel.getBiography());
        return result;
    }
}

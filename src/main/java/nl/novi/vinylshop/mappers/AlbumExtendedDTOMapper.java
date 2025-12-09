package nl.novi.vinylshop.mappers;

import nl.novi.vinylshop.dtos.album.AlbumExtendedResponseDto;
import nl.novi.vinylshop.entities.AlbumEntity;
import org.springframework.stereotype.Component;


@Component
public class AlbumExtendedDTOMapper extends AlbumDTOMapper {

    private final GenreDTOMapper genreDTOMapper;
    private final PublisherDTOMapper publisherDTOMapper;
    private final StockDTOMapper stockDTOMapper;

    public AlbumExtendedDTOMapper(GenreDTOMapper genreDTOMapper, PublisherDTOMapper publisherDTOMapper, StockDTOMapper stockDTOMapper) {
        super(genreDTOMapper, publisherDTOMapper);
        this.genreDTOMapper = genreDTOMapper;
        this.publisherDTOMapper = publisherDTOMapper;
        this.stockDTOMapper = stockDTOMapper;
    }

    @Override
    public AlbumExtendedResponseDto mapToDto(AlbumEntity model) {
        var result = new AlbumExtendedResponseDto();
        result.setId(model.getId());
        result.setTitle(model.getTitle());
        result.setReleaseYear(model.getReleaseYear());

        if (model.getGenre() != null) {
            result.setGenre(genreDTOMapper.mapToDto(model.getGenre()));
        }

        if (model.getPublisher() != null) {
            result.setPublisher(publisherDTOMapper.mapToDto(model.getPublisher()));
        }

        if (!model.getStock().isEmpty() && model.getStock() != null) {
            result.setStock(stockDTOMapper.mapToDto(model.getStock()));
        }

        return result;
    }

}

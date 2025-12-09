package nl.novi.vinylshop.mappers;

import nl.novi.vinylshop.dtos.stock.StockRequestDto;
import nl.novi.vinylshop.dtos.stock.StockResponseDto;
import nl.novi.vinylshop.entities.StockEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StockDTOMapper implements DTOMapper<StockResponseDto, StockRequestDto, StockEntity> {

    private final AlbumDTOMapper albumDTOMapper;

    public StockDTOMapper(AlbumDTOMapper albumDTOMapper) {
        this.albumDTOMapper = albumDTOMapper;
    }

    @Override
    public StockResponseDto mapToDto(StockEntity model) {

        var result = new StockResponseDto();
        result.setId(model.getId());
        result.setCondition(model.getCondition());
        result.setPrice(model.getPrice());

        if (model.getAlbum() != null) {
            result.setAlbum(albumDTOMapper.mapToDto(model.getAlbum()));
        }

        return result;
    }


    @Override
    public List<StockResponseDto> mapToDto(List<StockEntity> models) {
        var result = new ArrayList<StockResponseDto>();
        for (StockEntity model : models) {
            result.add(mapToDto(model));
        }
        return result;
    }


    @Override
    public StockEntity mapToEntity(StockRequestDto model) {
        var result = new StockEntity();
        result.setPrice(model.getPrice());
        result.setCondition(model.getCondition());
        return result;
    }
}

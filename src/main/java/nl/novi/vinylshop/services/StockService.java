package nl.novi.vinylshop.services;

import jakarta.persistence.EntityNotFoundException;
import nl.novi.vinylshop.dtos.stock.StockRequestDto;
import nl.novi.vinylshop.dtos.stock.StockResponseDto;
import nl.novi.vinylshop.entities.AlbumEntity;
import nl.novi.vinylshop.entities.StockEntity;
import nl.novi.vinylshop.mappers.StockDTOMapper;
import nl.novi.vinylshop.repositories.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final StockDTOMapper stockDTOMapper;
    private final AlbumService albumService;

    public StockService(StockRepository stockRepository, StockDTOMapper stockDTOMapper, AlbumService albumService) {
        this.stockRepository = stockRepository;
        this.stockDTOMapper = stockDTOMapper;
        this.albumService = albumService;
    }

    public List<StockResponseDto> findAllStock() {
        return stockDTOMapper.mapToDto(stockRepository.findAll());
    }

    public StockResponseDto findStockById(Long id) throws EntityNotFoundException {
        StockEntity stockEntity = getStockEntity(id);
        return stockDTOMapper.mapToDto(stockEntity);
    }

    public StockResponseDto createStock(Long albumId, StockRequestDto requestDto) {
        StockEntity stockEntity = stockDTOMapper.mapToEntity(requestDto);
        stockEntity.setAlbum(albumService.getAlbumEntity(albumId));
        stockEntity = stockRepository.save(stockEntity);
        return stockDTOMapper.mapToDto(stockEntity);
    }

    public StockResponseDto updateStock(Long albumId, Long id, StockRequestDto requestDto) throws EntityNotFoundException {
        StockEntity existingStockEntity = getStockEntity(id);

        existingStockEntity.setAlbum(albumService.getAlbumEntity(albumId));
        existingStockEntity.setPrice(requestDto.getPrice());
        existingStockEntity.setCondition(requestDto.getCondition());

        existingStockEntity = stockRepository.save(existingStockEntity);
        return stockDTOMapper.mapToDto(existingStockEntity);
    }

    public void deleteStock(Long albumId, Long id) {
        StockEntity existingStockEntity = getStockEntity(id);
        AlbumEntity albumEntity = albumService.getAlbumEntity(albumId);

        albumEntity.getStock().remove(existingStockEntity);

        stockRepository.deleteById(id);
    }

    public List<StockResponseDto> findStock(Long albumId) {
        List<StockEntity> stocks = stockRepository.findByAlbumId(albumId);
        return stockDTOMapper.mapToDto(stocks);
    }

    private StockEntity getStockEntity(Long id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Stock " + id +" not found"));
    }

}

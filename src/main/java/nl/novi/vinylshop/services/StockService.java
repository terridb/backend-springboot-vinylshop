package nl.novi.vinylshop.services;

import jakarta.persistence.EntityNotFoundException;
import nl.novi.vinylshop.dtos.stock.StockRequestDto;
import nl.novi.vinylshop.dtos.stock.StockResponseDto;
import nl.novi.vinylshop.entities.StockEntity;
import nl.novi.vinylshop.mappers.StockDTOMapper;
import nl.novi.vinylshop.repositories.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final StockDTOMapper stockDTOMapper;

    public StockService(StockRepository stockRepository, StockDTOMapper stockDTOMapper) {
        this.stockRepository = stockRepository;
        this.stockDTOMapper = stockDTOMapper;
    }

    public List<StockResponseDto> findAllStock() {
        return stockDTOMapper.mapToDto(stockRepository.findAll());
    }

    public StockResponseDto findStockById(Long id) throws EntityNotFoundException {
        StockEntity stockEntity = getStockEntity(id);
        return stockDTOMapper.mapToDto(stockEntity);
    }

    public StockResponseDto createStock(StockRequestDto requestDto) {
        StockEntity stockEntity = stockDTOMapper.mapToEntity(requestDto);
        stockEntity = stockRepository.save(stockEntity);
        return stockDTOMapper.mapToDto(stockEntity);
    }

    public StockResponseDto updateStock(Long id, StockRequestDto requestDto) throws EntityNotFoundException {
        StockEntity existingStockEntity = getStockEntity(id);

        existingStockEntity.setPrice(requestDto.getPrice());
        existingStockEntity.setCondition(requestDto.getCondition());

        existingStockEntity = stockRepository.save(existingStockEntity);
        return stockDTOMapper.mapToDto(existingStockEntity);
    }

    public void deleteStock(Long id) {
        stockRepository.deleteById(id);
    }

    private StockEntity getStockEntity(Long id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Stock " + id +" not found"));
    }

}

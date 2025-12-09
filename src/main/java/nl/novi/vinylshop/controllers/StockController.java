package nl.novi.vinylshop.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import nl.novi.vinylshop.dtos.stock.StockRequestDto;
import nl.novi.vinylshop.dtos.stock.StockResponseDto;
import nl.novi.vinylshop.helpers.UrlHelper;
import nl.novi.vinylshop.services.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albums/{albumId}/stock")
public class StockController {

    private final StockService stockService;
    private final UrlHelper urlHelper;

    public StockController(StockService stockService, UrlHelper urlHelper) {
        this.stockService = stockService;
        this.urlHelper = urlHelper;
    }

//    @GetMapping
//    public ResponseEntity<List<StockResponseDto>> getAllStock(@PathVariable Long albumId) {
//        List<StockResponseDto> stock = stockService.findAllStock(albumId);
//        return new ResponseEntity<>(stock, HttpStatus.OK);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<StockResponseDto> getStockById(@PathVariable Long albumId, @PathVariable Long id) throws EntityNotFoundException {
//        StockResponseDto stock = stockService.findStockById(albumId, id);
//        return new ResponseEntity<>(stock, HttpStatus.OK);
//    }

    @PostMapping
    public ResponseEntity<StockResponseDto> createStock(@PathVariable Long albumId, @RequestBody @Valid StockRequestDto model) {
        StockResponseDto newStock = stockService.createStock(albumId, model);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(newStock.getId())).body(newStock);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockResponseDto> updateStock(@PathVariable Long albumId, @PathVariable Long id, @RequestBody @Valid StockRequestDto model) throws EntityNotFoundException {
        StockResponseDto updatedStock = stockService.updateStock(albumId, id, model);
        return new ResponseEntity<>(updatedStock, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long albumId, @PathVariable Long id) {
        stockService.deleteStock(albumId, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

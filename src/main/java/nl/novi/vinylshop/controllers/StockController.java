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
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;
    private final UrlHelper urlHelper;

    public StockController(StockService stockService, UrlHelper urlHelper) {
        this.stockService = stockService;
        this.urlHelper = urlHelper;
    }

    @GetMapping
    public ResponseEntity<List<StockResponseDto>> getAllStock() {
        List<StockResponseDto> stock = stockService.findAllStock();
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockResponseDto> getStockById(@PathVariable Long id) throws EntityNotFoundException {
        StockResponseDto stock = stockService.findStockById(id);
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StockResponseDto> createStock(@RequestBody @Valid StockRequestDto model) {
        StockResponseDto newStock = stockService.createStock(model);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(newStock.getId())).body(newStock);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockResponseDto> updateStock(@PathVariable Long id, @RequestBody @Valid StockRequestDto model) throws EntityNotFoundException {
        StockResponseDto updatedStock = stockService.updateStock(id, model);
        return new ResponseEntity<>(updatedStock, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

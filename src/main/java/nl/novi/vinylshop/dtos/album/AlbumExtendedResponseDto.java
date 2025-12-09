package nl.novi.vinylshop.dtos.album;

import nl.novi.vinylshop.dtos.stock.StockResponseDto;

import java.util.List;

public class AlbumExtendedResponseDto extends AlbumResponseDto {
    private List<StockResponseDto> stock;

    public List<StockResponseDto> getStock() {
        return stock;
    }

    public void setStock(List<StockResponseDto> stock) {
        this.stock = stock;
    }
}

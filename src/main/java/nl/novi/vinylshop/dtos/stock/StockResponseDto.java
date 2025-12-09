package nl.novi.vinylshop.dtos.stock;

import nl.novi.vinylshop.dtos.album.AlbumResponseDto;

public class StockResponseDto {
    private Long id;
    private String condition;
    private double price;
    private AlbumResponseDto album;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public AlbumResponseDto getAlbum() {
        return album;
    }

    public void setAlbum(AlbumResponseDto album) {
        this.album = album;
    }
}

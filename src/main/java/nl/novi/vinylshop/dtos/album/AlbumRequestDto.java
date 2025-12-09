package nl.novi.vinylshop.dtos.album;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AlbumRequestDto {
    @NotBlank(message = "Titel mag niet leeg zijn")
    @Size(min = 3, max = 100, message = "Titel moet tussen de 3 en 100 karakters lang zijn")
    private String title;

    @Min(1877)
    @Max(2100)
    private int releaseYear;

    private Long genreId;
    private Long publisherId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }
}

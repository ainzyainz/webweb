package utils.converter;
import DTO.CatalogDTO;
import entities.Catalog;
import java.util.HashSet;

public class CatalogConverter {
    public Catalog apply(CatalogDTO catalogDTO) {
        return Catalog.builder()
                .id(catalogDTO.getId())
                .name(catalogDTO.getName())
                .games(new HashSet<>())
                .build();
    }

    public CatalogDTO applyDTO(Catalog catalog) {
        return CatalogDTO.builder()
                .id(catalog.getId())
                .name(catalog.getName())
                .gameDTOS(new HashSet<>())
                .build();
    }
}

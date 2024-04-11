package utils.converter;

import DTO.GameRequirementsDTO;
import entities.GameRequirements;

public class GameReqsConverter {
    public GameRequirementsDTO applyDTO(GameRequirements gameRequirements){
        return GameRequirementsDTO.builder()
                .id(gameRequirements.getId())
                .directX(gameRequirements.getDirectX())
                .graphics(gameRequirements.getGraphics())
                .memory(gameRequirements.getMemory())
                .storage(gameRequirements.getStorage())
                .processor(gameRequirements.getProcessor())
                .OS(gameRequirements.getOS())
                .build();

    }
    public GameRequirements apply(GameRequirementsDTO gameRequirementsDTO){
        return GameRequirements.builder()
                .id(gameRequirementsDTO.getId())
                .directX(gameRequirementsDTO.getDirectX())
                .graphics(gameRequirementsDTO.getGraphics())
                .memory(gameRequirementsDTO.getMemory())
                .storage(gameRequirementsDTO.getStorage())
                .processor(gameRequirementsDTO.getProcessor())
                .OS(gameRequirementsDTO.getOS())
                .build();
    }
}

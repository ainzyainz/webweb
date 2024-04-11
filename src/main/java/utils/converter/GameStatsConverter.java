package utils.converter;

import DTO.GameStatisticsDTO;
import entities.GameStatistics;

public class GameStatsConverter {
    public GameStatistics apply(GameStatisticsDTO gameStatisticsDTO){
        return GameStatistics.builder()
                .id(gameStatisticsDTO.getId())
                .purchaseCounter(gameStatisticsDTO.getPurchaseCounter())
                .price(gameStatisticsDTO.getPrice())
                .build();
    }
    public GameStatisticsDTO applyDTO(GameStatistics gameStatistics){
        return GameStatisticsDTO.builder()
                .id(gameStatistics.getId())
                .price(gameStatistics.getPrice())
                .purchaseCounter(gameStatistics.getPurchaseCounter())
                .build();
    }
}

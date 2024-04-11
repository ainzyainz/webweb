package utils.converter;

import DTO.*;
import entities.*;

import java.util.Set;
import java.util.stream.Collectors;

public class GameConverter {

    private GameReqsConverter gameReqsConverter = new GameReqsConverter();
    private GameStatsConverter gameStatsConverter = new GameStatsConverter();
    private CatalogConverter catalogConverter = new CatalogConverter();

    private ReviewConverter reviewConverter = new ReviewConverter();

    public Game apply(GameDTO gameDTO){
        Game game = Game.builder()
                .id(gameDTO.getId())
                .name(gameDTO.getName())
                .build();
        GameRequirements gameRequirements = gameReqsConverter.apply(gameDTO.getGameRequirementsDTO());
        gameRequirements.setGame(game);
        game.setReqs(gameRequirements);

        GameStatistics gameStatistics = gameStatsConverter.apply(gameDTO.getGameStatisticsDTO());
        gameStatistics.setGame(game);
        game.setStats(gameStatistics);

        Set<Catalog> catalogSet = gameDTO.getCatalogDTOSet()
                .stream()
                .map(catalogConverter::apply)
                .collect(Collectors.toSet());

        catalogSet.forEach(q -> q.getGames().add(game));
        game.setCatalogs(catalogSet);

        return game;
    }
    public GameDTO applyDTO(Game game){
        GameDTO gameDTO = GameDTO.builder()
                .id(game.getId())
                .name(game.getName())
                .build();
        GameRequirementsDTO gameRequirementsDTO = gameReqsConverter.applyDTO(game.getReqs());
        gameRequirementsDTO.setGameDTO(gameDTO);
        gameDTO.setGameRequirementsDTO(gameRequirementsDTO);

        GameStatisticsDTO gameStatisticsDTO = gameStatsConverter.applyDTO(game.getStats());
        gameStatisticsDTO.setGameDTO(gameDTO);
        gameDTO.setGameStatisticsDTO(gameStatisticsDTO);



        Set<CatalogDTO> catalogSet = game.getCatalogs()
                .stream()
                .map(catalogConverter::applyDTO)
                .collect(Collectors.toSet());

        Set<ReviewDTO> reviewSet = game.getReviews()
                .stream()
                .map(reviewConverter::applyDTO)
                .collect(Collectors.toSet());

        reviewSet.forEach(q -> q.setGameDTO(gameDTO));
        catalogSet.forEach(q -> q.getGameDTOS().add(gameDTO));

        gameDTO.setReviewDTOSet(reviewSet);
        gameDTO.setCatalogDTOSet(catalogSet);

        return gameDTO;
    }
}

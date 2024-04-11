package utils.converter;

import DTO.BinDTO;
import DTO.GameDTO;
import entities.Bin;
import entities.Game;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class BinConverter {
    GameConverter gameConverter = new GameConverter();

    public Bin apply(BinDTO binDTO){
        Set<Game> gameSet = binDTO.getGameDTOSet()
                .stream()
                .map(gameConverter::apply)
                .collect(Collectors.toSet());

        return Bin.builder()
                .id(binDTO.getId())
                .games(gameSet)
                .build();
    }
    public BinDTO applyDTO(Bin bin){
        Set<GameDTO> gameDTOSet = bin.getGames()
                .stream()
                .map(gameConverter::applyDTO)
                .collect(Collectors.toSet());

        return BinDTO.builder()
                .id(bin.getId())
                .gameDTOSet(gameDTOSet)
                .build();
    }
}

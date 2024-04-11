package utils.converter;

import DTO.GameDTO;
import DTO.LibraryDTO;
import entities.Game;
import entities.Library;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class LibraryConverter {
    private GameConverter gameConverter = new GameConverter();

    public Library apply(LibraryDTO libraryDTO){
        Set<Game> gameSet = libraryDTO.getGameDTOSet()
                .stream()
                .map(gameConverter::apply)
                .collect(Collectors.toSet());

        return Library.builder()
                .id(libraryDTO.getId())
                .games(gameSet)
                .build();
    }
    public LibraryDTO applyDTO(Library library){
        Set<GameDTO> gameSet = library.getGames()
                .stream()
                .map(gameConverter::applyDTO)
                .collect(Collectors.toSet());

        return LibraryDTO.builder()
                .id(library.getId())
                .gameDTOSet(gameSet)
                .build();
    }
}

package utils.converter;

import DTO.UserDescriptionDTO;
import entities.UserDescription;

public class UserDescConverter {
public UserDescriptionDTO applyDTO(UserDescription userDescription){
    return UserDescriptionDTO.builder()
            .id(userDescription.getId())
            .name(userDescription.getName())
            .surname(userDescription.getSurname())
            .address(userDescription.getAddress())
            .age(userDescription.getAge())
            .build();
}
public UserDescription apply(UserDescriptionDTO userDescriptionDTO){
    return UserDescription.builder()
            .id(userDescriptionDTO.getId())
            .name(userDescriptionDTO.getName())
            .surname(userDescriptionDTO.getSurname())
            .address(userDescriptionDTO.getAddress())
            .age(userDescriptionDTO.getAge())
            .build();
}

}

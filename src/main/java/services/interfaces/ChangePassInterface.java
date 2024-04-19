package services.interfaces;

import DTO.UserDTO;

public interface ChangePassInterface {
int sendEmail(String email);
UserDTO isValid(String email);
void updatePassword(long id, String newPassword);
boolean codeVerified(int code, int inputCode);
}

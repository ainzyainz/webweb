package services;

import DAO.classes.UserDAOImpl;
import DTO.UserDTO;
import entities.User;
import utils.converter.UserConverter;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChangePassHandler {
    private UserDAOImpl userDAO = new UserDAOImpl();
    private Logger LOGGER = Logger.getLogger(ChangePassHandler.class.getName());
    private final UserConverter userConverter = new UserConverter();

    public int sendEmail(String email) {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException e) {
            LOGGER.log(Level.INFO, "Email wasn't written correctly");
            return -1;
        }

        int code = (int) (Math.random() * 9000 + 1000);

        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("g30656561@gmail.com", "nslenecxkbpenout");
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(email));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("Hello");
            message.setText("your code is: " + code);

            Transport.send(message);
            LOGGER.log(Level.INFO,"Email sent successfully");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return code;
    }

    public UserDTO isValid(String email) {
        User changeUser = userDAO.getUserByEmail(email);
        if (changeUser != null) {
            return userConverter.applyDTO(changeUser);
        }
        return null;
    }
    public boolean codeVerified(int code, int inputCode){
        if (code == inputCode){
            return true;
        }
        return false;
    }
    public void updatePassword(long id, String newPassword) {
        User user = userDAO.read(id);
        user.setPassword(newPassword);
        userDAO.update(id, user);
    }
}

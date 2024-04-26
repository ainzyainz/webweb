package services.classes;

import DAO.classes.UserDAOImpl;
import DTO.UserDTO;
import entities.User;
import services.interfaces.ChangePassInterface;
import utils.converter.UserConverter;
import utils.functionalinterface.MyInterfaceToDAO;
import utils.functionalinterface.UtilsInterface;
import utils.hibernate.HibernateUtils;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.constant.ConstantsContainer.*;

public class ChangePassHandler implements ChangePassInterface {
    private UserDAOImpl userDAO = new UserDAOImpl(HibernateUtils.getEntityManager());
    private Logger LOGGER = Logger.getLogger(ChangePassHandler.class.getName());
    private final UserConverter userConverter = new UserConverter();

    @Override
    public int sendEmail(String email) {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException e) {
            LOGGER.log(Level.INFO, NO_SUCH_EMAIL+email);
            return 0;
        }

        int code = (int) (Math.random() * 9000 + 1000);

        Properties props = new Properties();

        props.put(HOST_PROP, HOST_VAL);
        props.put(FACTORYPORT_PROP, FACTORYPORT_VAL);
        props.put(FACTORYCLASS_PROP, FACTORYCLASS_VAL);
        props.put(AUTH_PROP, AUTH_VAL);
        props.put(AUTHPORT_PROP, AUTHPORT_VAL);

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, APP_PASSWORD);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(email));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("Hello");
            message.setText("your code is: " + code);
            Transport.send(message);
            LOGGER.log(Level.INFO,EMAIL_SENDING_SUCCESS+email);
        } catch (MessagingException e) {
            LOGGER.log(Level.INFO,EMAIl_SENDING_FAILED+email);
            return 0;
        }
        return code;
    }
    @Override
    public UserDTO isValid(String email) {
        MyInterfaceToDAO<User> betweenBeginAndCommited = () -> userDAO.getUserByEmail(email);
        User changeUser = UtilsInterface.superMethodInterface(betweenBeginAndCommited,HibernateUtils.getEntityManager());
        if (changeUser != null) {
            return userConverter.applyDTO(changeUser);
        }
        return null;
    }
    @Override
    public boolean codeVerified(int code, int inputCode){
        return code == inputCode;
    }
    @Override
    public void updatePassword(long id, String newPassword) {
        MyInterfaceToDAO<Object> betweenBeginAndCommited = () -> {
            User user = userDAO.read(id);
            user.setPassword(newPassword);
            userDAO.update(id, user);
            return null;
        };
        UtilsInterface.superMethodInterface(betweenBeginAndCommited,HibernateUtils.getEntityManager());
    }
}

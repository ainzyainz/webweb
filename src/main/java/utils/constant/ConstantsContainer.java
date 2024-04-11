package utils.constant;

public class ConstantsContainer {

    public static final String GET_ALL_STUDENT_QUERY = "select s from Student s";
    public static final String GET_ALL_USER_QUERY ="select u from User u";
    public static final String GET_ALL_GAMES_QUERY ="select g from Game g";

    public static final String GET_ALL_CATALOG_QUERY ="select u from Catalog u";

    public static final String GET_SEARCH = "from UserDescription where name = :value or  surname = :value or address = :value or age = :value";
    public static final String GET_SEARCH_USER = "from User where email = :value";
    public static final String GET_SEARCH_GAME = "from Game where name = :value";
    public static final String GET_SEARCH_GAME_REQS = "from GameRequirements where OS = :value or memory = :value or graphics = :value or directX = :value or storage = :value or processor = :value";
    public static final String GET_SEARCH_CATALOG = "from Catalog where name = :value";
    public static final String FROM_GAME = "from Game";

    public static final String FIND_BY_NAME = "from Catalog where name = :value";
    public static final String CREATE_FAILED_MSG = "Create failed. Nothing found";
    public static final String CREATE_SUCCESS_MSG = "Entity created";

    public static final String READ_FAILED_MSG = "Read failed. Nothing found";
    public static final String READ_SUCCESS_MSG = "Read the entity";

    public static final String UPDATE_FAILED_MSG = "Update failed. Nothing found";
    public static final String UPDATE_SUCCESS_MSG = "Updated the entity";

    public static final String DELETE_FAILED_MSG = "Delete failed. Nothing found";
    public static final String DELETE_SUCCESS_MSG = "Deleted the entity";

    public static final String START_GET_ALL_ENTITY = "Getting all entities from db";
    public static final String GET_ALL_ENTITY_SUCCESS = "Got all entities successfully";

    public static final String VALUE = "value";
    public static final String LIST_IS_EMPTY = "current list is empty";

    public static final Integer ROW_IN_PAGE = 10;
    public static final String USER_IS_NULL = "userDTOS is null";
    public static final String PAGE = "page";
    public static final Double PAGE_COEFFICIENT = 1.0;
    public static final String INCORRECT_TYPE = "incorrect type";

    public static final String DO_POST_START = "doPost start";
    public static final String DO_GET_START = "doGet start";
    public static final String PAGE_IS_NULL = "page is null";
    public static final String CREATE_SUCCESS = "create success";
    public static final String CRETE_FAILED = "create failed";
    public static final String DELETE_SUCCESS = "delete success";
    public static final String DELETE_FAILED = "delete failed";
    public static final String BOUGHT_GAME_MSG = "user bought";
    public static final String GET_USER_BY_EMAIL_QUERY = "select * from user where email = '";
    public static final String GET_GAME_BY_NAME_QUERY = "select * from game where name = '";
    public static final String GET_USER_BY_PASSWORD_QUERY = "select * from user where password = '";
    public static final String END_QUERY = "'";
    public static final String END_QUERY2 = "';";
    public static final String AND_PASSWORD = "' and password ='";

    public static final String GET_STUDENT_BY_USER_ID_QUERY = "select * from student where user_id = '";
    public static final String GET_STUDENT_BY_USER_ID_NOT_FOUND = " 404: Student findByUserId not found";

    private ConstantsContainer() {}
}

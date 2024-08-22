package sukhrob.developer.rest_api.utilities;

public interface AppConstant {

    String BASE_PATH = "/api/v1/";

    String AUTH = BASE_PATH + "auth/";
    String ATTACHMENT = BASE_PATH + "attachment/";
    String CATEGORY = BASE_PATH + "category/";
    String VIEW_ALL = "view/all";
    String VIEW_ALL_BY_ID = "view-all/";
    String VIEW_ONE = "view/";
    String EDIT = "update/";
    String DELETE = "delete/";
    String ADD = "add/";

    String MEASUREMENT = BASE_PATH + "measurement/";

    String DEFAULT_PAGE_NUMBER = "0";
    String DEFAULT_PAGE_SIZE = "20";

    String USER = "USER";

    String ADMIN = "ADMIN";

    String ROLE_CONTROLLER = BASE_PATH + "roles/";
    String ATTACH_ROLE = ROLE_CONTROLLER + "attach-role/";
    String BOOK_CONTROLLER = BASE_PATH + "books/";
    String COMMENT_CONTROLLER = BASE_PATH + "comments/";
}

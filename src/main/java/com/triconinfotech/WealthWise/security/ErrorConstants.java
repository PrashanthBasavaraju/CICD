package com.triconinfotech.WealthWise.security;

public class ErrorConstants {
    public static final String INVALID_USER_ID = "{\"error\": \"User Id\"}";
    public static final String INVALID_ID = "{\"error\": \"Invalid Id\"}";
    public static final String DATA_DELETED = "{\"message\": \"Data deleted successfully\"}";
    public static final String DATA_DELETED_ERROR = "{\"error\": \"Error while deleting, please check your access\"}";
    public static final String INVALID_DATA = "{\"error\": \"Invalid Data\"}";
    public static final String ERROR_FETCHING = "{\"error\": \"Error occurred in getting the data\"}";
    public static final String ERROR_SAVING = "{\"error\": \"Error occurred in saving the data\"}";
    public static final String NOT_FOUND = "{\"message\": \"Data not found\"}";
    public static final String INTERNAL_SERVER_ERROR = "{\"error\": \"Internal Server Error occurred\"}";
    public static final String UNAUTHORIZED_ACCESS = "Unauthorized Access";
    public static final String GOALS_NOT_ENABLED ="{\"error\": \"Goals are not enabled in your organization\"}";
    public static final String KRA_NOT_ENABLED ="{\"error\": \"KRA are not enabled in your organization\"}";
    public static final String GOAL_VS_KRA_NOT_ENABLED ="{\"error\": \"Goal Vs KRA is not enabled in your organization\"}";
    public static final String ERROR_PERFORMANCE_REVIEW = "Performance review can be enabled only with KRA or Goals";
    public static final String ERROR_GOAL_VS_KRA = "isGoalEnabled and isKraEnabled must be true to enable isGoalVsKRAEnabled";
    public static final String ROLE_NOT_ADMIN ="{\"error\": \"This operation can only be performed by Admins\"}";
    public static final String ROLE_NOT_MANAGER ="{\"error\": \"This operation can only be performed by Managers\"}";
    public static final String NO_PERMISSIONS ="{\"error\": \"This role does not have have any permissions\"}";
    public static final String MANAGER = "Manager";
    public static final String ADMIN = "Administrator";
    public static final String DEV_EMAIL = "dev.team@email.com";
    public static final String DEV_NAME = "Dev-Team";
    public static final String DEV_CONTACT = "Contact_URL";
    
    private ErrorConstants() {
        // Private constructor to hide the implicit public one
        throw new AssertionError("Utility class");
    }
}

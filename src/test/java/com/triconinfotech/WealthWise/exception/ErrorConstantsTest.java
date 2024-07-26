package com.triconinfotech.WealthWise.exception;

import org.junit.jupiter.api.Test;

import com.triconinfotech.WealthWise.security.ErrorConstants;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorConstantsTest {

	@Test
	public void testErrorConstantsValues() {
		assertEquals("{\"error\": \"User Id\"}", ErrorConstants.INVALID_USER_ID);
		assertEquals("{\"error\": \"Invalid Id\"}", ErrorConstants.INVALID_ID);
		assertEquals("{\"message\": \"Data deleted successfully\"}", ErrorConstants.DATA_DELETED);
		assertEquals("{\"error\": \"Error while deleting, please check your access\"}",
				ErrorConstants.DATA_DELETED_ERROR);
		assertEquals("{\"error\": \"Invalid Data\"}", ErrorConstants.INVALID_DATA);
		assertEquals("{\"error\": \"Error occurred in getting the data\"}", ErrorConstants.ERROR_FETCHING);
		assertEquals("{\"error\": \"Error occurred in saving the data\"}", ErrorConstants.ERROR_SAVING);
		assertEquals("{\"message\": \"Data not found\"}", ErrorConstants.NOT_FOUND);
		assertEquals("{\"error\": \"Internal Server Error occurred\"}", ErrorConstants.INTERNAL_SERVER_ERROR);
		assertEquals("Unauthorized Access", ErrorConstants.UNAUTHORIZED_ACCESS);
		assertEquals("{\"error\": \"Goals are not enabled in your organization\"}", ErrorConstants.GOALS_NOT_ENABLED);
		assertEquals("{\"error\": \"KRA are not enabled in your organization\"}", ErrorConstants.KRA_NOT_ENABLED);
		assertEquals("{\"error\": \"Goal Vs KRA is not enabled in your organization\"}",
				ErrorConstants.GOAL_VS_KRA_NOT_ENABLED);
		assertEquals("Performance review can be enabled only with KRA or Goals",
				ErrorConstants.ERROR_PERFORMANCE_REVIEW);
		assertEquals("isGoalEnabled and isKraEnabled must be true to enable isGoalVsKRAEnabled",
				ErrorConstants.ERROR_GOAL_VS_KRA);
		assertEquals("{\"error\": \"This operation can only be performed by Admins\"}", ErrorConstants.ROLE_NOT_ADMIN);
		assertEquals("{\"error\": \"This operation can only be performed by Managers\"}",
				ErrorConstants.ROLE_NOT_MANAGER);
		assertEquals("{\"error\": \"This role does not have have any permissions\"}", ErrorConstants.NO_PERMISSIONS);
		assertEquals("Manager", ErrorConstants.MANAGER);
		assertEquals("Administrator", ErrorConstants.ADMIN);
		assertEquals("dev.team@email.com", ErrorConstants.DEV_EMAIL);
		assertEquals("Dev-Team", ErrorConstants.DEV_NAME);
		assertEquals("Contact_URL", ErrorConstants.DEV_CONTACT);
	}
}

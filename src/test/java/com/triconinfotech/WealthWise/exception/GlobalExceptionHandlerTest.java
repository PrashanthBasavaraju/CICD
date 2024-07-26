package com.triconinfotech.WealthWise.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

	@InjectMocks
	private GlobalExceptionHandler exceptionHandler;

	@Test
	public void testHandleCustomException() {
		// Mocking a CustomException
		CustomException customException = new CustomException("Test Error", HttpStatus.INTERNAL_SERVER_ERROR);
		ResponseEntity<Map<String, String>> responseEntity = exceptionHandler.handleCustomException(customException);

		// Asserting the response status and body
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
		assertEquals("Test Error", responseEntity.getBody().get("error"));
	}

}

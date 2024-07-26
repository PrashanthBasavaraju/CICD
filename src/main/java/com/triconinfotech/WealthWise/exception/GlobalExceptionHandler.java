package com.triconinfotech.WealthWise.exception;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * GlobalExceptionHandler is a class responsible for handling exceptions
 * globally across the application. It provides centralized exception handling
 * logic for specific types of exceptions.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	/** The logger for logging exception details. */
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * Handle custom exceptions. This method handles exceptions of type
	 * CustomException.
	 *
	 * @param ex the CustomException instance.
	 * @return ResponseEntity containing error details and appropriate HTTP status.
	 */
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<Map<String, String>> handleCustomException(CustomException ex) {
		Map<String, String> response = new LinkedHashMap<>();
		HttpStatus status = ex.getStatus();

		if (status.is5xxServerError()) {
			String uuid = ex.getUuid();
			String errorMessage = ex.getMessage();
			logErrorWithUuid(uuid, errorMessage);
			response.put("uuid", uuid);
			response.put("error", errorMessage);
		} else {
			response.put("error", ex.getMessage());
		}

		return ResponseEntity.status(status).body(response);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<Map<String, String>> handleMissingParams(MissingServletRequestParameterException ex) {
		Map<String, String> response = new LinkedHashMap<>();
		HttpStatus status = (HttpStatus) ex.getStatusCode();
		response.put("error", ex.getMessage());
		return ResponseEntity.status(status).body(response);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Map<String, String>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
		Map<String, String> response = new LinkedHashMap<>();
		String name = ex.getName();
		String type = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "Unknown";
		Object value = ex.getValue();
		String message = String.format(
				"Request parameter '%s' should be of type '%s', but the value '%s' could not be converted", name, type,
				value);

		response.put("error", message);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

	}
	


	/**
	 * Logs error message with UUID.
	 *
	 * @param uuid         the UUID of the error.
	 * @param errorMessage the error message.
	 */
	private void logErrorWithUuid(String uuid, String errorMessage) {
		logger.error("[uuid-{}] - {}", uuid, errorMessage);
	}
	

}

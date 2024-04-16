package base.comm.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;

public class BaseException extends Exception {

	private static final long serialVersionUID = 1L;

	String message;
	
	String errorCode;
	
	public String getMessage() {
		return message;
	}

	public String setMessage(String defaultMessage) {
		return this.message = defaultMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	
	public BaseException(String defaultMessage) {
		this.message = defaultMessage;
	}

	public BaseException(String message, String code) {
		this.message = message;
		this.errorCode = code;
	}

	public BaseException(String defaultMessage,Throwable throwable) {
		super(defaultMessage, throwable);
	}

	public BaseException(String defaultMessage, String code ,Throwable throwable) {
		super(defaultMessage, throwable);
		this.errorCode = code;
	}
	
	public BaseException(MessageSource messageSource, String messageKey, Object[] messageParameters, String defaultMessage, Locale locale, Throwable throwable) {
		super(throwable);

		if(locale == null) locale = Locale.getDefault();
		this.message = messageSource.getMessage(messageKey, messageParameters, defaultMessage, locale);
	}
}

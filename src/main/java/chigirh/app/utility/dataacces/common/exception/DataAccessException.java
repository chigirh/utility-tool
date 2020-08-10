package chigirh.app.utility.dataacces.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DataAccessException extends RuntimeException {

	private final DataAccessError errorType;

}

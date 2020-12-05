package exception;

import enums.ErrorCode;

/**
 * Custom ParkingLot system exception class
 */
public class ParkingSystemException extends RuntimeException {

    public ErrorCode errorCode;

    public ParkingSystemException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ParkingSystemException(Throwable throwable) {
        super(throwable);
    }

    public ParkingSystemException(String message, ErrorCode errorCode) {
        super(message);
        this.setErrorCode(errorCode);
    }

    public ParkingSystemException(String message, ErrorCode errorCode, Throwable throwable) {
        super(message, throwable);
        this.setErrorCode(errorCode);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}

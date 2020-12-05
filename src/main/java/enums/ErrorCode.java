package enums;

import constant.ErrorMessage;

/**
 * Error code enums for custom exceptions
 */
public enum ErrorCode {

    VEHICLE_ALREADY_EXIST(ErrorMessage.VEHICLE_ALREADY_EXIST),
    INVALID_COMMAND_FORMAT(ErrorMessage.INVALID_COMMAND_FORMAT),
    NO_COMMAND_FOUND(ErrorMessage.NO_COMMAND_FOUND),
    INVALID_SLOT_TO_LEAVE(ErrorMessage.INVALID_SLOT_TO_LEAVE),
    NO_PARKING_FOUND(ErrorMessage.NO_PARKING_FOUND),
    INVALID_PARKING_LOT_SIZE(ErrorMessage.INVALID_PARKING_LOT_SIZE),
    NO_FREE_SLOT_FOUND(ErrorMessage.NO_FREE_SLOT_FOUND),
    PARKING_LOT_ALREADY_EXIST(ErrorMessage.PARKING_LOT_ALREADY_EXIST),
    SOME_ERROR_OCCURRED(ErrorMessage.SOME_ERROR_OCCURRED);

    private final String message;

    /**
     * @param message : The error message
     */
    ErrorCode(String message) {
        this.message = message;
    }

    /**
     * @return String: Returns the error message
     */
    public String getMessage() {
        return message;
    }

}

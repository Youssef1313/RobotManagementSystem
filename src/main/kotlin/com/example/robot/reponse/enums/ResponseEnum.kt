package com.example.robot.reponse.enums

import org.springframework.http.HttpStatus

enum class ResponseEnum(val code: String, val httpStatus: HttpStatus, val description: String) {

    SUCCESS("E0", HttpStatus.OK, "The request was performed successfully"),
    ROBOT_NOT_FOUND("E1", HttpStatus.NOT_FOUND, "Robot Not Found"),

    ROBOT_CANNOT_BE_LOADED("E2", HttpStatus.UNPROCESSABLE_ENTITY,
        "Robot is not idle or needs charging"),

    WEIGHT_LIMIT_EXCEEDED("E3", HttpStatus.UNPROCESSABLE_ENTITY,
        """The total weight of the medications exceeds 
            |the maximum weight that the robot can carry.""".trimMargin()),

    METHOD_ARGUMENT_NOT_VALID("E4", HttpStatus.BAD_REQUEST,""),

    INTERNAL_SERVER_ERROR("E5", HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),

    ROBOT_ALREADY_EXISTS("E6", HttpStatus.ALREADY_REPORTED, "The robot was registered before"),
    MEDICATION_ALREADY_EXISTS("E7", HttpStatus.ALREADY_REPORTED, "The medication was registered before"),
    OPTIMISTIC_LOCKING_EXCEPTION("E8", HttpStatus.CONFLICT, "Operation failed. Please try again"),
    MEDICATION_NAME_NOT_MATCHING_RULES("E9", HttpStatus.BAD_REQUEST, "Medication name can only contain letters, digits, - and _ and cannot be empty")


}

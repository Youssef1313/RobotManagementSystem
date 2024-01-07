package com.example.robot.exception.enums

import org.springframework.http.HttpStatus

enum class ErrorsEnum(val errorCode: String, val httpStatus: HttpStatus, val description: String) {

    ROBOT_NOT_FOUND("E1", HttpStatus.NOT_FOUND, "Robot Not Found"),

    ROBOT_CANNOT_BE_LOADED("E2", HttpStatus.UNPROCESSABLE_ENTITY,
        "Robot is not idle or needs charging"),

    WEIGHT_LIMIT_EXCEEDED("E3", HttpStatus.UNPROCESSABLE_ENTITY,
        """The total weight of the medications exceeds 
            |the maximum weight that the robot can carry.""".trimMargin()),

    METHOD_ARGUMENT_NOT_VALID("E4", HttpStatus.BAD_REQUEST,""),

    INTERNAL_SERVER_ERROR("E5", HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");

}

package com.example.robot.query

import com.example.robot.resource.robot.CheckRobotBatteryLevelResource

class CheckRobotBatteryLevelQuery(
    val serialNumber: String
) {
    constructor(checkRobotBatteryLevelResource: CheckRobotBatteryLevelResource) : this(
        serialNumber = checkRobotBatteryLevelResource.serialNumber!!
    )
}
package com.example.robot.controller.dispatchcontroller

import com.example.robot.controller.DispatchController
import com.example.robot.reponse.enums.ResponseEnum
import com.example.robot.service.CarriedMedicationService
import com.example.robot.service.RobotService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.isEqualTo

@WebMvcTest(controllers = [DispatchController::class])
class CheckRobotBatteryLevelTest(@Autowired val mockMvc : MockMvc) {

    @MockkBean
    lateinit var carriedMedicationService: CarriedMedicationService
    @MockkBean
    lateinit var robotService: RobotService

    @Test
    fun checkRobotBatteryLevelHappy(){
        val serialNumber : String = "ABC123"
        val batteryLevel : Int = 40
        every { robotService.checkRobotBatteryLevel(any()) } returns batteryLevel
        mockMvc.perform(
            MockMvcRequestBuilders.get("/robot/battery?serialNumber=${serialNumber}")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isEqualTo(ResponseEnum.SUCCESS.httpStatus.value()))
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.httpResponse.description").value(ResponseEnum.SUCCESS.description))
            .andExpect(MockMvcResultMatchers.jsonPath("$.httpResponse.code").value(ResponseEnum.SUCCESS.code))
            .andExpect(MockMvcResultMatchers.jsonPath("$.payLoad").value("$batteryLevel%"))
    }
}
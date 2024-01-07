package com.example.robot.service.impl

import com.example.robot.exception.RobotCannotBeLoadedException
import com.example.robot.exception.RobotNotFoundException
import com.example.robot.exception.WeightLimitExceededException
import com.example.robot.model.Robot
import com.example.robot.model.RobotDynamicState
import com.example.robot.model.enums.RobotState
import com.example.robot.repository.CarriedMedicationRepository
import com.example.robot.repository.MedicationRepository
import com.example.robot.repository.RobotDynamicStateRepository
import com.example.robot.repository.RobotRepository
import com.example.robot.service.CarriedMedication
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CarriedMedicationImpl(
        private val robotRepository: RobotRepository,
        private val robotDynamicStateRepository: RobotDynamicStateRepository,
        private val medicationRepository: MedicationRepository,
        private val carriedMedicationRepository: CarriedMedicationRepository
    ) : CarriedMedication
{
    @Transactional
    override fun loadRobotWithMedication(serialNumber: String, medicationNames: List<String>): Unit {
        val robot : Robot = robotRepository.findByIdOrNull(serialNumber) ?: throw RobotNotFoundException()
        canLoadMedications(robot, medicationNames)
        loadRobot(robot, medicationNames)
    }

    private fun canLoadMedications(
        robot: Robot,
        medicationNames: List<String>
    )
    {
        val robotDynamicState : RobotDynamicState = robot.robotDynamicState
        if(robotDynamicState.batteryCapacity <= 25 ||
            RobotState.IDLE != robotDynamicState.robotState)
                throw RobotCannotBeLoadedException()

        val medicationsWeight : Int = medicationRepository.findMedicationsWeight(medicationNames)
        if(medicationsWeight > robot.weightLimit)
            throw  WeightLimitExceededException()
    }

    private fun loadRobot(robot: Robot, medicationNames: List<String>){
        changeRobotState(robot, RobotState.LOADING)
        carriedMedicationRepository.storeLoadedMedication(robot.serialNumber, medicationNames)
        changeRobotState(robot, RobotState.LOADED)
    }

    private fun changeRobotState(robot : Robot, newState : RobotState){
        robot.robotDynamicState.robotState = newState
        robotDynamicStateRepository.save(robot.robotDynamicState)
    }

    override fun getLoadedMedication(serialNumber: String): List<String>? {
        return carriedMedicationRepository.getLoadedMedication(serialNumber)
        //TODO handle the null response
    }

}
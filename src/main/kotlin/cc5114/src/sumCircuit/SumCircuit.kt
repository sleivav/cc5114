package cc5114.src.sumCircuit

import cc5114.src.activationFunction.Step
import cc5114.src.perceptron.NandPerceptron

class SumCircuit(private val x: Double, private val y: Double) {
    private val calculatedSum: Double by lazy {
        val nandPerceptron = NandPerceptron(Step())
        nandPerceptron.calculateOutput(nandPerceptron.calculateOutput(x, nandPerceptron.calculateOutput(x, y)),
                                       nandPerceptron.calculateOutput(nandPerceptron.calculateOutput(x, y), y))
    }
    private val calculatedCarryBit: Double by lazy {
        val nandPerceptron = NandPerceptron(Step())
        nandPerceptron.calculateOutput(nandPerceptron.calculateOutput(x, y), nandPerceptron.calculateOutput(x, y))
    }

    fun getSumResult(): Double {
        return this.calculatedSum
    }

    fun getCarryBit(): Double {
        return this.calculatedCarryBit
    }
}
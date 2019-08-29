package sumCircuit

import activationFunction.step
import perceptron.NandPerceptron

class SumCircuit(private val x: Double, private val y: Double) {
    private val calculatedSum: Double by lazy {
        val nandPerceptron = NandPerceptron(step())
        nandPerceptron.calculateOutput(nandPerceptron.calculateOutput(x, nandPerceptron.calculateOutput(x, y)),
                                       nandPerceptron.calculateOutput(nandPerceptron.calculateOutput(x, y), y))
    }
    private val calculatedCarryBit: Double by lazy {
        val nandPerceptron = NandPerceptron(step())
        nandPerceptron.calculateOutput(nandPerceptron.calculateOutput(x, y), nandPerceptron.calculateOutput(x, y))
    }

    fun getSumResult(): Double {
        return this.calculatedSum
    }

    fun getCarryBit(): Double {
        return this.calculatedCarryBit
    }
}
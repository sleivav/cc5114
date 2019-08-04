package sumCircuit

import perceptron.NandPerceptron

class SumCircuit(private val x: Int, private val y: Int) {
    private val calculatedSum: Int by lazy {
        val nandPerceptron = NandPerceptron()
        nandPerceptron.calculateOutput(nandPerceptron.calculateOutput(x, nandPerceptron.calculateOutput(x, y)),
                                       nandPerceptron.calculateOutput(nandPerceptron.calculateOutput(x, y), y))
    }
    private val calculatedCarryBit: Int by lazy {
        val nandPerceptron = NandPerceptron()
        nandPerceptron.calculateOutput(nandPerceptron.calculateOutput(x, y), nandPerceptron.calculateOutput(x, y))
    }

    fun getSumResult(): Int {
        return this.calculatedSum
    }

    fun getCarryBit(): Int {
        return this.calculatedCarryBit
    }
}
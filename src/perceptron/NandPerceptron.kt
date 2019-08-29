package perceptron

import activationFunction.ActivationFunction

class NandPerceptron(override val function: ActivationFunction) : Perceptron {
    fun calculateOutput(input1: Double, input2: Double): Double {
        return super.calculateOutput(listOf(input1, input2), listOf(-1.0, -1.0),1.5)
    }
}
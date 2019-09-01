package cc5114.src.perceptron

import cc5114.src.activationFunction.ActivationFunction

/**
 * Perceptron que calcula un AND lógico entre dos entradas
 */
class AndPerceptron(override val function: ActivationFunction) : Perceptron {
    fun calculateOutput(input1: Double, input2: Double): Double {
        return super.calculateOutput(listOf(input1, input2), listOf(1.0, 1.0),-1.5)
    }
}
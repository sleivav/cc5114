package cc5114.src.perceptron

import cc5114.src.activationFunction.ActivationFunction

interface Perceptron {
    val function: ActivationFunction

    /**
     * Aplica la función de activación del cc5114.src.perceptron, y devuelve el resultado
     */
    fun calculateOutput(inputList: List<Double>, weightList: List<Double>, bias: Double): Double {
        return this.function.apply(inputList.zip(weightList) { x, w -> x*w }.sum() + bias)
    }
}
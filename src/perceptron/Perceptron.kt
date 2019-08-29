package perceptron

import activationFunction.ActivationFunction

interface Perceptron {
    val function: ActivationFunction

    fun calculateOutput(inputList: List<Double>, weightList: List<Double>, bias: Double): Double {
        return this.function.apply(inputList.zip(weightList) { x, w -> x*w }.sum() + bias)
    }
}
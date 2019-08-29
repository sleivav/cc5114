package activationFunction

interface ActivationFunction {
    fun apply(x: Double): Double
    fun derivative(x: Double): Double
}
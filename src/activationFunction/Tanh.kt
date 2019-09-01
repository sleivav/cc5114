package activationFunction

import kotlin.math.exp
import kotlin.math.pow

class Tanh : ActivationFunction {
    override fun apply(x: Double): Double {
        return (exp(x) - exp(-x)/(exp(x) + exp(-x)))
    }

    override fun derivative(x: Double): Double {
        return 1 - apply(x).pow(2)
    }
}
package activationFunction

import kotlin.math.exp

class sigmoid : ActivationFunction {
    override fun apply(x: Double): Double {
        return 1.0 / (1.0 + exp(x))
    }

    override fun derivative(x: Double): Double {
        return this.apply(x) * (1 - this.apply(x))
    }
}
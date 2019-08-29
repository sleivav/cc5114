package activationFunction

class step: ActivationFunction {
    override fun apply(x: Double): Double {
        return when(x <= 0) {
            true -> 0.0
            false -> 1.0
        }
    }

    override fun derivative(x: Double): Double {
        return 0.0 // this function has no derivative on 0, but since this shouldn't be used, it's kept this way
    }
}
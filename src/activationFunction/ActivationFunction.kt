package activationFunction

/**
 * Función de activación. Se usa para calcular el output de un perceptrón
 *
 */
interface ActivationFunction {
    /**
     * Aplica la función de activación y devuelve el resultado
     * @param x valor a evaluar en la función
     */
    fun apply(x: Double): Double

    /**
     * Aplica la derivada de la función y devuelve el resultado
     * @param x valor a evaluar en la derivada
     */
    fun derivative(x: Double): Double
}
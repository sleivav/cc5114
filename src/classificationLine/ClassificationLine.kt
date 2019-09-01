package classificationLine

/**
 * Clase que representa una ecuación de la recta, para probar y entrenar un perceptrón
 *
 * @property m La pendiente de la recta
 * @property n Intersección con el eje Y
 */
class ClassificationLine(private val m: Double, private val n: Double) {
    /**
     * Función para saber si un punto está sobre o bajo la recta
     *
     * @param x Coordenada x del punto
     * @param y Coordenada y del punto
     */
    fun pointPosition(x: Double, y: Double): Double {
        return when (y > this.m *x + this.n) {
            true -> 1.0
            false -> 0.0
        }
    }
}
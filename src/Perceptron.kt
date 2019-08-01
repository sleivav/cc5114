class Perceptron {
    private fun binaryOperation(x1: Int, x2: Int, weight1: Double, weight2: Double, bias: Double): Int {
        return if (x1 * weight1 + x2 * weight2 + bias > 0) 1 else 0
    }

    fun and(x1: Int, x2: Int): Int {
        return this.binaryOperation(x1, x2, 1.0, 1.0 , -1.5)
    }
}
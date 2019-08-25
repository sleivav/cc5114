package classificationLine

class classificationLine(private val m: Double, private val n: Double) {
    fun pointPosition(x: Double, y: Double): Double {
        return when (y > this.m *x + this.n) {
            true -> 1.0
            false -> 0.0
        }
    }
}
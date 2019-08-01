package perceptron

interface Perceptron {
    fun calculateOutput(inputs: List<Int>, weights: List<Double>, bias: Double): Int {
        return if (inputs.zip(weights) { x, w -> x*w }.sum() + bias > 0) 1 else 0
    }
}
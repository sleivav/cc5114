package perceptron

interface Perceptron {
    fun calculateOutput(inputList: List<Double>, weightList: List<Double>, bias: Double): Double {
        return if (inputList.zip(weightList) { x, w -> x*w }.sum() + bias > 0) 1.0 else 0.0
    }
}
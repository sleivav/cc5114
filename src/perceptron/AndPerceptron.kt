package perceptron

class AndPerceptron: Perceptron {
    fun calculateOutput(inputs: List<Int>): Int {
        return super.calculateOutput(inputs, listOf(1.0, 1.0),-1.5)
    }
}
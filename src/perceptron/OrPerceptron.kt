package perceptron

class OrPerceptron: Perceptron {
    fun calculateOutput(input1: Int, input2: Int): Int {
        return super.calculateOutput(listOf(input1, input2), listOf(1.0, 1.0),0.0)
    }
}
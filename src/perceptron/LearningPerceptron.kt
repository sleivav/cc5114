package perceptron

import kotlin.properties.Delegates
import kotlin.random.Random

class LearningPerceptron(weightsNumber: Int, learningRate: Double) : Perceptron {
    private var weights: MutableList<Double> by Delegates.notNull()
    private var learningRate: Double by Delegates.notNull()
    private val rng: Random = Random(5114)
    private var bias: Double = rng.nextDouble(-2.0, 2.0)

    init {
        this.learningRate = learningRate
        for (i in 1..weightsNumber) {
            this.weights.add(rng.nextDouble(-2.0, 2.0))
        }
    }

    fun learnFromOutput(inputList: List<Int>, realOutput: Int, desiredOutput: Int) {
        val diff: Int = desiredOutput - realOutput
        for ((index, weight) in this.weights.withIndex()) {
            this.weights[index] = weight + this.learningRate * inputList[index] * diff
        }
        this.bias += this.bias + this.learningRate * diff
    }

    fun calculateOutputandLearn(inputList: List<Int>, desiredOutput: Int) {
        val output = this.calculateOutput(inputList, this.weights, this.bias)
        this.learnFromOutput(inputList, output, desiredOutput)
    }
}
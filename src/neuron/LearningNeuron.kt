package neuron

import kotlin.properties.Delegates
import kotlin.random.Random

abstract class LearningNeuron(weightsNumber: Int, learningRate: Double) {
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

    abstract fun calculateOutput(inputList: List<Double>, weightList: List<Double>, bias: Double): Double

    fun learnFromOutput(inputList: List<Double>, realOutput: Double, desiredOutput: Double) {
        val diff: Double = desiredOutput - realOutput
        for ((index, weight) in this.weights.withIndex()) {
            this.weights[index] = weight + this.learningRate * inputList[index] * diff
        }
        this.bias += this.bias + this.learningRate * diff
    }

    fun calculateOutputAndLearn(inputList: List<Double>, desiredOutput: Double) {
        require(inputList.size==this.weights.size) {"El número de inputs pasados no es correcto para esta neurona"}
        val output = this.calculateOutput(inputList, this.weights, this.bias)
        this.learnFromOutput(inputList, output, desiredOutput)
    }
}
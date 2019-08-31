package perceptron

import activationFunction.ActivationFunction
import kotlin.properties.Delegates
import kotlin.random.Random

class LearningPerceptron(weightsNumber: Int, learningRate: Double, activationFunction: ActivationFunction) : Perceptron {
    var weights: MutableList<Double> = mutableListOf()
    private var learningRate: Double by Delegates.notNull()
    private val rng: Random = Random(5114)
    private var bias: Double = rng.nextDouble(-2.0, 2.0)
    override var function: ActivationFunction = activationFunction
    var delta: Double = 0.0
    var output: Double = 0.0

    init {
        this.learningRate = learningRate
        for (i in 1..weightsNumber) {
            this.weights.add(rng.nextDouble(-2.0, 2.0))
        }
    }

    fun learnFromOutput(inputList: List<Double>, realOutput: Double, desiredOutput: Double) {
        val diff: Double = desiredOutput - realOutput
        for ((index, weight) in this.weights.withIndex()) {
            this.weights[index] = weight + this.learningRate * inputList[index] * diff
        }
        this.bias += this.learningRate * diff
    }

    fun calculateOutputAndLearn(inputList: List<Double>, desiredOutput: Double): Double {
        require(inputList.size==this.weights.size) {"El número de inputs pasados no es correcto para esta neurona"}
        val output = this.calculateOutput(inputList)
        this.learnFromOutput(inputList, output, desiredOutput)
        return output
    }

    fun calculateOutput(inputList: List<Double>): Double {
        output = super.calculateOutput(inputList, this.weights, this.bias)
        return output
    }

    fun adjust(error: Double) {
        delta = error * transferDerivative(output)
    }

    private fun transferDerivative(output: Double): Double {
        return function.derivative(output)
    }

    fun updateWeightsAndBias(inputs: List<Double>) {
        for ((index, input) in inputs.withIndex()) {
            weights[index] += learningRate * delta * input
        }
        bias += learningRate * delta
    }
}
package neuralNetwork

import activationFunction.ActivationFunction
import perceptron.LearningPerceptron


class NeuralLayer(private val neurons: Int, private val previousLayer: NeuralLayer?) {
    private var neuronsArray = arrayOfNulls<LearningPerceptron>(neurons)
    private val outputs: MutableList<Double> = MutableList(neurons) {0.0}
    var nextLayer: NeuralLayer? = null
    var learningRate: Double = 0.0

    fun configureLayerFunction(function: ActivationFunction, layerWeights: Int, learningRate: Double) {
        this.learningRate = learningRate
        for(i in 0 until neurons) {
            if(neuronsArray[i] == null) {
                neuronsArray[i] = LearningPerceptron(layerWeights, learningRate, function)
            } else {
                neuronsArray[i]!!.function = function
            }
        }
    }

    fun feed(input: List<Double>): List<Double> {
        for((index, perceptron) in neuronsArray.withIndex()) outputs[index] = perceptron!!.calculateOutput(input)
        return nextLayer?.feed(outputs) ?: outputs
    }

    /*fun train(input: List<Double>, expectedOutput: List<Double>) {
        outputsfeed(input)
        previousLayer?.backPropagate(expectedOutput)
        updateWeighsAndBias()
    }*/

    fun backPropagate(expectedOutput: List<Double>) {
        for((index, perceptron) in neuronsArray.withIndex()) {
            val error: Double = expectedOutput[index] - outputs[index]
            perceptron?.adjust(error)
        }
        previousLayer?.recursiveBackPropagate()
    }

    fun recursiveBackPropagate() {
        for((index, perceptron) in neuronsArray.withIndex()) {
            var error = 0.0
            for(nextLayerPerceptron in nextLayer!!.neuronsArray) {
                error += nextLayerPerceptron!!.weights[index] * nextLayerPerceptron.delta
                perceptron?.adjust(error)
            }
        }
        previousLayer?.recursiveBackPropagate()
    }

    fun updateWeights(input: List<Double>) {

    }
}
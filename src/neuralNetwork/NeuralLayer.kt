package neuralNetwork

import activationFunction.ActivationFunction
import perceptron.LearningPerceptron


class NeuralLayer(private val neurons: Int, previousLayer: NeuralLayer?) {
    private var neuronsArray = arrayOfNulls<LearningPerceptron>(neurons)
    private val outputs: MutableList<Double> = MutableList(neurons) {0.0}
    val nextLayer: NeuralLayer? = null

    fun configureLayerFunction(function: ActivationFunction, layerWeights: Int, learningRate: Double) {
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
        return nextLayer!!.feed(outputs) ?: outputs
    }
}
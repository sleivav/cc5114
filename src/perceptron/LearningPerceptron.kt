package perceptron

import neuron.LearningNeuron
import kotlin.properties.Delegates
import kotlin.random.Random

class LearningPerceptron(weightsNumber: Int, learningRate: Double) : Perceptron, LearningNeuron(weightsNumber, learningRate) {
    override fun calculateOutput(inputList: List<Double>, weightList: List<Double>, bias: Double): Double {
        return super.calculateOutput(inputList, weightList, bias)
    }
}
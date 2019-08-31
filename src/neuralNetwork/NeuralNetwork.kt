package neuralNetwork

import activationFunction.ActivationFunction
import networkExperiment.TrainingSet
import kotlin.math.pow

class NeuralNetwork(layers: Int, neuronsByLayer: List<Int>, val numberOfInputs: Int, numberOfOutputs: Int) {
    val layersList: MutableList<NeuralLayer> = mutableListOf()
    var output: Int = 0
    val precision: MutableList<Double> = mutableListOf()
    val error: MutableList<Double> = mutableListOf()

    init {
        layersList.add(NeuralLayer(neuronsByLayer[0], null))
        for(i in 1 until layers) {
            layersList.add(NeuralLayer(neuronsByLayer[i], layersList[i-1]))
            layersList[i-1].nextLayer = layersList[i]
        }
    }

    fun configureLayerFunction(function: ActivationFunction, layer: Int, layerWeights: Int, learningRate: Double) {
        layersList[layer].configureLayerFunction(function, layerWeights, learningRate)
    }

    fun feed(input: List<Double>): List<Double> {
        return layersList[0].feed(input)
    }

    fun train(input: List<Double>, expectedOutput: List<Double>) {
        val output = predict(input)
        backPropagate(expectedOutput)
        updateWeight(input)
    }

    fun backPropagate(expectedOutput: List<Double>) {
        layersList.last().backPropagate(expectedOutput)
    }

    fun predict(inputs: List<Double>): Int {
        val outputs = feed(inputs)
        return outputs.indexOf(outputs.max())
    }

    fun updateWeight(input: List<Double>) {
        layersList[0].updateWeights(input)
    }

    fun trainEpochs(epochs: Int, trainingSets: List<TrainingSet>) {
        for (i in 0 until epochs) {
            trainingSets.shuffled()
            var correctPredictions = 0
            var totalError = 0.0
            for((input, correctOutputs) in trainingSets) {
                train(input, correctOutputs)
                val expectedOutput = correctOutputs.indexOf(correctOutputs.max())
                if(output == expectedOutput) correctPredictions++
                else totalError += (output - expectedOutput).toDouble().pow(2)
            }
            precision.add(correctPredictions.toDouble() / trainingSets.size * 100)
            error.add(totalError / trainingSets.size)
        }
    }
}


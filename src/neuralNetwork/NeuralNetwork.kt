package neuralNetwork

import activationFunction.ActivationFunction

class NeuralNetwork(layers: Int, neuronsByLayer: List<Int>, numberOfInputs: Int, numberOfOutputs: Int) {
    val layersList: MutableList<NeuralLayer> = mutableListOf()

    init {
        layersList.add(NeuralLayer(neuronsByLayer[0], null))
        for(i in 1 until layers) {
            layersList.add(NeuralLayer(neuronsByLayer[i], layersList[i-1]))
        }
    }

    fun configureLayerFunction(function: ActivationFunction, layer: Int, layerWeights: Int, learningRate: Double) {
        layersList[layer].configureLayerFunction(function, layerWeights, learningRate)
    }

    fun feed(input: List<Double>): List<Double> {
        return layersList[0].feed(input)
    }

    fun train(input: List<Double>, expectedOutput: List<Double>) {

    }

    private fun predict(input: List<Double>): Int {
        val outputs = feed(input)
        return 0
    }
}
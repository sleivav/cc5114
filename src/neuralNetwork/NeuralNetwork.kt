package neuralNetwork

import activationFunction.ActivationFunction

class NeuralNetwork(layers: Int, neuronsByLayer: List<Int>, val numberOfInputs: Int, numberOfOutputs: Int) {
    val layersList: MutableList<NeuralLayer> = mutableListOf()

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

    fun normalize(x: Double, min: Double, max: Double): Double {
        return (x-min) / (max-min)
    }

    fun predict(inputs: List<Double>): Int {
        val outputs = feed(inputs)
        return outputs.indexOf(outputs.max())
    }

    fun updateWeight(input: List<Double>) {
        layersList[0].updateWeights(input)
    }
}
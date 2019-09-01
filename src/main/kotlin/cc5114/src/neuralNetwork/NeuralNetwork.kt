package cc5114.src.neuralNetwork

import cc5114.src.activationFunction.ActivationFunction
import cc5114.src.networkExperiment.TrainingSet
import kotlin.math.pow
import kotlin.random.Random

/**
 * Representación de una red neuronal
 *
 * @param layers número de capas de la red
 * @param neuronsByLayer número de neuranas por cada capa de la red
 * @property numberOfInputs número de entradas de la red
 */
class NeuralNetwork(layers: Int, neuronsByLayer: List<Int>, val numberOfInputs: Int) {
    val layersList: MutableList<NeuralLayer> = mutableListOf()
    var output: Int = 0
    val precision: MutableList<Double> = mutableListOf()
    val error: MutableList<Double> = mutableListOf()
    val rng: Random = Random(1234)

    init {
        require(layers == neuronsByLayer.size)
        layersList.add(NeuralLayer(neuronsByLayer[0], null))
        for(i in 1 until layers) {
            layersList.add(NeuralLayer(neuronsByLayer[i], layersList[i-1]))
            layersList[i-1].nextLayer = layersList[i]
        }
    }

    /**
     * Configura la función de activación de una capa de la red
     *
     * @param function función de activación
     * @param layer capa a configurar
     * @param layerWeights cantidad de pesos a configurar en la capa
     * @param learningRate tasa de aprendizaje para la capa
     */
    fun configureLayerFunction(function: ActivationFunction, layer: Int, layerWeights: Int, learningRate: Double) {
        layersList[layer].configureLayerFunction(function, layerWeights, learningRate)
    }

    /**
     * Retorna el resultado de la red al darle [input] como entrada
     */
    fun feed(input: List<Double>): List<Double> {
        return layersList[0].feed(input)
    }

    /**
     * Entrena la red por un epoch completo, dada una entrada como [input] y un resultado esperado en [expectedOutput]
     */
    fun train(input: List<Double>, expectedOutput: List<Double>) {
        output = predict(input)
        backPropagate(expectedOutput)
        updateWeight(input)
    }

    /**
     * Comienza la propagación de errores desde la última capa de la red
     */
    fun backPropagate(expectedOutput: List<Double>) {
        layersList.last().backPropagate(expectedOutput)
    }

    /**
     * Entrega el resultado al darle una entrada [inputs] a la red
     */
    fun predict(inputs: List<Double>): Int {
        val outputs = feed(inputs)
        return outputs.indexOf(outputs.max())
    }

    /**
     * Actualiza los pesos de las capas, dado [input]
     */
    fun updateWeight(input: List<Double>) {
        layersList[0].updateWeights(input)
    }

    /**
     * Entrena la red una cantidad [epochs] de veces, utilizando como entrada los [trainingSets]. Actualiza la precisión
     * y error de la red, para poder graficarlos más tarde
     */
    fun trainEpochs(epochs: Int, trainingSets: List<TrainingSet>) {
        for (i in 0 until epochs) {
            var correctPredictions = 0
            var totalError = 0.0
            for((input, correctOutputs) in trainingSets.shuffled(rng)) {
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


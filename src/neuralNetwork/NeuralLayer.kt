package neuralNetwork

import activationFunction.ActivationFunction
import perceptron.LearningPerceptron


/**
 * Capa de una red neuronal
 *
 * @property neurons cantidad de neuronas de la capa
 * @property previousLayer capa anterior en la red
 */
class NeuralLayer(private val neurons: Int, private val previousLayer: NeuralLayer?) {
    private var neuronsArray = arrayOfNulls<LearningPerceptron>(neurons)
    private val outputs: MutableList<Double> = MutableList(neurons) {0.0}
    var nextLayer: NeuralLayer? = null
    var learningRate: Double = 0.0

    /**
     * Función para configurar la función de activación [function] en la capa. Recibe la cantidad de pesos [layerWeights]
     * y la tasa de aprendizaje [learningRate], para crear las neuronas en caso de que sea necesario
     */
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

    /**
     * Da el valor de entrada [input] a los perceptrones de la capa, y guarda sus resultados
     */
    fun feed(input: List<Double>): List<Double> {
        for((index, perceptron) in neuronsArray.withIndex()) {
            outputs[index] = perceptron!!.calculateOutput(input)
        }
        return nextLayer?.feed(outputs) ?: outputs
    }

    /**
     * Inicia la propagación de errores. Se llama desde la última capa de la red
     *
     * @param expectedOutput salida esperada de la red
     */
    fun backPropagate(expectedOutput: List<Double>) {
        for((index, perceptron) in neuronsArray.withIndex()) {
            val error: Double = expectedOutput[index] - outputs[index]
            perceptron?.adjust(error)
        }
        previousLayer?.recursiveBackPropagate()
    }

    /**
     * Pasa la propagación de errores desde la última capa hasta el comienzo de la red
     */
    private fun recursiveBackPropagate() {
        for((index, perceptron) in neuronsArray.withIndex()) {
            var error = 0.0
            for(nextLayerPerceptron in nextLayer!!.neuronsArray) {
                error += nextLayerPerceptron!!.weights[index] * nextLayerPerceptron.delta
                perceptron?.adjust(error)
            }
        }
        previousLayer?.recursiveBackPropagate()
    }

    /**
     * Hace que se actualicen los pesos de los perceptrones de la red, dado [input]
     */
    fun updateWeights(input: List<Double>) {
        val inputs: List<Double>
        inputs = if (previousLayer == null) {
            input
        } else {
            val previousLayerOutput: MutableList<Double> = mutableListOf()
            for (perceptron in previousLayer.neuronsArray) {
                previousLayerOutput.add(perceptron!!.output)
            }
            previousLayerOutput
        }
        for(perceptron in neuronsArray) {
            perceptron?.updateWeightsAndBias(inputs)
        }
        nextLayer?.updateWeights(input)
    }
}
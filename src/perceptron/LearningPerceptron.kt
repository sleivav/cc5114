package perceptron

import activationFunction.ActivationFunction
import kotlin.properties.Delegates
import kotlin.random.Random

/**
 * Perceptrón que ajusta sus pesos y sesgos automáticamente para ir aprendiendo
 *
 * @param weightsNumber número de entradas que recivirá la neurona
 * @param learningRate tasa de aprendizaje
 * @param activationFunction función de activación de la neurona
 */
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

    /**
     * Función que ajusta automáticamente las propiedades del perceptron, dado una entrada en [inputList], el [realOutput]
     * calculado por el perceptrón, y la salida esperada [desiredOutput]
     */
    fun learnFromOutput(inputList: List<Double>, realOutput: Double, desiredOutput: Double) {
        val diff: Double = desiredOutput - realOutput
        for ((index, weight) in this.weights.withIndex()) {
            this.weights[index] = weight + this.learningRate * inputList[index] * diff
        }
        this.bias += this.learningRate * diff
    }

    /**
     * Función que calcula la salida de un perceptrón, y luego llama a la función para ajustar sus parámetros
     *
     * @param inputList entradas
     * @param desiredOutput salida esperada
     */
    fun calculateOutputAndLearn(inputList: List<Double>, desiredOutput: Double): Double {
        require(inputList.size==this.weights.size) {"El número de inputs pasados no es correcto para esta neurona"}
        val output = this.calculateOutput(inputList)
        this.learnFromOutput(inputList, output, desiredOutput)
        return output
    }

    /**
     * Actualiza la propiedad output del perceptrón después de calcular su salida con [inputList] como entradas
     */
    fun calculateOutput(inputList: List<Double>): Double {
        output = super.calculateOutput(inputList, this.weights, this.bias)
        return output
    }

    /**
     * Ajusta el delta del perceptrón dado un [error]
     */
    fun adjust(error: Double) {
        delta = error * transferDerivative(output)
    }

    /**
     * Calcula la derivada de la función de activación de un perceptrón, evaluada en [output]
     */
    private fun transferDerivative(output: Double): Double {
        return function.derivative(output)
    }

    /**
     * Actualiza los pesos y bias del perceptrón, dada la entrada [inputs]
     */
    fun updateWeightsAndBias(inputs: List<Double>) {
        for ((index, input) in inputs.withIndex()) {
            weights[index] += learningRate * delta * input
        }
        bias += learningRate * delta
    }
}
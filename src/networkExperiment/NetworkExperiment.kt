package networkExperiment

import activationFunction.ActivationFunction
import activationFunction.Sigmoid
import neuralNetwork.NeuralNetwork
import java.io.File
import kotlin.random.Random

/**
 * Clase para probar una red neuronal, con el dataset Iris
 */
class NetworkExperiment {
    private val sepalLenghts: MutableList<Double> = mutableListOf()
    private val sepalWidths: MutableList<Double> = mutableListOf()
    private val petalLenghts: MutableList<Double> = mutableListOf()
    private val petalWidths: MutableList<Double> = mutableListOf()
    private val classes: MutableList<String> = mutableListOf()
    private val classesHotlist: MutableList<List<Double>> = mutableListOf()
    private val trainingSets: MutableList<TrainingSet> = mutableListOf()
    private val rng: Random = Random(4321)

    /**
     * Lee los datos desde un archivo separado por comas, y agregar los parámetros al objeto
     */
    fun readData() {
        File("data/Iris.data").forEachLine {
            val splittedLine = it.split(',')
            sepalLenghts.add(splittedLine[0].toDouble())
            sepalWidths.add(splittedLine[1].toDouble())
            petalLenghts.add(splittedLine[2].toDouble())
            petalWidths.add(splittedLine[3].toDouble())
            classes.add(splittedLine[4])
        }
    }

    /**
     * Normaliza los datos después de leerlos
     */
    fun normalizeData() {
        normalizeList(sepalLenghts)
        normalizeList(sepalWidths)
        normalizeList(petalLenghts)
        normalizeList(petalWidths)
    }

    /**
     * Normaliza los elementos de una lista
     *
     * @param list Lista a normalizar
     */
    private fun normalizeList(list: MutableList<Double>) {
        for((index, el) in list.withIndex()) {
            list[index] = normalizeElement(el, list)
        }
    }

    /**
     * Normaliza un elemento de una lista
     *
     * @param value Valor a normalizar
     * @param set Lista a la que pertenece el valor
     */
    fun normalizeElement(value: Double, set: List<Double>): Double {
        val max: Double = set.max()!!
        val min: Double = set.min()!!
        return (value-min) / (max-min)
    }

    /**
     * Hace el hot encode de las clases del dataset
     */
    fun hotEncode() {
        val classesSet = classes.toSet()
        val classMap = classesSet.mapIndexed { index, element -> element to index}.toMap()
        createhotLists(classMap)
    }

    /**
     * Función auxiliar que crea las listas para el hot encoding
     */
    private fun createhotLists(classMap: Map<String, Int>) {
        for(c in classes) {
            val hotlist = mutableListOf<Double>(0.0, 0.0, 0.0)
            hotlist[classMap.getValue(c)] = 1.0
            classesHotlist.add(hotlist)
        }
    }

    /**
     * Escribe los datos depsués de haberlos normalizado y encodeados
     */
    fun rewriteData() {
        val writer = File("data/irisNormalized.data").printWriter().use { out ->
            for((i, line) in sepalLenghts.withIndex()) {
                out.println("${sepalLenghts[i]},${sepalWidths[i]},${petalLenghts[i]},${petalWidths[i]},${classesHotlist[i]}")
            }
        }
    }

    /**
     * Agrega lee las líneas del archivo con los datos normalizados y los agrega al trainingSet del experimento
     */
    fun addLinesToTrainingSet() {
        val lines: MutableList<String> = mutableListOf()
        File("data/irisNormalized.data").readLines().forEach { lines.add(it) }
        lines.shuffle(rng)
        for (i in 0 until (lines.size*0.8).toInt()) {
            feedLineToNetWork(lines[i])
        }
    }

    /**
     * Función auxiliar que agrega una línea al trainingSet
     */
    private fun feedLineToNetWork(line: String) {
        val splittedLine = line.split(",".toRegex(), 5)
        trainingSets.add(TrainingSet(listOf(splittedLine[0].toDouble(), splittedLine[1].toDouble(), splittedLine[2].toDouble(), splittedLine[3].toDouble()),
            stringToList(splittedLine[4])))
    }

    /**
     * Convierte el string del hot encode de una línea a una lista de Doubles
     *
     * @param s string del hot encode
     */
    private fun stringToList(s: String): List<Double> {
        val a = s.removePrefix("[").removeSuffix("]").split(',')
        return listOf(a[0].toDouble(), a[1].toDouble(), a[2].toDouble())
    }

    /**
     * Hace que la red se entrene una cierta cantidad de veces
     *
     * @param network red a entrenar
     * @param epochs cantidad de épocas que se va a entrenar la red
     */
    fun makeNetworkTrain(network: NeuralNetwork, epochs: Int) {
        network.trainEpochs(epochs, trainingSets)
    }

    /**
     * Configura una red neuronal
     *
     * @param network red a configurar
     * @param activationFunction función que usarán los perceptrones de la red
     * @param neuronsByLayer cantidad de neuronas por capa de la red
     */
    fun configureNetwork(
        network: NeuralNetwork,
        activationFunction: ActivationFunction,
        neuronsByLayer: List<Int>
    ) {
        network.configureLayerFunction(activationFunction, 0, network.numberOfInputs, 0.1)
        for (i in 1 until network.layersList.size)
        network.configureLayerFunction(activationFunction, i, neuronsByLayer[i-1], 0.1)
    }

    /**
     * Grafica la precisión y error de una red, obtenidos durante su entrenamiento
     *
     * @param network red sobre la que se hará el gráfico
     */
    fun makePrecisionAndErrorGraphs(network: NeuralNetwork) {
        networkGraph(network.precision, "Precisión de la Red Neuronal", "Épocas", "Precisión (%)")
        networkGraph(network.error, "Error de la Red Neuronal", "Épocas", "Error")
    }
}

data class TrainingSet(val inputs: List<Double>, val outputs: List<Double>)

fun main() {
    val experiment = NetworkExperiment()
    experiment.readData()
    experiment.normalizeData()
    experiment.hotEncode()
    experiment.rewriteData()
    val neuronsByLayer = listOf(4, 3)
    val network = NeuralNetwork(2, neuronsByLayer, 4)
    experiment.addLinesToTrainingSet()
    experiment.configureNetwork(network, Sigmoid(), neuronsByLayer)
    experiment.makeNetworkTrain(network, 100)
    experiment.makePrecisionAndErrorGraphs(network)
}
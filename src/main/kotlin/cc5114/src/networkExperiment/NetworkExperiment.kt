package cc5114.src.networkExperiment

import cc5114.src.activationFunction.ActivationFunction
import cc5114.src.activationFunction.Sigmoid
import cc5114.src.neuralNetwork.NeuralNetwork
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
    private val predictionSets: MutableList<TrainingSet> = mutableListOf()
    private val predictions: MutableList<Int> = mutableListOf()
    private val correctOutputs: MutableList<Int> = mutableListOf()
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
        File("data/irisNormalized.data").printWriter().use { out ->
            for(i in sepalLenghts.indices) {
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
        for (i in (lines.size*0.8).toInt() until lines.size) {
            addLineToPredictionSet(lines[i])
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
     * Función auxiliar que agrega líneas al predictionSet
     */
    private fun addLineToPredictionSet(line: String) {
        val splittedLine = line.split(",".toRegex(), 5)
        predictionSets.add(TrainingSet(listOf(splittedLine[0].toDouble(), splittedLine[1].toDouble(), splittedLine[2].toDouble(), splittedLine[3].toDouble()),
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
        networkGraph(
            network.precision,
            "Precisión de la Red Neuronal",
            "Épocas",
            "Precisión (%)"
        )
        networkGraph(network.error, "Error de la Red Neuronal", "Épocas", "Error")
    }

    fun feedPredictionSetsToNetwork(network: NeuralNetwork) {
        for (set in predictionSets) {
            predictions.add(network.predict(set.inputs))
            correctOutputs.add(set.outputs.indexOf(set.outputs.max()))
        }
    }

    fun printConfusionData() {
        val map: MutableMap<String, Int> =
            mutableMapOf(
                "0,0" to 0,
                "0,1" to 0,
                "0,2" to 0,
                "1,0" to 0,
                "1,1" to 0,
                "1,2" to 0,
                "2,0" to 0,
                "2,1" to 0,
                "2,2" to 0
            )
        for (i in predictions.indices) {
            map["${predictions[i]},${correctOutputs[i]}"] =
                map.getValue("${predictions[i]},${correctOutputs[i]}") + 1
        }
        print(map)
    }
}

data class TrainingSet(val inputs: List<Double>, val outputs: List<Double>)

fun main(args: Array<String>) {
    val experiment = NetworkExperiment()
    val neuronsByLayer = listOf(4, 3)
    val network = NeuralNetwork(2, neuronsByLayer, 4)
    with(experiment) {
        readData()
        normalizeData()
        hotEncode()
        rewriteData()
        addLinesToTrainingSet()
        configureNetwork(network, Sigmoid(), neuronsByLayer)
        makeNetworkTrain(network, 100)
        //makePrecisionAndErrorGraphs(network)
        feedPredictionSetsToNetwork(network)
        printConfusionData()
    }
}
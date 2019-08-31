package networkExperiment

import java.io.File

class NetworkExperiment {
    private val sepalLenghts: MutableList<Double> = mutableListOf()
    private val sepalWidths: MutableList<Double> = mutableListOf()
    private val petalLenghts: MutableList<Double> = mutableListOf()
    private val petalWidths: MutableList<Double> = mutableListOf()
    private val classes: MutableList<String> = mutableListOf()
    private val classesHotlist: MutableList<List<Double>> = mutableListOf()

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

    fun normalizeData() {
        normalizeList(sepalLenghts)
        normalizeList(sepalWidths)
        normalizeList(petalLenghts)
        normalizeList(petalWidths)
    }

    fun normalizeList(list: MutableList<Double>) {
        for((index, el) in list.withIndex()) {
            list[index] = normalizeElement(el, list)
        }
    }

    fun normalizeElement(value: Double, set: List<Double>): Double {
        val max: Double = set.max()!!
        val min: Double = set.min()!!
        return (value-min) / (max-min)
    }

    fun hotEncode() {
        val classesSet = classes.toSet()
        val classMap = classesSet.mapIndexed { index, element -> element to index}.toMap()
        createhotLists(classMap)
    }

    private fun createhotLists(classMap: Map<String, Int>) {
        for(c in classes) {
            val hotlist = mutableListOf<Double>(0.0, 0.0, 0.0, 0.0, 0.0)
            hotlist[classMap.getValue(c)] = 1.0
            classesHotlist.add(hotlist)
        }
    }
}

fun main() {
    val experiment = NetworkExperiment()
    experiment.readData()
    experiment.normalizeData()
    experiment.hotEncode()
}
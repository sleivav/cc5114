package neuronExperiment

import activationFunction.Step
import classificationLine.ClassificationLine
import perceptron.LearningPerceptron
import kotlin.random.Random

class NeuronExperiment(private val trainings: Int, private val pointNumbers: Int) {

    fun runExperiment() {
        val perceptron = LearningPerceptron(2, 0.1, Step())
        val rng = Random(1234)
        val minX = -10.0
        val minY = -10.0
        val maxX = 10.0
        val maxY = 10.0
        val accuracies: MutableList<Double> = mutableListOf()
        for (i in 0..trainings) {
            val m = rng.nextDouble(-10.0, 10.0)
            val n = 0.0
            val classLine = ClassificationLine(m, n)
            val pointsX: MutableList<Double> = mutableListOf()
            val pointsY: MutableList<Double> = mutableListOf()
            val outputs: MutableList<Double> = mutableListOf()
            val realOutputs: MutableList<Double> = mutableListOf()
            for (j in 0..pointNumbers) {
                val pointX: Double = rng.nextDouble(minX, maxX)
                val pointY: Double = rng.nextDouble(minY, maxY)
                val realOutput = classLine.pointPosition(pointX, pointY)
                val output = perceptron.calculateOutputAndLearn(listOf(pointX, pointY), realOutput)
                pointsX.add(pointX)
                pointsY.add(pointY)
                outputs.add(output)
                realOutputs.add(realOutput)
            }
            var correctPoints = 0.0
            for(j in 0..pointNumbers) {
                if (outputs[j] == realOutputs[j]) correctPoints += 1.0
            }
            val accuracy = correctPoints/pointNumbers
            accuracies.add(accuracy)
            if (i == 1000) this.drawPointsGraph(classLine, pointsX, pointsY, outputs, m, n)
        }
        this.drawAccuracyGraph(accuracies)
    }

    fun drawPointsGraph(classificationLine: ClassificationLine, pointsX: List<Double>, pointsY: List<Double>, outputs: List<Double>, m: Double, n: Double) {
        linePlot(classificationLine, pointsX, pointsY, outputs, m, n)
    }

    fun drawAccuracyGraph(accuracies: List<Double>) {
        linePlot(accuracies)
    }
}

fun main(args: Array<String>) {
    NeuronExperiment(100, 100).runExperiment()
}
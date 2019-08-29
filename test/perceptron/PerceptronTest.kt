package perceptron

import activationFunction.step
import org.junit.Test
import kotlin.test.assertEquals


internal class PerceptronTest {
    @Test
    fun andPerceptronTest() {
        val andPerceptron = AndPerceptron(step())
        assertEquals(0.0, andPerceptron.calculateOutput(0.0,0.0), "AND between two 0s should be 0")
        assertEquals(1.0, andPerceptron.calculateOutput(1.0,1.0), "AND between two 1s should be 1")
        assertEquals(0.0, andPerceptron.calculateOutput(0.0,1.0), "AND between 0 and 1 should be 0")
        assertEquals(0.0, andPerceptron.calculateOutput(1.0,0.0), "AND between 1 and 0 should be 0 (commutation)")
    }
    @Test
    fun orPerceptronTest() {
        val orPerceptron = OrPerceptron(step())
        assertEquals(0.0, orPerceptron.calculateOutput(0.0,0.0), "OR between two 0s should be 0")
        assertEquals(1.0, orPerceptron.calculateOutput(1.0,1.0), "OR between two 1s should be 1")
        assertEquals(1.0, orPerceptron.calculateOutput(0.0,1.0), "OR between 0 and 1 should be 1")
        assertEquals(1.0, orPerceptron.calculateOutput(1.0,0.0), "OR between 1 and 0 should be 1 (commutation)")
    }
    @Test
    fun nandPerceptronTest() {
        val nandPerceptron = NandPerceptron(step())
        assertEquals(1.0, nandPerceptron.calculateOutput(0.0,0.0), "NAND between two 0s should be 1")
        assertEquals(0.0, nandPerceptron.calculateOutput(1.0,1.0), "NAND between two 1s should be 0")
        assertEquals(1.0, nandPerceptron.calculateOutput(0.0,1.0), "NAND between 0 and 1 should be 1")
        assertEquals(1.0, nandPerceptron.calculateOutput(1.0,0.0), "NAND between 1 and 0 should be 1 (commutation)")
    }
}
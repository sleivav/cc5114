package perceptron

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PerceptronTest {
    @Test
    fun andPerceptronTest() {
        val andPerceptron = AndPerceptron()
        assertEquals(0, andPerceptron.calculateOutput(0,0), "AND between two 0s should be 0")
        assertEquals(1, andPerceptron.calculateOutput(1,1), "AND between two 1s should be 1")
        assertEquals(0, andPerceptron.calculateOutput(0,1), "AND between 0 and 1 should be 0")
        assertEquals(0, andPerceptron.calculateOutput(1,0), "AND between 1 and 0 should be 0 (commutation)")
    }
    @Test
    fun orPerceptronTest() {
        val orPerceptron = OrPerceptron()
        assertEquals(0, orPerceptron.calculateOutput(0,0), "OR between two 0s should be 0")
        assertEquals(1, orPerceptron.calculateOutput(1,1), "OR between two 1s should be 1")
        assertEquals(1, orPerceptron.calculateOutput(0,1), "OR between 0 and 1 should be 1")
        assertEquals(1, orPerceptron.calculateOutput(1,0), "OR between 1 and 0 should be 1 (commutation)")
    }
    @Test
    fun nandPerceptronTest() {
        val nandPerceptron = NandPerceptron()
        assertEquals(1, nandPerceptron.calculateOutput(0,0), "NAND between two 0s should be 1")
        assertEquals(0, nandPerceptron.calculateOutput(1,1), "NAND between two 1s should be 0")
        assertEquals(1, nandPerceptron.calculateOutput(0,1), "NAND between 0 and 1 should be 1")
        assertEquals(1, nandPerceptron.calculateOutput(1,0), "NAND between 1 and 0 should be 1 (commutation)")
    }
}
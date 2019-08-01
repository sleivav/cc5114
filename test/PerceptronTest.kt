import perceptron.AndPerceptron
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PerceptronTest {
    @Test
    fun andPerceptronTest() {
        val andPerceptron = AndPerceptron()
        assertEquals(andPerceptron.calculateOutput(listOf(0,0)), 0, "AND between two 0s should be 0")
        assertEquals(andPerceptron.calculateOutput(listOf(1,1)), 1, "AND between two 1s should be 1")
        assertEquals(andPerceptron.calculateOutput(listOf(0,1)), 0, "AND between 0 and 1 should be 0")
        assertEquals(andPerceptron.calculateOutput(listOf(1,0)), 0, "AND between 1 and 0 should be 0 (commutation)")
    }
}
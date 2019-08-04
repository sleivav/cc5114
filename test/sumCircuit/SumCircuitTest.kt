package sumCircuit

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SumCircuitTest {
    @Test
    fun sumCircuitTest() {
        val sumCircuit1 = SumCircuit(0 ,0)
        val sumCircuit2 = SumCircuit(0 ,1)
        val sumCircuit3 = SumCircuit(1 ,0)
        val sumCircuit4 = SumCircuit(1 ,1)

        assertEquals(sumCircuit1.getSumResult(), 0, "Adding two 0s should return 0")
        assertEquals(sumCircuit1.getCarryBit(), 0, "Adding two 0s shouldn't return a carry bit")

        assertEquals(sumCircuit2.getSumResult(), 1, "Adding 1 and 0 should return 1")
        assertEquals(sumCircuit2.getCarryBit(), 0, "Adding 1 and 0 shouldn't set the carry bit")

        assertEquals(sumCircuit3.getSumResult(), 1, "Adding 1 and 0 should return 1")
        assertEquals(sumCircuit3.getCarryBit(), 0, "Adding 1 and 0 shouldn't set the carry bit")

        assertEquals(sumCircuit4.getSumResult(), 0, "Adding two 1s should return 0 (result is 10)")
        assertEquals(sumCircuit4.getCarryBit(), 1, "Adding two 1s should set the carry bit")
    }
}
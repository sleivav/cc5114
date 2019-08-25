package sumCircuit

import org.junit.Test
import kotlin.test.assertEquals

internal class SumCircuitTest {
    @Test
    fun sumCircuitTest() {
        val sumCircuit1 = SumCircuit(0 ,0)
        val sumCircuit2 = SumCircuit(0 ,1)
        val sumCircuit3 = SumCircuit(1 ,0)
        val sumCircuit4 = SumCircuit(1 ,1)

        assertEquals(0, sumCircuit1.getSumResult(), "Adding two 0s should return 0")
        assertEquals(0, sumCircuit1.getCarryBit(), "Adding two 0s shouldn't return a carry bit")

        assertEquals(1, sumCircuit2.getSumResult(), "Adding 1 and 0 should return 1")
        assertEquals(0, sumCircuit2.getCarryBit(), "Adding 1 and 0 shouldn't set the carry bit")

        assertEquals(1, sumCircuit3.getSumResult(), "Adding 1 and 0 should return 1")
        assertEquals(0, sumCircuit3.getCarryBit(), "Adding 1 and 0 shouldn't set the carry bit")

        assertEquals(0, sumCircuit4.getSumResult(), "Adding two 1s should return 0 (result is 10)")
        assertEquals(1, sumCircuit4.getCarryBit(), "Adding two 1s should set the carry bit")
    }
}
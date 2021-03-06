import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import seamcarving.Dijkstra

internal class DijkstraTest {

    private lateinit var dk:Dijkstra

    @BeforeEach
    fun before() {
        val grid = arrayOf(
            arrayOf(0.0, 10.0, 25.0),
            arrayOf(2.0, 18.0, 3.0),
            arrayOf(1.0, 3.0, 4.0)
        )
        dk = Dijkstra(grid)
    }


    @Test
    fun printGrid() {

        dk.printGrid()
    }

    @Test
    fun ShortestPath() {

        val sum = dk.shortestPath()

        assertEquals(9.0, sum)
    }

    @Test
    fun findAnotherShortestPathSum() {

        val grid = arrayOf(
            arrayOf(0.0, 10.0, 25.0),
            arrayOf(3.0, 18.0, 3.0),
            arrayOf(2.0, 4.0, 4.0)
        )

        dk = Dijkstra(grid)

        val sum = dk.shortestPath()

        assertEquals(11.0, sum)
    }

    @Test
    fun getAdjacentVectors() {

        val grid = arrayOf(
            arrayOf(0.0, 10.0, 25.0),
            arrayOf(3.0, 18.0, 3.0),
            arrayOf(2.0, 4.0, 4.0)
        )

        var distanceVectors = Array(grid.size){Array(grid[0].size){Double.POSITIVE_INFINITY} }

        dk.distanceVectors = distanceVectors

        var neighbors = dk.getAdjacentVectors(listOf(0,0) )

        assertEquals(
            setOf(
               listOf (1,0),
               listOf (0,1),
               listOf (1,1)
        ), neighbors)

        neighbors = dk.getAdjacentVectors(listOf(1, 1) )

        assertEquals(
            setOf(
                listOf (0,0),
                listOf (0,1),
                listOf (0,2),
                listOf (1,0),
                listOf (1,2),
                listOf (2,0),
                listOf (2,1),
                listOf (2,2),
            ).toSet(), neighbors.toSet())
    }

    @Test
    fun shortestPathSeam() {

        val grid = arrayOf(
            arrayOf(0.0, 10.0, 25.0),
            arrayOf(3.0, 18.0, 3.0),
            arrayOf(2.0, 4.0, 4.0)
        )

        dk = Dijkstra(grid)

        val sum = dk.shortestPathVerticalSeam()


    }
    @Test
    fun shortestPathSequence(){
        val grid = arrayOf(
            arrayOf(0.0, 10.0, 25.0),
            arrayOf(3.0, 18.0, 3.0),
            arrayOf(2.0, 4.0, 4.0)
        )

        dk = Dijkstra(grid)

        println(dk.shortestPathVerticalSeam())

        val sequence = dk.getShortestPathSequence(listOf(2,4))

        println(sequence)


    }
}
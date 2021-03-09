package seamcarving

import java.util.HashSet


class Dijkstra(val grid: Array<Array<Double>>) {

    fun printGrid() {
        repeat(grid.size) { y ->
            repeat(grid[0].size) { x ->
                print("" +grid[y][x] + ", ")
            }
            println()
        }
    }
    fun <T> listEqualsIgnoreOrder(list1: List<T>?, list2: List<T>?): Boolean {
        return HashSet(list1) == HashSet(list2)
    }

    fun fillAdjacentVectors (adjacentVectors: List<List<Int>>): List<List<Int>> {

        return adjacentVectors
            .filter{ adjacentVector ->
                try {
                    grid[adjacentVector[1]][adjacentVector[0]]
                    true
                }catch(e: Exception){
                    false
                }
            }

    }
    fun seamAdjacency (x:Int, y:Int):List<List<Int>>{
        return listOf(
            listOf(x ,    y + 1),
            listOf(x - 1, y + 1),
            listOf(x + 1, y + 1),
        )
    }
    fun imaginarySeamAdjacency (x:Int, y:Int):List<List<Int>>{
        return listOf(
            listOf(x - 1, y),
            listOf(x + 1, y),
            listOf(x ,    y + 1),
            listOf(x - 1, y + 1),
            listOf(x + 1, y + 1),
        )
    }
    fun gridAdjacency(x:Int, y:Int):List<List<Int>>{
        return listOf(
            listOf(x - 1, y),
            listOf(x + 1, y),
            listOf(x ,    y + 1),
            listOf(x ,    y - 1),
            listOf(x - 1, y + 1),
            listOf(x + 1, y - 1),
            listOf(x - 1, y - 1),
            listOf(x + 1, y + 1),
        )
    }

    fun getAdjacentVectors (vector: List<Int>, vectors: MutableMap<List<Int>, Double>): List<List<Int>> {
        var (x, y) = vector


        var adjacentVectors = gridAdjacency(x,y)

        return adjacentVectors
            .filter{ adjacentVector ->
                try {
                    grid[adjacentVector[1]][adjacentVector[0]]
                    true
                }catch(e: Exception){
                    false
                }
            }

    }
    fun getAdjacentVectorsVerticalSeam (vector: List<Int>, vectors: MutableMap<List<Int>, Double>): List<List<Int>> {
        var (x, y) = vector

        var adjacentVectors: List<List<Int>>
        if (y == 0 || y == grid.size - 1){
            adjacentVectors = imaginarySeamAdjacency(x,y)
        }else {
            adjacentVectors = seamAdjacency(x,y)
        }

        return adjacentVectors
            .filter{ adjacentVector ->
                try {
                    grid[adjacentVector[1]][adjacentVector[0]]
                    true
                }catch(e: Exception){
                    false
                }
            }

    }

    fun updateAdjacentVectors(vector: List<Int>, adjacentVectors: List<List<Int>>, vectors: MutableMap<List<Int>, Double>, shortestPath: MutableSet<List<Int>>) {

//        println("Adjacents: $adjacents")
        adjacentVectors
            .filter{
                vectors.contains(it)
            }
            .filterNot{
                shortestPath.contains(it)
            }
            .filter{ adjacentVector ->
//                println(adjacentVector.toString())
                val (x:Int,y:Int) = adjacentVector
                val currentMinimumDistanceToAdjacent = vectors[vector]!!.toDouble() + grid[y][x]
                val currentValueOfAdjacent = vectors[adjacentVector]!!.toDouble()
//                println("$adjacentVector distance from source = $currentMinimumDistanceToAdjacent")
//                println("$adjacentVector current distance = $currentValueOfAdjacent")
                currentMinimumDistanceToAdjacent < currentValueOfAdjacent
            }
            .forEach{ adjacentVector ->
                vectors[adjacentVector] = vectors[vector]!!.toDouble() + grid[adjacentVector[1]][adjacentVector[0]]
            }


    }
    fun initialiseInfiniteDistanceVectors():MutableMap<List<Int>, Double> {
        var distanceVectors = mutableMapOf<List<Int>, Double>()

        repeat(grid.size) { y ->

            repeat(grid[0].size) { x ->
                distanceVectors[listOf(x,y)] = Double.POSITIVE_INFINITY
            }
        }

        distanceVectors[listOf(0,0)] = 0.0
        return distanceVectors

    }

    fun findMinimumDistanceVector(
        distanceVectors: MutableMap<List<Int>, Double>,
        shortestPathSet: MutableSet<List<Int>>
    ): List<Int> {
        var minDistance = Double.POSITIVE_INFINITY

        var shortestKey: List<Int> = listOf()

        distanceVectors.filterNot{vector ->
            shortestPathSet.contains(vector.key)
        }.forEach{ vector ->
            if (vector.value < minDistance) {
                minDistance = vector.value
                shortestKey = vector.key
            }
        }
        return shortestKey
    }
    //    fun shortestPathSeam(){
//        var shortestPathSet = mutableSetOf<List<Int>>()
//        while ( shortestPathSet != distanceVectors.keys.toSet() ){
//            findMinimumDistanceVector(distanceVectors)
//
//            var minDistance = Double.POSITIVE_INFINITY
//
//            var shortestKey: List<Int> = listOf()
//
//            distanceVectors.filterNot{vector ->
//                shortestPathSet.contains(vector.key)
//            }.forEach{ vector ->
//                if (vector.value < minDistance) {
//                    minDistance = vector.value
//                    shortestKey = vector.key
//                }
//            }
////            println("Next shortest = $shortestKey")
//
//
//            shortestPathSet.add(shortestKey)
////        part1()
//            val adjacentVectors = getAdjacentVectorsVerticalSeam(shortestKey, distanceVectors)
//
//            updateAdjacentVectors(shortestKey, adjacentVectors, distanceVectors, shortestPathSet)
////                println(distanceVectors)
////                println(distanceVectors.keys)
////                println(shortestPathSet)
////        distanceVectors.remove(shortestKey)
//
//        }
//
//    }
    fun shortestPathSeam(): Double? {

        var shortestPathSet = mutableSetOf<List<Int>>()
        var distanceVectors = initialiseInfiniteDistanceVectors()

        while ( shortestPathSet != distanceVectors.keys.toSet() ){


            var shortestKey = findMinimumDistanceVector(distanceVectors, shortestPathSet)

            shortestPathSet.add(shortestKey)
            val adjacentVectors = getAdjacentVectorsVerticalSeam(shortestKey, distanceVectors)

            updateAdjacentVectors(shortestKey, adjacentVectors, distanceVectors, shortestPathSet)
        }

        return distanceVectors[listOf(grid.size - 1, grid[0].size - 1)]


    }
    fun shortestPath(): Double? {

        var shortestPathSet = mutableSetOf<List<Int>>()
        var distanceVectors = initialiseInfiniteDistanceVectors()

        while ( shortestPathSet != distanceVectors.keys.toSet() ){


            var shortestKey = findMinimumDistanceVector(distanceVectors, shortestPathSet)
//            println("Next shortest = $shortestKey")

            shortestPathSet.add(shortestKey)
            val adjacentVectors = getAdjacentVectors(shortestKey, distanceVectors)

            updateAdjacentVectors(shortestKey, adjacentVectors, distanceVectors, shortestPathSet)
//                println(distanceVectors)
//                println(distanceVectors.keys)
//                println(shortestPathSet)
//        distanceVectors.remove(shortestKey)

        }

//        println(shortestPathSet)
//        return distanceVectorsj
        return distanceVectors[listOf(grid.size - 1, grid[0].size - 1)]


    }




}


fun main(){

    val grid = Array(1){ arrayOf(1.0, 2.0) }
    val dk = Dijkstra(grid)

    dk.printGrid()

}
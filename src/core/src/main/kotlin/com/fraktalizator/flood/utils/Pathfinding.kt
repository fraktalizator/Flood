package com.fraktalizator.flood.utils

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.math.Vector2
import com.fraktalizator.flood.GameWorld
import com.fraktalizator.flood.componentes.PositionComponent.Companion.GRIDSIZE
import kotlin.math.abs

class Pathfinding(GameWorld: GameWorld) {
    private val GameWorld: GameWorld = GameWorld

    fun isPathValid(startNodePos: Vector2, endNodePos: Vector2, range: Int): Boolean {
        val positionAndMoveCosts = flood(startNodePos, range)
        return positionAndMoveCosts.containsKey(endNodePos)
    }


    fun getPath(startNodePos: Vector2, endNodePos: Vector2?): ArrayList<Direction> {
        val directionsReversed = ArrayList<Direction>()

        val range = startNodePos.dst(endNodePos).toInt()
        val positionAndMoveCosts = flood(startNodePos, range)

        var currentPosition = Vector2(endNodePos)

        for (i in 0..range) {
            if (positionAndMoveCosts[currentPosition] == 0) {
                return directionsReversed
            }
            val upTilePositon = Vector2(currentPosition.x + 0 * GRIDSIZE, currentPosition.y + 1 * GRIDSIZE)
            val rightTilePositon = Vector2(currentPosition.x + 1 * GRIDSIZE, currentPosition.y + 0 * GRIDSIZE)
            val downTilePositon = Vector2(currentPosition.x + 0 * GRIDSIZE, currentPosition.y - 1 * GRIDSIZE)
            val leftTilePositon = Vector2(currentPosition.x - 1 * GRIDSIZE, currentPosition.y + 0 * GRIDSIZE)
            val upPathCost = positionAndMoveCosts[upTilePositon]!!
            val rightPathCost = positionAndMoveCosts[rightTilePositon]!!
            val downPathCost = positionAndMoveCosts[downTilePositon]!!
            val leftPathCost = positionAndMoveCosts[leftTilePositon]!!

            val aroundCostList: ArrayList<Int> = ArrayList<Int>()
            aroundCostList.add(upPathCost)
            aroundCostList.add(rightPathCost)
            aroundCostList.add(downPathCost)
            aroundCostList.add(leftPathCost)
            var minValue = aroundCostList[0]
            for (value in aroundCostList) {
                if (value < minValue) {
                    minValue = value
                }
            }
            if (upPathCost == minValue) {
                directionsReversed.add(Direction.Up)
                currentPosition = upTilePositon
            } else if (rightPathCost == minValue) {
                directionsReversed.add(Direction.Right)
                currentPosition = rightTilePositon
            } else if (downPathCost == minValue) {
                directionsReversed.add(Direction.Down)
                currentPosition = downTilePositon
            } else if (leftPathCost == minValue) {
                directionsReversed.add(Direction.Left)
                currentPosition = leftTilePositon
            }
        }

        val directionsCorrected = ArrayList<Direction>()
        for (i in directionsReversed.indices.reversed()) {
            val direction = directionsCorrected[i]
            if (direction == Direction.Left) directionsCorrected.add(Direction.Right)
            else if (direction == Direction.Right) directionsCorrected.add(Direction.Left)
            else if (direction == Direction.Up) directionsCorrected.add(Direction.Down)
            else if (direction == Direction.Down) directionsCorrected.add(Direction.Up)
        }

        return directionsCorrected
    }

    // assuming start node is center of positionAndMoveCosts array
    fun isPathValid(endNodePos: Vector2?, positionAndMoveCosts: HashMap<Vector2?, Int?>): Boolean {
        return positionAndMoveCosts.containsKey(endNodePos)
    }


    fun getPath(
        startNodePos: Vector2,
        endNodePos: Vector2?,
        positionAndMoveCosts: HashMap<Vector2, Int>
    ): ArrayList<Direction> {
        val directionsReversed = ArrayList<Direction>()
        val range = startNodePos.dst(endNodePos).toInt()
        var currentPosition = Vector2(endNodePos)

        for (i in 0..range) {
            if (positionAndMoveCosts[currentPosition] == 0) {
                return reverseDirections(directionsReversed)
            }
            val upTilePositon = Vector2(currentPosition.x + 0 * GRIDSIZE, currentPosition.y + 1 * GRIDSIZE)
            val rightTilePositon = Vector2(currentPosition.x + 1 * GRIDSIZE, currentPosition.y + 0 * GRIDSIZE)
            val downTilePositon = Vector2(currentPosition.x + 0 * GRIDSIZE, currentPosition.y - 1 * GRIDSIZE)
            val leftTilePositon = Vector2(currentPosition.x - 1 * GRIDSIZE, currentPosition.y + 0 * GRIDSIZE)

            val upPathCost =
                if (positionAndMoveCosts[upTilePositon] == null) 999 else positionAndMoveCosts[upTilePositon]!!
            val rightPathCost =
                if (positionAndMoveCosts[rightTilePositon] == null) 999 else positionAndMoveCosts[rightTilePositon]!!
            val downPathCost =
                if (positionAndMoveCosts[downTilePositon] == null) 999 else positionAndMoveCosts[downTilePositon]!!
            val leftPathCost =
                if (positionAndMoveCosts[leftTilePositon] == null) 999 else positionAndMoveCosts[leftTilePositon]!!

            var aroundCostList: ArrayList<Int> = ArrayList<Int>()
            aroundCostList += (upPathCost)
            aroundCostList += (rightPathCost)
            aroundCostList += (downPathCost)
            aroundCostList += (leftPathCost)
            var minValue = aroundCostList[0]
            for (value in aroundCostList) {
                if (value < minValue) {
                    minValue = value
                }
            }
            if (upPathCost == minValue) {
                directionsReversed += (Direction.Up)
                currentPosition = upTilePositon
            } else if (rightPathCost == minValue) {
                directionsReversed += (Direction.Right)
                currentPosition = rightTilePositon
            } else if (downPathCost == minValue) {
                directionsReversed += (Direction.Down)
                currentPosition = downTilePositon
            } else if (leftPathCost == minValue) {
                directionsReversed += (Direction.Left)
                currentPosition = leftTilePositon
            }
        }
        return reverseDirections(directionsReversed)
    }

    private fun reverseDirections(directions: ArrayList<Direction>): ArrayList<Direction> {
        var directionsReversed = ArrayList<Direction>()
        for (i in directions.indices.reversed()) {
            val direction = directions[i]
            if (direction == Direction.Left) directionsReversed += (Direction.Right)
            else if (direction == Direction.Right) directionsReversed  += (Direction.Left)
            else if (direction == Direction.Up) directionsReversed  += (Direction.Down)
            else if (direction == Direction.Down) directionsReversed  += (Direction.Up)
        }
        return directionsReversed
    }


    fun getTilesInRange(position: Vector2, range: Int): ArrayList<Vector2> {
        val tilePositions = ArrayList<Vector2>()
        val positionAndMoveCosts = flood(position, range)

        for (pos in positionAndMoveCosts.keys) {
            if (positionAndMoveCosts[pos]!! <= range) tilePositions.add(pos)
        }

        return tilePositions
    }

    fun getTilesInRange(positionAndMoveCosts: HashMap<Vector2, Int>, range: Int): ArrayList<Vector2> {
        val tilePositions = ArrayList<Vector2>()

        for (pos in positionAndMoveCosts.keys) {
            if (positionAndMoveCosts[pos]!! <= range) tilePositions.add(pos)
        }

        return tilePositions
    }

    fun flood(position: Vector2, range: Int): HashMap<Vector2, Int> {
        val positionAndMoveCosts = hashMapInit(range, position)
        val justComputedTiles = ArrayList<Vector2>()
        var justComputedTilesTMP: ArrayList<Vector2>
        justComputedTiles.add(position)

        for (n in 0 until range) {
            justComputedTilesTMP = ArrayList()
            for (tile in justComputedTiles) {
                val currentCost = positionAndMoveCosts[tile]!!

                val upTilePositon = Vector2(tile.x + 0 * GRIDSIZE, tile.y + 1 * GRIDSIZE)
                val upTileCost = getTileCost(upTilePositon)

                val rightTilePositon = Vector2(tile.x + 1 * GRIDSIZE, tile.y + 0 * GRIDSIZE)
                val rightTileCost = getTileCost(rightTilePositon)

                val downTilePositon = Vector2(tile.x + 0 * GRIDSIZE, tile.y - 1 * GRIDSIZE)
                val downTileCost = getTileCost(downTilePositon)

                val leftTilePositon = Vector2(tile.x - 1 * GRIDSIZE, tile.y + 0 * GRIDSIZE)
                val leftTileCost = getTileCost(leftTilePositon)


                if (upTileCost + currentCost < positionAndMoveCosts[upTilePositon]!!) {
                    positionAndMoveCosts[upTilePositon] = upTileCost + currentCost
                    justComputedTilesTMP.add(upTilePositon)
                }

                if (rightTileCost + currentCost < positionAndMoveCosts[rightTilePositon]!!) {
                    positionAndMoveCosts[rightTilePositon] = rightTileCost + currentCost
                    justComputedTilesTMP.add(rightTilePositon)
                }


                if (downTileCost + currentCost < positionAndMoveCosts[downTilePositon]!!) {
                    positionAndMoveCosts[downTilePositon] = downTileCost + currentCost
                    justComputedTilesTMP.add(downTilePositon)
                }

                if (leftTileCost + currentCost < positionAndMoveCosts[leftTilePositon]!!) {
                    positionAndMoveCosts[leftTilePositon] = leftTileCost + currentCost
                    justComputedTilesTMP.add(leftTilePositon)
                }
            }
            justComputedTiles.clear()
            justComputedTiles.addAll(justComputedTilesTMP)
            justComputedTilesTMP.clear()
        }
        return positionAndMoveCosts
    }

    fun floodIgnoreTerrainCostOLD(position: Vector2, range: Int): HashMap<Vector2, Int> {
        val positionAndMoveCosts = hashMapInit(range, position)
        val justComputedTiles = ArrayList<Vector2>()
        var justComputedTilesTMP: ArrayList<Vector2>
        justComputedTiles.add(position)

        for (n in 0 until range) {
            justComputedTilesTMP = ArrayList()
            for (tile in justComputedTiles) {
                val currentCost = positionAndMoveCosts[tile]!!

                val upTilePositon = Vector2(tile.x + 0 * GRIDSIZE, tile.y + 1 * GRIDSIZE)
                val upTileCost = if (getTileCost(upTilePositon) < 90) 1 else getTileCost(upTilePositon)

                val rightTilePositon = Vector2(tile.x + 1 * GRIDSIZE, tile.y + 0 * GRIDSIZE)
                val rightTileCost = if (getTileCost(rightTilePositon) < 90) 1 else getTileCost(rightTilePositon)

                val downTilePositon = Vector2(tile.x + 0 * GRIDSIZE, tile.y - 1 * GRIDSIZE)
                val downTileCost = if (getTileCost(downTilePositon) < 90) 1 else getTileCost(downTilePositon)

                val leftTilePositon = Vector2(tile.x - 1 * GRIDSIZE, tile.y + 0 * GRIDSIZE)
                val leftTileCost = if (getTileCost(leftTilePositon) < 90) 1 else getTileCost(leftTilePositon)


                if (upTileCost + currentCost < positionAndMoveCosts[upTilePositon]!!) {
                    positionAndMoveCosts[upTilePositon] = upTileCost + currentCost
                    justComputedTilesTMP.add(upTilePositon)
                }

                if (rightTileCost + currentCost < positionAndMoveCosts[rightTilePositon]!!) {
                    positionAndMoveCosts[rightTilePositon] = rightTileCost + currentCost
                    justComputedTilesTMP.add(rightTilePositon)
                }


                if (downTileCost + currentCost < positionAndMoveCosts[downTilePositon]!!) {
                    positionAndMoveCosts[downTilePositon] = downTileCost + currentCost
                    justComputedTilesTMP.add(downTilePositon)
                }

                if (leftTileCost + currentCost < positionAndMoveCosts[leftTilePositon]!!) {
                    positionAndMoveCosts[leftTilePositon] = leftTileCost + currentCost
                    justComputedTilesTMP.add(leftTilePositon)
                }
            }
            justComputedTiles.clear()
            justComputedTiles.addAll(justComputedTilesTMP)
            justComputedTilesTMP.clear()
        }
        positionAndMoveCosts[position] = 0
        return positionAndMoveCosts
    }


    fun getTilesAround(range: Int, position: Vector2): ArrayList<Vector2> {
        val tilePositions = ArrayList<Vector2>()
        var iterateDirection = Vector2(1 * GRIDSIZE.toFloat(), -1 * GRIDSIZE)
        val currentCalculatedTilePosition = Vector2(position.x, range * GRIDSIZE + position.y)
        for (i in 0..4 * range) {
            tilePositions.add(Vector2(currentCalculatedTilePosition))
            if (currentCalculatedTilePosition == Vector2(range * GRIDSIZE + position.x, position.y)) iterateDirection =
                Vector2(-1 * GRIDSIZE, -1 * GRIDSIZE)
            if (currentCalculatedTilePosition == Vector2(position.x, -range * GRIDSIZE + position.y)) iterateDirection =
                Vector2(-1 * GRIDSIZE, 1 * GRIDSIZE)
            if (currentCalculatedTilePosition == Vector2(-range * GRIDSIZE + position.x, position.y)) iterateDirection =
                Vector2(1 * GRIDSIZE, 1 * GRIDSIZE)
            currentCalculatedTilePosition.add(iterateDirection)
        }
        return tilePositions
    }

    private fun getTilesCircle(position: Vector2, range: Int): HashMap<Vector2, Int> {
        val positionAndMoveCosts = HashMap<Vector2, Int>()

        for (xCord in range downTo -range - 1 + 1) {
            for (yCord in range downTo -range - 1 + 1) {
                if (abs(xCord.toDouble()) + abs(yCord.toDouble()) <= range) {
                    val tilepos = Vector2(position.x + xCord * GRIDSIZE, position.y + yCord * GRIDSIZE)
                    positionAndMoveCosts[tilepos] = 0
                }
            }
        }

        return positionAndMoveCosts
    }

    private fun getTileCost(position: Vector2): Int {
        val cell = (GameWorld.tiledMap.getLayers().get(0) as TiledMapTileLayer).getCell(
            position.x.toInt() / GRIDSIZE.toInt(),
            position.y.toInt() / GRIDSIZE.toInt()
        )
            ?: return 9999
        if (GameWorld.entities.get(position) != null) return GameWorld.entities.get(position)!!.moveCost
        return cell.tile.properties["moveCost"].toString().toInt()
    }

    private fun hashMapInit(range: Int, position: Vector2): HashMap<Vector2, Int> {
        val positionAndMoveCosts = HashMap<Vector2, Int>()
        for (xCord in range downTo -range - 1 + 1) {
            for (yCord in range downTo -range - 1 + 1) {
                if (abs(xCord.toDouble()) + abs(yCord.toDouble()) <= range) {
                    val tilepos = Vector2(position.x + xCord * GRIDSIZE, position.y + yCord * GRIDSIZE)
                    positionAndMoveCosts[tilepos] = range + 1
                }
            }
        }
        positionAndMoveCosts[position] = 0
        return positionAndMoveCosts
    }

    enum class Direction(direction: String, var posFrame: Int) {
        Up("u", 0),
        Right("r", 1),
        Down("d", 2),
        Left("l", 3);

        var targetTilePos: Vector2 = Vector2(0f, 0f)

        init {
            when (direction) {
                "u" -> targetTilePos = Vector2(0f, 1f)
                "l" -> targetTilePos = Vector2(-1f, 0f)
                "d" -> targetTilePos = Vector2(0f, -1f)
                "r" -> targetTilePos = Vector2(1f, 0f)
            }
        }
    } //    private ArrayList<Directions> getDirectionsToCalculate(int n, Vector2 position, Vector2 tile){
    //        ArrayList<Directions> directionsToCalculate = new ArrayList<>(4);
    //        if( n == 0){
    //            directionsToCalculate.add(Directions.North);
    //            directionsToCalculate.add(Directions.East);
    //            directionsToCalculate.add(Directions.South);
    //            directionsToCalculate.add(Directions.West);
    //        }
    //        else if(tile.equals(new Vector2(n*GRIDSIZE+position.x, 0*GRIDSIZE+position.y))){
    //            directionsToCalculate.add(Directions.North);
    //            directionsToCalculate.add(Directions.South);
    //            directionsToCalculate.add(Directions.East);
    //        }
    //        else if(tile.equals(new Vector2(0*GRIDSIZE+position.x, -n*GRIDSIZE+position.y))){
    //            directionsToCalculate.add(Directions.East);
    //            directionsToCalculate.add(Directions.South);
    //            directionsToCalculate.add(Directions.West);
    //        }
    //        else if(tile.equals(new Vector2(-n*GRIDSIZE+position.x, 0*GRIDSIZE+position.y))){
    //            directionsToCalculate.add(Directions.North);
    //            directionsToCalculate.add(Directions.South);
    //            directionsToCalculate.add(Directions.West);
    //        }
    //        else if(tile.equals(new Vector2(0*GRIDSIZE+position.x, n*GRIDSIZE+position.y))){
    //            directionsToCalculate.add(Directions.North);
    //            directionsToCalculate.add(Directions.East);
    //            directionsToCalculate.add(Directions.West);
    //        }
    //        else if(tile.x > 0*GRIDSIZE+position.x && tile.y > 0*GRIDSIZE+position.y){
    //            directionsToCalculate.add(Directions.North);
    //            directionsToCalculate.add(Directions.East);
    //            directionsToCalculate.add(Directions.South);
    //        }
    //        else if(tile.x > 0*GRIDSIZE+position.x && tile.y < 0*GRIDSIZE+position.y){
    //            directionsToCalculate.add(Directions.East);
    //            directionsToCalculate.add(Directions.South);
    //            directionsToCalculate.add(Directions.West);
    //        }
    //        else if(tile.x < 0*GRIDSIZE+position.x && tile.y < 0*GRIDSIZE+position.y){
    //            directionsToCalculate.add(Directions.South);
    //            directionsToCalculate.add(Directions.West);
    //            directionsToCalculate.add(Directions.East);
    //        }
    //        else if(tile.x < 0*GRIDSIZE+position.x && tile.y > 0*GRIDSIZE+position.y){
    //            directionsToCalculate.add(Directions.North);
    //            directionsToCalculate.add(Directions.West);
    //            directionsToCalculate.add(Directions.East);
    //        }
    //        return directionsToCalculate;
    //    }
    //    public HashMap<Vector2, Integer> floodIsrakaaasdasdasdadgnoreTerrainCost(Vector2 position, int range){
    //        HashMap<Vector2, Integer> positionAndMoveCosts = hashMapInit(range, position);
    //
    //        for (int n = 0; n < range;n++) {
    //            ArrayList<Vector2> tilesInRange = getTilesAround(n, position);
    //            for(Vector2 tile : tilesInRange) {
    //                int currentCost = positionAndMoveCosts.get(tile);
    //
    //                Vector2 upTilePositon = new Vector2(tile.x+0*GRIDSIZE, tile.y+1*GRIDSIZE);
    //                int upTileCost = getTileCost(upTilePositon) < 90 ? 1: getTileCost(upTilePositon);
    //
    //                Vector2 rightTilePositon = new Vector2(tile.x+1*GRIDSIZE, tile.y+0*GRIDSIZE);
    //                int rightTileCost = getTileCost(rightTilePositon) < 90 ? 1: getTileCost(rightTilePositon);
    //
    //                Vector2 downTilePositon = new Vector2(tile.x+0*GRIDSIZE, tile.y-1*GRIDSIZE);
    //                int downTileCost = getTileCost(downTilePositon) < 90 ? 1: getTileCost(downTilePositon);
    //
    //                Vector2 leftTilePositon = new Vector2(tile.x-1*GRIDSIZE, tile.y+0*GRIDSIZE);
    //                int leftTileCost = getTileCost(leftTilePositon) < 90 ? 1: getTileCost(leftTilePositon);
    //
    //
    //                if(upTileCost+currentCost< positionAndMoveCosts.get(upTilePositon) || positionAndMoveCosts.get(upTilePositon) == 0)
    //                    positionAndMoveCosts.put(upTilePositon, upTileCost+currentCost);
    //
    //                if(rightTileCost+currentCost< positionAndMoveCosts.get(rightTilePositon) || positionAndMoveCosts.get(rightTilePositon) == 0)
    //                    positionAndMoveCosts.put(rightTilePositon, rightTileCost+currentCost);
    //
    //
    //                if(downTileCost+currentCost< positionAndMoveCosts.get(downTilePositon) || positionAndMoveCosts.get(downTilePositon) == 0)
    //                    positionAndMoveCosts.put(downTilePositon, downTileCost+currentCost);
    //
    //                if(leftTileCost+currentCost< positionAndMoveCosts.get(leftTilePositon) || positionAndMoveCosts.get(leftTilePositon) == 0)
    //                    positionAndMoveCosts.put(leftTilePositon, leftTileCost+currentCost);
    //            }
    //        }
    //        positionAndMoveCosts.put(position, 0);
    //        return positionAndMoveCosts;
    //    }
    //    public enum Directions{
    //
    //        North("N"),
    //        East("E"),
    //        South("S"),
    //        West("W");
    //
    //        Directions(String direction) {
    //            switch (direction) {
    //                case "N":
    //                    targetTilePos = new Vector2(0, 1*GRIDSIZE);
    //                    break;
    //                case "E":
    //                    targetTilePos = new Vector2(-1*GRIDSIZE, 0);
    //                    break;
    //                case "S":
    //                    targetTilePos = new Vector2(0, -1*GRIDSIZE);
    //                    break;
    //                case "W":
    //                    targetTilePos = new Vector2(1*GRIDSIZE, 0);
    //                    break;
    //
    //            }
    //        }
    //        Vector2 targetTilePos= new Vector2(0, 0);
    //    }
}

package Cells

import org.openrndr.color.ColorRGBa
import org.openrndr.draw.Drawer
import kotlin.random.Random

class Grid(numXCells: Int, numYCells: Int) {

    val numXCells = numXCells
    val numYCells = numYCells

    var cells = createSymmetricalCellGrid(numXCells, numYCells)

    private fun createSymmetricalCellGrid(numXCells: Int, numYCells: Int): Array<Array<LifeCell>> {
        var cells = Array(numXCells) { Array(numYCells) {LifeCell()} }

        for(x in 0 until numXCells)
            for(y in 0 until numYCells)
            {
                cells[x][y] = LifeCell()
            }

        for(x in 0 until numXCells / 2)
        {
            for(y in 0..4)
            {
                var alive = Random.nextDouble() < 0.05

                cells[x][y].alive = alive
                cells[numXCells - x - 1][y].alive = alive
                cells[numXCells - x - 1][numYCells - y - 1].alive = alive
                cells[x][numYCells - y - 1].alive = alive
            }
        }

        for(y in 0 until numYCells / 2)
        {
            for(x in 0..5)
            {
                var alive = Random.nextDouble() < 0.05
                cells[x][y].alive = alive
                cells[numXCells - x - 1][y].alive = alive
                cells[numXCells - x - 1][numYCells - y - 1].alive = alive
                cells[x][numYCells - y - 1].alive = alive
            }
        }


        return cells
    }

    private fun createEmptyCellGrid(numXCells: Int, numYCells: Int): Array<Array<LifeCell>> {
        var cells = Array(numXCells) { Array(numYCells) {LifeCell()} }

        for(x in 0 until numXCells)
            for(y in 0 until numYCells)
            {
                cells[x][y] = LifeCell()
            }

        return cells
    }

    private fun createRandomCellGrid(numXCells: Int, numYCells: Int): Array<Array<LifeCell>>  {
        var cells = Array(numXCells) { Array(numYCells) {LifeCell()} }

        for(x in 0 until numXCells)
            for(y in 0 until numYCells)
            {
                cells[x][y] = LifeCell()

                var alive = Random.nextDouble() < 0.05
                cells[x][y].alive = alive
            }

        return cells
    }

    fun updateGrid()
    {
        cells = nextStep(cells)
    }

    fun moveCellUp()
    {
        val emptyGrid = createEmptyCellGrid(numXCells, numYCells)
        var theseCells = cells
        for(x in 0 until numXCells)
            for(y in 1 until numYCells - 1)
            {
                emptyGrid[x][y] = theseCells[x][y - 1]
            }
        cells = emptyGrid
    }

    fun nextStep(cells: Array<Array<LifeCell>>): Array<Array<LifeCell>> {
        val ret = Array(numXCells) { Array(numYCells) {LifeCell()} }

        for(x in 1 until numXCells - 1)
        {
            for(y in 1 until numYCells - 1)
            {
                val neighborCount = countNeighbors(x, y, cells)

                if(cells[x][y].alive)
                {
                    if(neighborCount < 2)
                        ret[x][y].alive = false
                    ret[x][y].alive = neighborCount < 4
                    ret[x][y].numStepsAlive = cells[x][y].numStepsAlive + 1

                    if(ret[x][y].numStepsAlive > 100)
                        ret[x][y].alive = false
                }
                else
                {
                    if(neighborCount == 3)
                        ret[x][y].alive = true
                }
            }
        }

        return ret
    }

    fun countNeighbors(xPos: Int, yPos: Int, cells: Array<Array<LifeCell>>): Int
    {
        var neighbors = 0

        //top row
        if(cells[xPos - 1][yPos+1].alive)
            neighbors++
        if(cells[xPos][yPos+1].alive)
            neighbors++
        if(cells[xPos+1][yPos+1].alive)
            neighbors++

        //middle neighbors
        if(cells[xPos-1][yPos].alive)
            neighbors++
        if(cells[xPos+1][yPos].alive)
            neighbors++

        //bottom row
        if(cells[xPos - 1][yPos-1].alive)
            neighbors++
        if(cells[xPos][yPos-1].alive)
            neighbors++
        if(cells[xPos+1][yPos-1].alive)
            neighbors++

        return neighbors
    }

    fun rules()
    {

    }

    fun draw(drawer: Drawer, screenHeight: Int, screenWidth: Int) {
        var deltaX = screenWidth / numXCells
        var deltaY = screenHeight / numYCells

        var temp = cells as Array<Array<LifeCell>>

        drawer.fill = ColorRGBa.WHITE

        for(x in 0 until numXCells)
            for(y in 0 until numYCells)
            {
                if(temp[x][y].alive)
                    drawer.rectangle((x * deltaX).toDouble(), (y * deltaY).toDouble(), deltaX.toDouble(), deltaY.toDouble())
            }
    }
}
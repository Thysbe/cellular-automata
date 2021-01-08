package Cells

enum class CellType {
    LIFE, VOID, VIRUS
}

class LifeCell {


    var type = CellType.LIFE
    var alive = false
    var numStepsAlive = 0

}
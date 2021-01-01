import Cells.Grid
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.loadFont
import org.openrndr.draw.loadImage
import kotlin.math.cos
import kotlin.math.sin

fun main() = application {
    configure {
        width = 1920
        height = 1080
    }

    program {

        val grid = Grid(width/12, height/12)


        extend {
            grid.updateGrid()
            grid.draw(drawer, height, width)

            //drawer.circle(10.0, 10.0, 10.0)
        }
    }
}

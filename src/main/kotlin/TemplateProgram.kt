import Cells.Grid
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.depthBuffer
import org.openrndr.draw.loadFont
import org.openrndr.draw.loadImage
import org.openrndr.draw.renderTarget
import org.openrndr.extra.compositor.compose
import org.openrndr.extra.compositor.draw
import org.openrndr.extra.compositor.layer
import org.openrndr.extra.compositor.post
import org.openrndr.extra.fx.blur.Bloom
import org.openrndr.extra.fx.distort.FluidDistort
import org.openrndr.extra.gui.GUI
import org.openrndr.extra.gui.addTo
import org.openrndr.ffmpeg.ScreenRecorder
import org.openrndr.ffmpeg.VideoWriter
import kotlin.math.cos
import kotlin.math.sin

fun main() = application {
    configure {
        width = 1920
        height = 1080
    }

    program {

        //val videoWriter = VideoWriter.create().size(width, height).output("output.mp4").start()
        /*
        val videoTarget = renderTarget(width, height) {
            colorBuffer()
            depthBuffer()
        }
*/
        var frame = 0

        val grid = Grid(width/8, height/8)
        val gui = GUI()
        var count = 0

        var blendInc = 0.01

        val bloom = Bloom()
        var fluid = FluidDistort()

        fluid.blend = 0.0

        bloom.brightness = 0.9
        bloom.blendFactor = 0.9

        val c = compose{
            layer {
                draw {
                    grid.draw(drawer, height, width)
                }
                post(bloom).addTo(gui)
                post(fluid)
            }
        }
        //extend(gui)
        extend(ScreenRecorder())
        extend {


            count++
            if(count % 3 == 0)
                grid.updateGrid()

            fluid.blend += blendInc

            if(fluid.blend >= 1)
            {
                blendInc = -0.01
            }
            else if(fluid.blend <= 0.0)
            {
                blendInc = 0.01
            }


            //.draw(drawer)
            c.draw(drawer)
/*
            videoWriter.frame(videoTarget.colorBuffer(0))
            drawer.image(videoTarget.colorBuffer(0))
            frame++
            if (frame == 10000) {
                videoWriter.stop()
                application.exit()
            }*/

            if(frame == 10000) {
                application.exit()
            }


            //drawer.circle(10.0, 10.0, 10.0)
        }
    }
}

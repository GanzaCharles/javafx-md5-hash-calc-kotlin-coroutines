package sample

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class Main : Application() {

    @Throws(Exception::class)
    override fun start(primaryStage: Stage) {
        val loader = FXMLLoader(javaClass.getResource("/fxml/main.fxml"))

        primaryStage.scene = Scene(loader.load(), 544.0, 400.0)
        primaryStage.isResizable = false
        primaryStage.title = "MD5 File Has Calculator"
        primaryStage.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(Main::class.java, *args)
        }
    }
}

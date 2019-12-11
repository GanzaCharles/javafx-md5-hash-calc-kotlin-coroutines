package sample

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.ProgressIndicator
import javafx.scene.control.TextField
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import javafx.scene.input.TransferMode
import javafx.scene.layout.GridPane
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import md5.CheckSumCalculator
import java.io.File
import java.net.URL
import java.util.*

class Controller : Initializable {

    @FXML
    private lateinit var checkSumView: TextField
    @FXML
    private lateinit var copyBtn: Button
    @FXML
    private lateinit var progressIndicator: ProgressIndicator
    @FXML
    private lateinit var gridPane: GridPane
    private var checkSumCalculator = CheckSumCalculator()

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        checkSumView.textProperty().addListener { observable, oldText, newText ->
            copyBtn.isVisible = !newText.isNullOrEmpty()
        }
        gridPane.setOnDragOver {
            val dragEvent = it.dragboard

            if (dragEvent.hasFiles()) {
                it.acceptTransferModes(TransferMode.COPY)
            }
        }
        gridPane.setOnDragDropped {

            val dragEvent = it.dragboard
            var dragSuccessful = false

            if (dragEvent.hasFiles()) {

                var checkSum: String
                val file = dragEvent.files[0] //support only one file at a time, will support more in the future

                dragSuccessful = true
                progressIndicator.isVisible = true

                //calculate the checksum in background
                CoroutineScope(Dispatchers.Default).launch {
                    checkSum = calculateMD5ForFile(file)
                    progressIndicator.isVisible = false
                    checkSumView.text = checkSum
                }

            }

            it.isDropCompleted = dragSuccessful
            it.consume()
        }
    }

    @FXML
    fun copyHashText() {
        if (!checkSumView.text.isNullOrEmpty()) {
            setDataToClipboard(checkSumView.text)
        }
    }

    private fun <T> setDataToClipboard(value: T) {
        val clipboard = Clipboard.getSystemClipboard()
        val content = ClipboardContent()
        content.putString(value.toString())
        clipboard.setContent(content)
    }

    private fun calculateMD5ForFile(vararg file: File): String {
        return checkSumCalculator.checksum(file[0])
    }

}

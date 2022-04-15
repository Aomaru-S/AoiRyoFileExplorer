// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.io.File

@Composable
@Preview
fun App() {
    var path by remember { mutableStateOf(System.getProperty("user.home")) }
    var isDisplayHiddenObject by remember { mutableStateOf(true) }
    var objectList = getObjectList(System.getProperty("user.home"))!!

    MaterialTheme {
        Column {
            Row {
                TextField(
                    path,
                    onValueChange = {
                        println(it)
                        path = it
                        objectList = getObjectList(it) ?: return@TextField
                    }
                )
                Checkbox(
                    isDisplayHiddenObject,
                    onCheckedChange = {
                        isDisplayHiddenObject = it
                    }
                )
            }

            LazyColumn {
                itemsIndexed(objectList) { _, item ->
                    Row {
                        if (isDisplayHiddenObject || !item.isHidden) {
                            Text(item.name)
                        }
                    }
                }
            }
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "AoiRyoFileExplorer"
    ) {
        App()
    }
}

fun getObjectList(_path: String?): List<File>? {
    val objectList = mutableListOf<File>()
    _path ?: return null
    val file = File(_path)
    if (file.exists()) {
        val objectNameList = file.list() ?: throw NullPointerException()
        for (objectName in objectNameList) {
            objectList.add(File("${file.path}/$objectName"))
        }
        return objectList
    }
    return null
}

fun upCurrentDir(): String? {
    // TODO: 2022/04/15 return newPath or null
    return null
}
package io.mimsoft.file

import io.ktor.http.content.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object FileController {

    private const val PATH = "/var/www/data/dreamarch"
    private const val template = "yyyy-MM-dd-HH-mm-ss-SSS"

    const val IMAGE = "images"
    const val VIDEO = "videos"

    suspend fun uploadFile(multipart: MultiPartData, dir: String, type: String = IMAGE): String {
        var name = ""
        multipart.forEachPart { part ->
            if (part is PartData.FileItem) {
                if (part.originalFileName != null) {
                    val ext = File(part.originalFileName).extension
                    val parent = File("$PATH/$type/$dir/")
                    if (!parent.exists()) {
                        parent.mkdirs()
                    }
                    name = "$type/$dir/${SimpleDateFormat(template).format(Date())}.$ext"
                    val file = File("$PATH/$name")
                    part.streamProvider().use { its ->
                        file.outputStream().buffered().use {
                            its.copyTo(it)
                        }
                    }
                }
            }
            part.dispose()
        }
        return name
    }


    fun deleteFile(url: String): Boolean {
        val fd = File("$PATH/$url")

        if (fd.exists()) {
            fd.delete()
        }
        return true
    }
}

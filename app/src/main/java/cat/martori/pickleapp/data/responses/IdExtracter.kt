package cat.martori.pickleapp.data.responses

object IdExtracter {
    fun String.extractId() = kotlin.runCatching { split("/").last().toInt() }.getOrNull()
}
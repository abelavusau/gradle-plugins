package com.abelavusau.build.plugins.utils

import com.abelavusau.build.plugins.utils.P2Downloader
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.File

internal class P2DownloaderTest {
    val p2Downloader = P2Downloader()
    val p2Folder = File("p2")

    @Test
    fun downloadP2() {
        p2Downloader.downloadP2(
                p2Downloader.efxUrl,
            "p2",
            true
        )

        Assertions.assertEquals(p2Folder.exists(), true)
        p2Folder.deleteRecursively()
        Assertions.assertEquals(p2Folder.exists(), false)

        p2Downloader.downloadP2(
                p2Downloader.efxUrl,
            "p2",
            false
        )

        p2Downloader.downloadP2(
                p2Downloader.efxUrl,
            "p2",
            false
        )

        Assertions.assertEquals(p2Folder.exists(), true)
//        p2Folder.deleteRecursively()
//        Assertions.assertEquals(p2Folder.exists(), false)
    }
}
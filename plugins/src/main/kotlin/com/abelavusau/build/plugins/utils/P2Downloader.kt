package com.abelavusau.build.plugins.utils

import com.abelavusau.build.plugins.utils.p2.xml.P2RepositoryDescriptorParser
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels


class P2Downloader {

    companion object {
        const val P2_DESCRIPTOR = "artifacts.xml"
    }

    fun downloadP2(urlBase: String, p2Repo: String, force: Boolean = false) {
        val p2LocalRoot = File(p2Repo)

        if (p2LocalRoot.exists() && p2LocalRoot.isDirectory) {
            System.out.println("${p2LocalRoot.path} exists!")

            if (force) {
                println("force mode was selected. Re-downloading $urlBase")
                p2LocalRoot.deleteRecursively()
            } else if (p2LocalRoot.listFiles().size > 0) {
                println("Already downloaded. Skipping...")
                return
            }
        }

        if (p2LocalRoot.mkdirs()) {
            System.out.println("Folder ${p2LocalRoot.path} has been created")
        } else {
            throw RuntimeException("Unable to create ${p2LocalRoot.path}!")
        }

        var p2RemoteRoot = urlBase

        if (!p2RemoteRoot.endsWith("/")) {
            p2RemoteRoot += "/"
        }

        val p2DescriptorUrl = p2RemoteRoot + P2_DESCRIPTOR
        val p2RemotePlugins = p2RemoteRoot + "plugins/"
        val p2RemoteFeatures = p2RemoteRoot + "features/"
        val p2LocalPlugins = File(p2LocalRoot.path + "/plugins")
        val p2LocalFeatures = File(p2LocalRoot.path + "/features")

        p2LocalPlugins.mkdir()
        p2LocalFeatures.mkdir()

        downloadFile(p2DescriptorUrl, p2LocalRoot)

        val p2DescriptorFile = File(p2LocalRoot.path + "/" + P2_DESCRIPTOR)
        val p2RepositoryDescriptorParser = P2RepositoryDescriptorParser(p2DescriptorFile)

        val artifacts = p2RepositoryDescriptorParser.getOsgiBundles()

        artifacts.forEach {
            downloadFile(p2RemotePlugins + it.toString(), p2LocalPlugins)
        }
    }

    private fun downloadFile(uri: String, targetDir: File) {
        println("Downloading $uri")
        val url = URL(uri)
        val basename = uri.substringAfterLast("/")
        val readableByteChannel = Channels.newChannel(url.openStream())
        val pathToLocalFile = targetDir.path + "/" + basename
        val fileOutputStream = FileOutputStream(pathToLocalFile)
        val fileChannel = fileOutputStream.channel
        fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE)
        println("Downloaded to $pathToLocalFile")
    }
}
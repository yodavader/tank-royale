import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.Files

abstract class BaseTask : DefaultTask() {
    @Internal
    protected val cwd: Path = Paths.get(System.getProperty("user.dir"))
    @Internal
    protected val buildDir: Path = cwd.resolve("build")
    @Internal
    protected val libsDir: Path = buildDir.resolve("libs")

    protected fun createDir(path: Path) {
        if (!Files.exists(path)) {
            Files.createDirectory(path)
        }
    }

    protected fun deleteDir(path: Path) {
        if (Files.exists(path)) {
            Files.walk(path)
                .sorted(Comparator.reverseOrder())
                .map(({ obj: Path -> obj.toFile() }))
                .forEach(({ obj: File -> obj.delete() }))
        }
    }
}

abstract class Clean : BaseTask() {
    @TaskAction
    fun clean() {
        deleteDir(buildDir)
    }
}

abstract class JavaSampleBotsTask : BaseTask() {
    @TaskAction
    fun build() {
        createDir(buildDir)
        createDir(libsDir)

        Files.list(cwd).forEach { projectDir -> run {
            if (Files.isDirectory(projectDir) && isBotProjectDir(projectDir)) {
                copyBotJarArchive(projectDir)
                copyBotJsonFile(projectDir)
                createCmdFile(projectDir)
                createShFile(projectDir)
                createCommandFile(projectDir)
            }
        }}

        copyIcon()
    }

    private fun isBotProjectDir(dir: Path): Boolean {
        val filename = dir.fileName.toString()
        return !filename.startsWith(".") && filename != "build"
    }

    private fun copyBotJarArchive(projectDir: Path) {
        val jarFilename = getBotJarArchiveFilename(projectDir)
        if (jarFilename != null) {
            Files.copy(jarFilename, libsDir.resolve(jarFilename.fileName))
        }
    }

    private fun getBotJarArchiveFilename(projectDir: Path): Path? {
        val buildDir: Path = projectDir.resolve("build/libs")
        for (dir in Files.list(buildDir)) {
            if (dir.startsWith(projectDir)) {
                return buildDir.resolve(dir)
            }
        }
        System.err.println("Could not find jar archive in dir: $projectDir")
        return null
    }

    private fun copyBotJsonFile(projectDir: Path) {
        val filename = projectDir.fileName.toString() + ".json"
        val jsonFilePath = projectDir.resolve("src/main/resources/$filename")
        Files.copy(jsonFilePath, buildDir.resolve(filename))
    }

    private fun createCmdFile(projectDir: Path) {
        val filename = projectDir.fileName.toString() + ".cmd"
        val printWriter = object : java.io.PrintWriter(buildDir.resolve(filename).toFile()) {
            override fun println() {
                write("\r\n") // Windows Carriage Return + New-line
            }
        }

        printWriter.use {
            val jarFilename = getBotJarArchiveFilename(projectDir)!!.fileName
            val className = "dev.robocode.tankroyale.sample.bots." + projectDir.fileName.toString()
            it.println("java -cp libs/$jarFilename;libs/robocode-tankroyale-bot-api-0.9.8.jar $className")
            it.close()
        }
    }

    private fun createShFile(projectDir: Path) {
        val filename = projectDir.fileName.toString() + ".sh"
        val printWriter = object : java.io.PrintWriter(buildDir.resolve(filename).toFile()) {
            override fun println() {
                write("\n") // Unix New-line
            }
        }
        printWriter.use {
            it.println("#!/bin/sh")
            val jarFilename = getBotJarArchiveFilename(projectDir)!!.fileName
            val className = "dev.robocode.tankroyale.sample.bots." + projectDir.fileName.toString()
            it.println("java -cp libs/$jarFilename:libs/robocode-tankroyale-bot-api-0.9.8.jar $className")
            it.close()
        }
    }

    private fun createCommandFile(projectDir: Path) {
        val filename = projectDir.fileName.toString() + ".command"
        val printWriter = object : java.io.PrintWriter(buildDir.resolve(filename).toFile()) {
            override fun println() {
                write("\n") // OS-X New-line
            }
        }
        printWriter.use {
            it.println("#!/bin/sh")
            val jarFilename = getBotJarArchiveFilename(projectDir)!!.fileName
            val name = projectDir.fileName.toString()
            val className = "dev.robocode.tankroyale.sample.bots.$name"
            val xdockIconAndName = "-Xdock:icon=robocode.ico -Xdock:name=$name"
            it.println("java $xdockIconAndName -cp libs/$jarFilename:libs/robocode-tankroyale-bot-api-0.9.8.jar $className")
            it.close()
        }
    }

    private fun copyIcon() {
        Files.copy(cwd.resolve("../../gfx/Tank/Tank.ico"), buildDir.resolve("robocode.ico"))
    }
}

task<JavaSampleBotsTask>("copyBotsToArchiveDir") {
    dependsOn(":sample-bots:java:Corners:build")
    dependsOn(":sample-bots:java:Crazy:build")
    dependsOn(":sample-bots:java:Fire:build")
    dependsOn(":sample-bots:java:MyFirstBot:build")
    dependsOn(":sample-bots:java:RamFire:build")
    dependsOn(":sample-bots:java:SpinBot:build")
    dependsOn(":sample-bots:java:Target:build")
    dependsOn(":sample-bots:java:TrackFire:build")
    dependsOn(":sample-bots:java:Walls:build")
}

task<Copy>("copyBotApiJar") {
    dependsOn("copyBotsToArchiveDir")

    from(project(":bot-api:java").file("build/libs/robocode-tankroyale-bot-api-0.9.8.jar"))
    into(Paths.get(System.getProperty("user.dir")).resolve("build/libs"))
}

task<Clean>("clean")

tasks.register("build") {
    dependsOn("clean")
    dependsOn("copyBotApiJar")
}

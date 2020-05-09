package dev.robocode.tankroyale.bootstrap.commands

import dev.robocode.tankroyale.bootstrap.model.BotInfo
import dev.robocode.tankroyale.bootstrap.util.Env
import dev.robocode.tankroyale.bootstrap.util.OSUtil
import dev.robocode.tankroyale.bootstrap.util.OSUtil.OSType.MacOS
import dev.robocode.tankroyale.bootstrap.util.OSUtil.OSType.Windows
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.UnstableDefault
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Files.list
import java.nio.file.Path
import java.util.function.Predicate
import java.util.stream.Collectors.toList

@UnstableDefault
@ImplicitReflectionSerializer
class RunCommand(private val botPaths: List<Path>): Command(botPaths) {

    fun startBots(filenames: Array<String>): List<Process> {
        val processes = ArrayList<Process>()
        filenames.forEach { filename ->
            run {
                val process = startBot(filename)
                if (process != null) processes.add(process)
            }
        }
        return processes
    }

    private fun startBot(filename: String): Process? {
        try {
            val scriptPath = findOsScript(filename)
            if (scriptPath == null) {
                System.err.println("ERROR: No script found for the bot: $filename")
                return null
            }

            val command = scriptPath.toString()
            val commandLC = command.toLowerCase()

            val processBuilder = when {
                commandLC.endsWith(".bat") -> // handle Batch script
                    ProcessBuilder("cmd.exe", "/c \"$command\"")
                commandLC.endsWith(".ps1") -> // handle PowerShell script
                    ProcessBuilder("powershell.exe", "-ExecutionPolicy ByPass", "-File \"$command\"")
                commandLC.endsWith(".sh") -> // handle Bash Shell script
                    ProcessBuilder("bash.exe", "-c \"$command\"")
                else -> // handle regular command
                    ProcessBuilder(command)
            }

            val process = processBuilder.start()
            val env = processBuilder.environment()

            setEnvVars(env, getBotInfo(filename)!!)

            println("${process.pid()}:$filename")
            return process

        } catch (ex: IOException) {
            System.err.println("ERROR: ${ex.message}")
            return null
        }
    }

    private fun findOsScript(botName: String): Path? = when (OSUtil.getOsType()) {
        Windows -> findWindowsScript(botName)
        MacOS -> findMacOsScript(botName)
        else -> findFirstUnixScript(botName)
    }

    private fun findWindowsScript(botName: String): Path? {
        var path: Path?
        botPaths.forEach { dirPath ->
            run {
                path = resolveFullBotPath(dirPath, "$botName.bat")
                if (path != null) return path

                path = resolveFullBotPath(dirPath, "$botName.cmd")
                if (path != null) return path

                path = resolveFullBotPath(dirPath, "$botName.psi")
                if (path != null) return path
            }
        }
        return findFirstUnixScript(botName)
    }

    private fun findMacOsScript(botName: String): Path? {
        var path: Path?
        botPaths.forEach { dirPath ->
            run {
                path = resolveFullBotPath(dirPath, "$botName.command")
                if (path != null) return path
            }
        }
        return findFirstUnixScript(botName)
    }

    private fun findFirstUnixScript(botName: String): Path? {
        var path: Path?
        botPaths.forEach { dirPath ->
            run {
                path = resolveFullBotPath(dirPath, "$botName.sh")
                if (path != null) return path
            }
        }

        // Look for any file with no file extension or where the file containing the '#!' characters, i.e. a script
        botPaths.forEach { dirPath ->
            run {
                list(dirPath).filter(IsBotFile(botName)).collect(toList()).forEach { path ->
                    if (path.fileName.toString().toLowerCase() == botName.toLowerCase())
                        return path
                    if (readFirstLine(dirPath.resolve(path)).trim().startsWith("#!"))
                        return path
                }
            }
        }
        return null // No path found
    }

    companion object {
        private fun readFirstLine(path: Path): String {
            return Files.newInputStream(path).bufferedReader().readLine() ?: ""
        }

        private fun setEnvVars(envMap: MutableMap<String,String>, botInfo: BotInfo) {
            setEnvVar(envMap, Env.SERVER_URL, System.getProperty("server.url"))
            setEnvVar(envMap, Env.BOT_NAME, botInfo.name)
            setEnvVar(envMap, Env.BOT_VERSION, botInfo.version)
            setEnvVar(envMap, Env.BOT_AUTHOR, botInfo.author)
            setEnvVar(envMap, Env.BOT_DESCRIPTION, botInfo.description)
            setEnvVar(envMap, Env.BOT_URL, botInfo.url)
            setEnvVar(envMap, Env.BOT_COUNTRY_CODE, botInfo.countryCode)
            setEnvVar(envMap, Env.BOT_GAME_TYPES, botInfo.gameTypes.joinToString())
            setEnvVar(envMap, Env.BOT_PLATFORM, botInfo.platform)
            setEnvVar(envMap, Env.BOT_PROG_LANG, botInfo.programmingLang)
        }

        private fun setEnvVar(envMap: MutableMap<String,String>, env: Env, value: Any?) {
            if (value != null) envMap[env.name] = value.toString()
        }
    }
}

internal class IsBotFile(private val botName: String) : Predicate<Path> {

    override fun test(path: Path): Boolean {
        val filenameLC = path.fileName.toString().toLowerCase()
        val botNameLC = botName.toLowerCase()

        return filenameLC == botNameLC || filenameLC.startsWith("$botNameLC.")
    }
}
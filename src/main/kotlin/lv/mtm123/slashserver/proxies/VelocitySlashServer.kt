package lv.mtm123.slashserver.proxies

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.proxy.ProxyServer
import lv.mtm123.slashserver.cmd.VelocityNavigationCommand
import lv.mtm123.slashserver.util.Config
import java.io.File
import java.util.logging.Logger

@Plugin(
    id = "slashserver",
    name = "SlashServer",
    version = "@version@",
    description = "Allows to use /<servername>",
    authors = ["MTM123"]
)
class VelocitySlashServer @Inject constructor(private val proxy: ProxyServer) {

    @Subscribe
    fun onInit(event: ProxyInitializeEvent) {

        val mainDir = File("./plugins/SlashServer/")
        if (!mainDir.exists()) {
            mainDir.mkdirs()
        }

        Config.loadConfig(mainDir).servers.forEach { s ->
            val meta = proxy.commandManager.metaBuilder(s.server)
                .aliases(*s.commands.toTypedArray())
                .build()
            proxy.commandManager.register(
                meta,
                VelocityNavigationCommand(proxy, s.server)
            )
        }

    }

}

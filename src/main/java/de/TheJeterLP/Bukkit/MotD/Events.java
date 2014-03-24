package de.TheJeterLP.Bukkit.MotD;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * @author TheJeterLP
 */
public class Events implements Listener {

        private final File faviconFolder = new File(Main.getInstance().getDataFolder().getAbsolutePath() + File.separator + "Favicons");

        public Events() {
                faviconFolder.mkdirs();
        }

        @EventHandler(priority = 1)
        public void onPing(final ProxyPingEvent e) throws IOException {
                MotD motd = new MotD(Config.MOTD_LINE_1.getString(), Config.MOTD_LINE_2.getString(), e.getConnection().getName());
                File[] icons = faviconFolder.listFiles();
                File rnd = icons[new Random().nextInt(icons.length)];
                Favicon ico = new Favicon(rnd);
                ServerPing ping = new ServerPing(e.getResponse().getVersion(), e.getResponse().getPlayers(), motd.getMotd(), ico.getIcon());
                e.setResponse(ping);
        }
}

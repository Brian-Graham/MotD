package de.TheJeterLP.Bukkit.MotD;

import de.TheJeterLP.JeterConfig.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;

/**
 * @author TheJeterLP
 */
public enum Config {

        MOTD_LINE_1("Motd.Line1", "&a[]====&9&lVirus&4&lCraft&5&lNetwork&a====[]&r"),
        MOTD_LINE_2("Motd.Line2", "&6Welcome %player, you have %stars stars."),
        MYSQL_HOST("SQL.MySQLHost", "127.0.0.1"),
        MYSQL_PORT("SQL.MySQLPort", 3306),
        MYSQL_DATABASE("SQL.MySQLDatabase", "tests"),
        MYSQL_USER("SQL.MySQLUser", "root"),
        MYSQL_PASSWORD("SQL.MySQLPassword", "");

        private final Object value;
        private final String path;
        private static YamlConfiguration cfg;
        private static final File f = new File(Main.getInstance().getDataFolder(), "config.yml");

        private Config(String path, Object val) {
                this.path = path;
                this.value = val;
        }

        public String getPath() {
                return path;
        }

        public Object getDefaultValue() {
                return value;
        }

        public String getString() {
                String s = cfg.getString(path);
                return s.replaceAll("&((?i)[0-9a-fk-or])", "§$1");
        }

        public static void load() {
                Main.getInstance().getDataFolder().mkdirs();
                reload(false);
                for (Config c : values()) {
                        if (!cfg.contains(c.getPath())) {
                                c.set(c.getDefaultValue());
                        }
                }
                try {
                        cfg.save(f);
                } catch (IOException ex) {
                        ex.printStackTrace();
                }
        }

        public void set(Object value) {
                cfg.set(path, value);
                try {
                        cfg.save(f);
                } catch (IOException ex) {
                        ex.printStackTrace();
                }
                reload(false);
        }

        public static void reload(boolean complete) {
                if (!complete) {
                        cfg = YamlConfiguration.loadConfiguration(f);
                        return;
                }
                load();
        }
}

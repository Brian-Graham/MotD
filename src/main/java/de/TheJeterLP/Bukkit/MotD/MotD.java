package de.TheJeterLP.Bukkit.MotD;

/**
 * @author TheJeterLP
 */
public class MotD {

        private String motd;

        public MotD(String line1, String line2, String pName) {
                this.motd = (line1 + "\n" + line2).replaceAll("&((?i)[0-9a-fk-or])", "§$1");
                this.motd = this.motd.replace("%stars", String.valueOf(Database.getStars(pName)));
                this.motd = this.motd.replace("%player", pName);
        }

        public String getMotd() {
                return motd;
        }

}

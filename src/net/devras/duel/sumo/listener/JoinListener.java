package net.devras.duel.sumo.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.devras.duel.sumo.ScoreHelper;
import net.devras.duel.sumo.Sumo;

public class JoinListener implements Listener {

	public static int i = 0;
	public static boolean playing = false;
	public static Player p1, p2;

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		event.setJoinMessage("");

		p.getInventory().clear();
		p.setGameMode(GameMode.SURVIVAL);
		p.setFoodLevel(20);

		Location loc = Bukkit.getWorlds().get(0).getSpawnLocation();

		if (p1 == null) {
			p1 = p;
		}else if (p2 == null) {
			p2 = p;
		}

		p.teleport(loc);

		if (playing) {
			p.setGameMode(GameMode.SPECTATOR);
		}

		Sumo.GameStart(p1, p2);

	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player p = event.getPlayer();

		event.setQuitMessage("");

		if (p1 != null && p1.getUniqueId().equals(p.getUniqueId())) {
			p1 = null;
			Sumo.GameOver(p2);
		}
		if (p2 != null && p2.getUniqueId().equals(p.getUniqueId())) {
			p2 = null;
			Sumo.GameOver(p1);
		}

		if (ScoreHelper.hasScore(p)) {
			ScoreHelper.removeScore(p);
		}

	}

}

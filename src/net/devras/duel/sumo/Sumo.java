package net.devras.duel.sumo;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import net.devras.duel.sumo.listener.BuildListener;
import net.devras.duel.sumo.listener.CommonListener;
import net.devras.duel.sumo.listener.JoinListener;
import net.devras.duel.sumo.listener.MoveListener;

public class Sumo extends JavaPlugin {

	private static Sumo Instance;

	@Override
	public void onDisable() {
		super.onDisable();
	}

	@Override
	public void onEnable() {
		Instance = this;

		Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
		Bukkit.getPluginManager().registerEvents(new MoveListener(), this);
		Bukkit.getPluginManager().registerEvents(new BuildListener(), this);
		Bukkit.getPluginManager().registerEvents(new CommonListener(), this);
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

		Bukkit.getScheduler().runTaskTimer(this, new MainTask(), 0L, 20L);

		super.onEnable();
	}

	@SuppressWarnings("deprecation")
	public static void GameStart(Player p1, Player p2) {

		if (p1 == null || p2 == null) {
			return;
		}

		if (JoinListener.playing) {
			return;
		}

		Location loc = Bukkit.getWorlds().get(0).getSpawnLocation();
		Location l1 = loc.add(5, 0, 0);
		Location l2 = loc.add(-5, 0, 0);

		p1.teleport(l1);
		p2.teleport(l2);

		p1.sendTitle("§eGameStart", "");
		p2.sendTitle("§eGameStart", "");

		JoinListener.playing = true;

	}

	@SuppressWarnings("deprecation")
	public static void GameOver(Player win) {
		if (!JoinListener.playing) {
			return;
		}
		JoinListener.playing = false;

		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendTitle("§6§lVICTORY", win.getName() + " has won!");
			sendLobby(p);
		}

	}

	public static void sendLobby(Player p) {
		try {
			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			out.writeUTF("Connect");
			out.writeUTF("lobby");
			p.sendPluginMessage(Instance, "BungeeCord", out.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

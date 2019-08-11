package net.devras.duel.sumo;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import net.devras.duel.sumo.listener.JoinListener;

public class MainTask implements Runnable {

	@Override
	public void run() {
		try {

			if (!JoinListener.playing) {
				if (JoinListener.p1 != null && JoinListener.p2 != null) {
					Sumo.GameStart(JoinListener.p1, JoinListener.p2);
				}
			}

			String p1 = JoinListener.p1 != null ? JoinListener.p1.getName() : "Wait";
			String p2 = JoinListener.p2 != null ? JoinListener.p2.getName() : "Wait";

			for (Player p : Bukkit.getOnlinePlayers()) {
				ScoreHelper helper;
				if (ScoreHelper.hasScore(p)) {
					helper = ScoreHelper.getByPlayer(p);
				}else {
					helper = ScoreHelper.createScore(p);
				}

				helper.setTitle("§eSUMO DUEL");
				helper.setSlot(7, "");
				helper.setSlot(6, "§eRubec");
				helper.setSlot(5, " §c" + p1);
				helper.setSlot(4, "§eLetas");
				helper.setSlot(3, " §c" + p2);
				helper.setSlot(2, "");
				helper.setSlot(1, "§emc.devras.info");

			}

			if (JoinListener.playing) {
				if (JoinListener.p1 != null) {
					checkSum(JoinListener.p1);
				}
				if (JoinListener.p2 != null) {
					checkSum(JoinListener.p2);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void checkSum(Player p) {
		if (p.getLocation().getBlock().getType().equals(Material.STATIONARY_WATER) || p.getLocation().getBlock().getType().equals(Material.WATER)) {
			if (JoinListener.playing) {

				if (JoinListener.p1.getUniqueId().equals(p.getUniqueId())) {
					Sumo.GameOver(JoinListener.p2);
				}
				if (JoinListener.p2.getUniqueId().equals(p.getUniqueId())) {
					Sumo.GameOver(JoinListener.p1);
				}
			}
		}
	}

}

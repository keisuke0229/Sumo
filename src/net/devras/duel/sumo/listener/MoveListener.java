package net.devras.duel.sumo.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.devras.duel.sumo.MainTask;

public class MoveListener implements Listener {

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		MainTask.checkSum(p);
	}
}

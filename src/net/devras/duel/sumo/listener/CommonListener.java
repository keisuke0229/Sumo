package net.devras.duel.sumo.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class CommonListener implements Listener {


	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			if (JoinListener.playing) {
				event.setDamage(0);
			}else {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onFood(FoodLevelChangeEvent event) {
		event.setFoodLevel(20);
	}
}

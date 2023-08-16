package org.karrotos.me.simpleindicator.Listeners;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.text.DecimalFormat;
import java.util.Objects;

public class Indicator implements Listener {
    private final Plugin plugin;

    public Indicator(Plugin plugin) {
        this.plugin = plugin;
    }

    private double getRandomOffset() {
        double getRandom = Math.random();
        if (Math.random() > 0.5) getRandom *= -1;
        return getRandom;
    }
    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof LivingEntity entity) {
            Objects.requireNonNull(Bukkit.getWorld(entity.getWorld().getUID())).spawn(entity.getLocation().clone().add(getRandomOffset(), 1, getRandomOffset()), ArmorStand.class, armorStand -> {
                armorStand.setSmall(true);
                armorStand.setVisible(false);
                armorStand.setCustomNameVisible(true);
                armorStand.setMarker(true);
                armorStand.customName(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(plugin.getConfig().getString("splashText")).replace("%damage%", new DecimalFormat(Objects.requireNonNull(plugin.getConfig().getString("damageFormat"))).format(e.getDamage()))));
                BukkitTask bukkitTask = new BukkitRunnable() {
                    @Override
                    public void run() {
                        armorStand.remove();
                    }
                }.runTaskLater(plugin, plugin.getConfig().getLong("delayToRemove") * 20L);
            });
        }
    }
}

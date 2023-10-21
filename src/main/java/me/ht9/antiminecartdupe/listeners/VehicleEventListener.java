package me.ht9.antiminecartdupe.listeners;

import net.minecraft.server.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.vehicle.VehicleListener;
import org.bukkit.event.vehicle.VehicleMoveEvent;

public class VehicleEventListener extends VehicleListener
{
    @Override
    public void onVehicleMove(VehicleMoveEvent event)
    {
        Entity entity = this.getEntityById(event.getVehicle().getEntityId());
        CraftPlayer craftPlayer = (CraftPlayer) PacketEventListener.player;
        if (entity == PacketEventListener.minecart && PacketEventListener.listening)
        {
            if (this.isEntityFarFromPlayer(entity, PacketEventListener.player))
            {
                if (craftPlayer.getHandle().activeContainer instanceof ContainerChest)
                {
                    if (craftPlayer.getHandle().activeContainer.windowId == PacketEventListener.windowId)
                    {
                        (PacketEventListener.player).sendPacket(PacketEventListener.player, new Packet101CloseWindow(craftPlayer.getHandle().activeContainer.windowId));
                    }
                }
            }
        }
    }

    public Entity getEntityById(int entityId)
    {
        return Bukkit.getWorlds().stream()
                .flatMap(world -> world.getEntities().stream())
                .filter(entity -> entity.getEntityId() == entityId)
                .findFirst()
                .orElse(null);
    }

    public boolean isEntityFarFromPlayer(Entity entity, Player player)
    {
        double distanceSquared = entity.getLocation().distanceSquared(player.getLocation());
        return Math.sqrt(distanceSquared) > 10.0;
    }
}

package me.ht9.antiminecartdupe;

import me.ht9.antiminecartdupe.listeners.PacketEventListener;
import me.ht9.antiminecartdupe.listeners.VehicleEventListener;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiMinecartDupe extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        PacketEventListener playerListener = new PacketEventListener(this);
        getServer().getPluginManager().registerEvents(playerListener, this);

        VehicleEventListener vehicleListener = new VehicleEventListener();
        getServer().getPluginManager().registerEvent(Event.Type.VEHICLE_MOVE, vehicleListener, Event.Priority.Monitor, this);
    }

    @Override
    public void onDisable() {}
}

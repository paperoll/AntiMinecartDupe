package me.ht9.antiminecartdupe.listeners;

import com.legacyminecraft.poseidon.event.PlayerReceivePacketEvent;
import com.legacyminecraft.poseidon.event.PlayerSendPacketEvent;
import com.legacyminecraft.poseidon.event.PoseidonCustomListener;
import me.ht9.antiminecartdupe.AntiMinecartDupe;
import net.minecraft.server.Packet100OpenWindow;
import net.minecraft.server.Packet101CloseWindow;
import net.minecraft.server.Packet7UseEntity;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftStorageMinecart;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;

public class PacketEventListener implements PoseidonCustomListener
{
    private final AntiMinecartDupe plugin;

    public PacketEventListener(AntiMinecartDupe plugin)
    {
        this.plugin = plugin;
    }

    public static Entity minecart;
    public static Player player;
    public static int windowId;
    public static boolean listening = false;

    @EventHandler(priority = Event.Priority.Monitor)
    public void onSendPacket(PlayerSendPacketEvent event)
    {
        if (event.isCancelled()) return;

        if (event.getPacket() instanceof Packet100OpenWindow)
        {
            Packet100OpenWindow packet = (Packet100OpenWindow) event.getPacket();
            if (packet.c.equals("Minecart"))
            {
                windowId = packet.a;
                listening = true;
            }
        }
    }

    @EventHandler(priority = Event.Priority.Monitor)
    public void onPacket(PlayerReceivePacketEvent event)
    {
        if (event.isCancelled()) return;

        if (event.getPacket() instanceof Packet7UseEntity)
        {
            Packet7UseEntity packet = (Packet7UseEntity) event.getPacket();
            if (packet.c == 0)
            {
                if (this.getEntityById(packet.target) instanceof CraftStorageMinecart)
                {
                    minecart = this.getEntityById(packet.target);
                    player = plugin.getServer().getPlayer(event.getUsername());
                }
            }
        } else if (event.getPacket() instanceof Packet101CloseWindow)
        {
            Packet101CloseWindow packet = (Packet101CloseWindow) event.getPacket();
            if (listening && packet.a == windowId)
            {
                listening = false;
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
}

package me.towercraft.rolles.minigame.deadrun.blockbelow;

import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.ArrayList;

@RequiredArgsConstructor
public class BlockBelow {

    private final Player player;

    public ArrayList<Block> getBlocks() {
        ArrayList<Block> blocksBelow = new ArrayList<>();
        Location location = player.getLocation();
        double x = location.getX();
        double z = location.getZ();
        World world = player.getWorld();
        double yBelow = player.getLocation().getBlock().getRelative(BlockFace.DOWN).getY();
        Block northEast = new Location(world, x + 0.3, yBelow, z - 0.3).getBlock();
        Block northWest = new Location(world, x - 0.3, yBelow, z - 0.3).getBlock();
        Block southEast = new Location(world, x + 0.3, yBelow, z + 0.3).getBlock();
        Block southWest = new Location(world, x - 0.3, yBelow, z + 0.3).getBlock();
        Block[] blocks = {northEast, northWest, southEast, southWest};
        for (Block block : blocks) {
            if (!blocksBelow.isEmpty()) {
                boolean duplicateExists = false;
                for (int i = 0; i < blocksBelow.size(); i++) {
                    if (blocksBelow.get(i).equals(block)) {
                        duplicateExists = true;
                    }
                }
                if (!duplicateExists) {
                    blocksBelow.add(block);
                }
            } else {
                blocksBelow.add(block);
            }
        }
        return blocksBelow;
    }
}

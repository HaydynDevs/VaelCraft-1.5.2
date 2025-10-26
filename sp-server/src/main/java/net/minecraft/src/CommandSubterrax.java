package net.minecraft.src;

public class CommandSubterrax extends CommandBase {
    public String getCommandName() {
        return "subterrax";
    }

    // Allow all players to run the command by default; change to 2 if you want ops-only
    public int getRequiredPermissionLevel() {
        return 0;
    }

    public String getCommandUsage(ICommandSender sender) {
        return "/subterrax - summon the Subterrax boss at your location";
    }

    public void processCommand(ICommandSender sender, String[] args) {
        // If sender is not a player, require a player argument or fail
        EntityPlayerMP player;

        try {
            player = getCommandSenderAsPlayer(sender);
        } catch (PlayerNotFoundException e) {
            // Not a player: try to resolve a player from args if provided
            if (args.length > 0) {
                player = func_82359_c(sender, args[0]);
            } else {
                throw new PlayerNotFoundException("You must specify a player when running this command from the console.");
            }
        }

        World world = player.worldObj;

        // Create and position the boss at the player's location
        EntitySubterrax boss = new EntitySubterrax(world);
        boss.setLocationAndAngles(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch);

        if (world.spawnEntityInWorld(boss)) {
            sender.sendChatToPlayer("Subterrax has been summoned.");
            notifyAdmins(sender, "commands.subterrax.success", new Object[] { player.getEntityName() });
        } else {
            sender.sendChatToPlayer("Failed to summon Subterrax here (chunk not loaded or invalid location).");
        }
    }
}

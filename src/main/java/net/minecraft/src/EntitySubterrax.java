package net.minecraft.src;

import java.util.List;

/**
 * A custom boss entity "Subterrax".
 * - 200 HP (boss)
 * - Spawns only at night
 * - Plays a spawn sound when it appears
 * - Uses the player/zombie (char) model/texture
 */
public class EntitySubterrax extends EntityMob {
	public EntitySubterrax() {
		super();
		this.moveSpeed = 0.23F;
		// Use player dimensions so it looks like the player/zombie model
		this.setSize(0.6F, 1.8F);
		this.entityType = "humanoid";
		// Make it more rewarding as a boss
		this.experienceValue = 500;
	}

	// Server/EntityList uses constructor with World parameter when creating instances
	public EntitySubterrax(World world) {
		super();
		// some build setups create entities via a (World) constructor but the client-side
		// EntityMob has no super(World) constructor. Set the world explicitly.
		this.setWorld(world);
		this.moveSpeed = 0.23F;
		this.setSize(0.6F, 1.8F);
		this.entityType = "humanoid";
		this.experienceValue = 500;
	}

	/**
	 * Boss health
	 */
	public int getMaxHealth() {
		return 200;
	}

	/**
	 * Enable AI similar to other mobs
	 */
	protected boolean isAIEnabled() {
		return true;
	}

	/**
	 * Only allow spawning at night (world.isDaytime() == false)
	 */
	public boolean getCanSpawnHere() {
		return !this.worldObj.isDaytime() && super.getCanSpawnHere();
	}

	/**
	 * Play a spawn sound on the first tick after spawning.
	 */
	public void onLivingUpdate() {
		super.onLivingUpdate();

		if (this.ticksExisted == 1) {
			// Use a custom sound name; if not present, fallbacks (or add the sound) may be needed.
			this.playSound("mob.subterrax.spawn", 1.0F, 1.0F);

			// Broadcast a server-side chat message to nearby players when Subterrax spawns.
			if (!this.worldObj.isRemote) {
				List nearby = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class,
						AxisAlignedBB.getAABBPool().getAABB(this.posX, this.posY, this.posZ, this.posX, this.posY, this.posZ)
								.expand(50.0D, 20.0D, 50.0D));

				for (int i = 0; i < nearby.size(); ++i) {
					EntityPlayer player = (EntityPlayer) nearby.get(i);
					player.addChatMessage("Subterrax has spawned!");
				}
			}
		}
	}

	protected String getLivingSound() {
		// reuse zombie living sound for a humanoid boss
		return "mob.zombie.say";
	}

	protected String getHurtSound() {
		return "mob.zombie.hurt";
	}

	protected String getDeathSound() {
		return "mob.zombie.death";
	}

	/**
	 * Use the default player texture so it appears like a player/zombie model.
	 */
	public String getTexture() {
		return "/mob/char.png";
	}

	/**
	 * Stronger attack for a boss
	 */
	public int getAttackStrength(Entity par1Entity) {
		int base = 10;
		ItemStack held = this.getHeldItem();
		if (held != null) {
			base += held.getDamageVsEntity(this);
		}
		return base;
	}

	protected void dropFewItems(boolean par1, int par2) {
		// optional: drop some rare loot; keep empty for now or add drops later
	}
}


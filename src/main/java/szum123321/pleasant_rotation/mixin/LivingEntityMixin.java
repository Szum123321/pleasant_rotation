package szum123321.pleasant_rotation.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import szum123321.pleasant_rotation.YawVelocity;

@Mixin(PlayerEntity.class)
public abstract class LivingEntityMixin extends LivingEntity implements YawVelocity {
	private float rotationLeft;

	protected LivingEntityMixin(EntityType<? extends LivingEntity> type, World world) {
		super(type, world);
	}

	@Override
	public void addSmoothlyYaw(float delta) {
		this.rotationLeft += delta;
	}

	@Environment(EnvType.CLIENT)
	@Inject(method = "tick", at = @At("RETURN"))
	public void updateYaw(CallbackInfo ci) {
		if (Math.abs(this.rotationLeft) > 1.0F) {
			this.yaw += (this.rotationLeft * 0.75F);
			this.rotationLeft -= (this.rotationLeft * 0.75F);
		}
	}
}

package szum123321.pleasant_rotation.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import szum123321.pleasant_rotation.YawVelocity;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements YawVelocity {
	private float rotationLeft;

	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Override
	public void addSmoothlyYaw(float delta) {
		this.rotationLeft += delta;
	}

	@Environment(EnvType.SERVER)
	@Inject(method = "tick", at = @At("RETURN"))
	public void updateYaw(CallbackInfo ci) {
		if(Math.abs(this.rotationLeft) > 1.0F) {
			this.yaw += (this.rotationLeft * 0.8F);
			this.rotationLeft -= (this.rotationLeft * 0.8F);
		}
	}
}

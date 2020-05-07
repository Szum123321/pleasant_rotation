package szum123321.pleasant_rotation.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import szum123321.pleasant_rotation.YawVelocity;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements YawVelocity {
	@Shadow private float movementSpeed;
	private float rotationLeft;

	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Override
	public void addSmoothlyYaw(float delta) {
		rotationLeft += delta;
	}

	@Inject(method = "tickMovement", at = @At("RETURN"))
	public void updateYaw(CallbackInfo ci) {
		//System.out.println(this.yaw);
		float v = (float) Math.sqrt(Math.pow(this.getVelocity().x, 2) + Math.pow(this.getVelocity().y, 2) + Math.pow(this.getVelocity().z, 2));
		v *= 5;
		if(Math.abs(rotationLeft) > v) {
			this.yaw = (this.yaw + (rotationLeft * v)) % 360.0F;
			rotationLeft -= rotationLeft * v;
		}
	}
}

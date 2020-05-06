package szum123321.pleasant_rotation.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.MinecartEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Entity.class)
public class EntityMixin {
	@Shadow public float yaw;

	@Inject(method = "setRotation", at = @At("HEAD"))
	public void updatePassengersRotation(float yaw, float pitch, CallbackInfo ci) {
		if((Object)this instanceof MinecartEntity) {
			MinecartEntity minecartEntity = (MinecartEntity)(Object)this;

			List<Entity> passengers = minecartEntity.getPassengerList();

			if(!passengers.isEmpty()) {
				float deltaYaw = yaw - this.yaw;
				passengers.forEach(e -> e.yaw += deltaYaw);

			}
		}
	}
}

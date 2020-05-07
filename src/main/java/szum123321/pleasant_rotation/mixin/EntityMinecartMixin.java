/*
	Don't you hate when minecart rotates but you do not?
    Copyright (C) 2020  Szum123321

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package szum123321.pleasant_rotation.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import szum123321.pleasant_rotation.YawVelocity;

@Mixin(AbstractMinecartEntity.class)
public abstract class EntityMinecartMixin extends Entity {
	public EntityMinecartMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Inject(method = "tick", at = @At("RETURN"))
	public void updatePassengersRotation(CallbackInfo ci) {
		if((Object)this instanceof MinecartEntity) {
			MinecartEntity me = (MinecartEntity)(Object)this;
			float deltaYaw = this.yaw - this.prevYaw;

			me.getPassengerList()
					.stream()
					.filter(e -> e instanceof PlayerEntity)
					.forEach(e -> ((YawVelocity) e).addSmoothlyYaw(deltaYaw));
		}
	}

/*	@Shadow public float prevYaw;

	@Environment(EnvType.CLIENT)
	@Inject(method = "setRotation", at = @At("HEAD"))
	public void updatePassengersRotation(float yaw, float pitch, CallbackInfo ci) {
		if((Object)this instanceof MinecartEntity) {
			MinecartEntity minecartEntity = (MinecartEntity)(Object)this;

			if(minecartEntity.hasPassengers()){
				float deltaYaw = yaw - this.prevYaw;

				minecartEntity.getPassengerList().forEach(e -> {
					if(e instanceof ClientPlayerEntity){

					}
				});
			}
		}
	}

 */
}

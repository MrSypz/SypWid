package sypztep.sypwid.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(PlayerEntity.class) @Environment(EnvType.CLIENT)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    @Unique
    private Vec3d lastPos;
    @Unique
    public boolean isSonic = false;

    @Inject(at = @At("HEAD"), method = "tickMovement")
    public void aiStep(CallbackInfo info) {
        lastPos = this.getPos();
    }
    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo info) {
        if(!this.getWorld().isClient) return;
        if(!this.isFallFlying()) return;

        if(lastPos == null) lastPos = this.getPos();
        if(!isSonic && getSpeed() > 35){
            explode();
            isSonic = true;
        }
        if(getSpeed() < 30)
            isSonic = false;
    }

    @Unique
    public double getSpeed(){
        return this.getLastPos().distanceTo(getPos()) * 20;
    }

    @Unique
    private void explode(){
        World world = this.getWorld();
        world.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.getX(), this.getY(), this.getZ(), 1.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 1.0D, 0.0D, 0.0D);
        world.playSound(this,this.getBlockPos(),SoundEvents.ENTITY_GENERIC_EXPLODE.value(),SoundCategory.PLAYERS,4.0f,1);
    }

    @Unique
    public Vec3d getLastPos() {
        return lastPos;
    }
}

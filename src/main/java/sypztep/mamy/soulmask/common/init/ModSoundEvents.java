package sypztep.mamy.soulmask.common.init;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import sypztep.mamy.soulmask.common.SoulMaskMod;

import java.util.LinkedHashMap;
import java.util.Map;

public interface ModSoundEvents {
    Map<SoundEvent, Identifier> SOUND_EVENTS = new LinkedHashMap();
    //Sound
    SoundEvent ENTITY_GENERIC_CHARGE2 = createSoundEvent("entity.generic.charge2");
    static void init() {
        SOUND_EVENTS.keySet().forEach((soundEvent) -> {
            Registry.register(Registries.SOUND_EVENT, SOUND_EVENTS.get(soundEvent), soundEvent);
        });
    }
    private static SoundEvent createSoundEvent(String path) {
        SoundEvent soundEvent = SoundEvent.of(SoulMaskMod.id(path));
        SOUND_EVENTS.put(soundEvent, SoulMaskMod.id(path));
        return soundEvent;
    }
}

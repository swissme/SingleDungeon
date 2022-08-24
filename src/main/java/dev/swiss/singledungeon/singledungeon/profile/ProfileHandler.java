package dev.swiss.singledungeon.singledungeon.profile;

import lombok.Getter;

import java.util.HashMap;
import java.util.UUID;

public class ProfileHandler {

    @Getter
    private static HashMap<UUID, Profile> profiles;

    public ProfileHandler() {
        profiles = new HashMap<>();
    }

    public static Profile getProfileByUUID(UUID uuid) {
        return profiles.get(uuid);
    }

}

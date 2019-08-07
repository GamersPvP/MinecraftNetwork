package me.riguron.game.setup.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import me.riguron.game.setup.MapSetup;
import me.riguron.game.setup.SoloMapSetup;

public class SoloSetupModule extends AbstractModule {

    @Provides
    @Singleton
    public MapSetup soloMapSetup() {
        return new SoloMapSetup();
    }

}

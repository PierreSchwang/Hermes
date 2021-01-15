package de.pierreschwang.hermes;

import de.pierreschwang.hermes.player.HermesPlayer;
import de.pierreschwang.hermes.player.HermesPlayerFactory;

public abstract class SimpleHermesPlugin extends HermesPlugin<HermesPlayer> {

    @Override
    public HermesPlayerFactory<HermesPlayer> getPlayerFactory() {
        return HermesPlayer::new;
    }

}

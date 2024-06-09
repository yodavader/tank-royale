package dev.robocode.tankroyale.botapi.internal;

import dev.robocode.tankroyale.botapi.BotInfo;
import dev.robocode.tankroyale.botapi.mapper.InitialPositionMapper;
import dev.robocode.tankroyale.schema.BotHandshake;
import dev.robocode.tankroyale.schema.Message.Type;

import java.util.ArrayList;

/**
 * Utility class used for creating bot handshakes.
 */
final class BotHandshakeFactory {

    // Hide constructor as this class is a utility class used for static access only
    private BotHandshakeFactory() {
    }

    static BotHandshake create(String sessionId, BotInfo botInfo, boolean isDroid, String secret) {
        BotHandshake handshake = new BotHandshake();
        handshake.setSessionId(sessionId);
        handshake.setType(Type.BOT_HANDSHAKE);
        handshake.setName(botInfo.getName());
        handshake.setVersion(botInfo.getVersion());
        handshake.setAuthors(new ArrayList<>(botInfo.getAuthors()));
        handshake.setDescription(botInfo.getDescription());
        handshake.setHomepage(botInfo.getHomepage());
        handshake.setCountryCodes(new ArrayList<>(botInfo.getCountryCodes()));
        handshake.setGameTypes(new ArrayList<>(botInfo.getGameTypes()));
        handshake.setPlatform(botInfo.getPlatform());
        handshake.setProgrammingLang(botInfo.getProgrammingLang());
        handshake.setInitialPosition(InitialPositionMapper.map(botInfo.getInitialPosition()));
        handshake.setTeamId(EnvVars.getTeamId());
        handshake.setTeamName(EnvVars.getTeamName());
        handshake.setTeamVersion(EnvVars.getTeamVersion());
        handshake.setIsDroid(isDroid);
        handshake.setSecret(secret);
        return handshake;
    }
}

package net.robocode2.server;

public enum ServerState {

	// Waiting for enough participant bots to join
	WAIT_FOR_PARTICIPANTS_TO_JOIN,
	// Game type has been sent, waiting for ready signal from players
	WAIT_FOR_READY_PARTICIPANTS,
	// Game has been started and is running
	GAME_RUNNING,
	// Game has been stopped
	GAME_STOPPED,
}

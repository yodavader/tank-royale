package dev.robocode.tankroyale.server.dev.robocode.tankroyale.server.score

import dev.robocode.tankroyale.schema.Participant
import dev.robocode.tankroyale.server.model.Score

object ResultsView {

    fun getResults(botScores: Collection<Score>, participants: Collection<Participant>): Collection<Score> {

        data class Participant(private val id: Int, private val name: String)

        val rows = mutableMapOf<Participant, Score>()

        participants.forEach { participant ->
            run {
                botScores.find { s -> s.teamOrBotId.botId.id == participant.id }?.let { botScore ->
                    if (participant.teamId != null) {
                        val team = Participant(participant.teamId, participant.teamName)
                        val accumulatedTeamScore = rows[team]
                        rows[team] = if (accumulatedTeamScore == null) {
                            botScore
                        } else {
                            accumulatedTeamScore + botScore
                        }
                    } else {
                        rows[Participant(participant.id, participant.name)] = botScore
                    }
                }
            }
        }
        return rows.values
    }
}
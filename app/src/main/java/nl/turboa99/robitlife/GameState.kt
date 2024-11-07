package nl.turboa99.robitlife

data class GameState(
    val selectedSituation: Int = 0,
    val showDialog: Boolean = false,
    val gameStatus: GameStatus = GameStatus.SituationPicking,
    val robitStats: Array<Int> = Array(Stat.entries.size) { 0 },
    val currentRole: Role? = null,
    val currentVotes: Array<Option?> = Array(Role.entries.size) { null },
    val lastWinner: Option? = null
)

enum class GameStatus {
    SituationPicking,
    TeamPicking,
    OptionPicking,
    Timer,
    ShowWinner
}

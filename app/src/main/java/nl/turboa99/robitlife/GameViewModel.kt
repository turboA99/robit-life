package nl.turboa99.robitlife

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameState())
    val uiState: StateFlow<GameState> = _uiState.asStateFlow()
    val data: List<Situation> = DATA
    val situationCount: Int
        get() = data.size

    fun selectSituation(situation: Int) {
        _uiState.value = _uiState.value.copy(selectedSituation = situation)
    }

    fun getId(index: Int): Int {
        return data[index].id
    }

    fun getSituation(index: Int): Situation {
        return data[index]
    }

    fun showDialog(value: Boolean) {
        _uiState.value = _uiState.value.copy(showDialog = value)
    }

    fun setGameStatus(status: GameStatus) {
        _uiState.value = _uiState.value.copy(gameStatus = status)
    }

    fun setCurrentRole(role: Role) {
        _uiState.value = _uiState.value.copy(currentRole = role)
    }

    fun vote(role: Role, option: Option) {
        val currentVotes = _uiState.value.currentVotes.toMutableList()
        currentVotes[role.ordinal] = option
        _uiState.value = _uiState.value.copy(currentVotes = currentVotes.toTypedArray())
    }

    fun countVotes() {
        val currentStats = _uiState.value.robitStats.toMutableList()
        val curSituation = getSituation(_uiState.value.selectedSituation)
        var votes: MutableMap<Option, Int> = mutableMapOf()
        _uiState.value.currentVotes.forEachIndexed { index, vote ->
            votes[vote!!] = votes.getOrDefault(vote, 0) + when (Role.entries[index]) {
                curSituation.priorityRole -> 2
                else -> 1
            }
        }
        val max = votes.values.max()
        val maxCount = votes.count { it.value == max }
        lateinit var winner: Option
        if (maxCount == 1) {
            votes.entries.forEach {
                if (it.value == max) {
                    currentStats[curSituation.stat.ordinal] += it.key.value
                    winner = it.key
                }
            }
        } else {
            currentStats[curSituation.stat.ordinal] += _uiState.value.currentVotes[curSituation.priorityRole.ordinal]!!.value
            winner = _uiState.value.currentVotes[curSituation.priorityRole.ordinal]!!
        }
        _uiState.value = _uiState.value.copy(robitStats = currentStats.toTypedArray())
        _uiState.value = _uiState.value.copy(lastWinner = winner)
        resetVotes()
    }

    fun resetVotes() {
        _uiState.value = _uiState.value.copy(
            currentVotes = Array(Role.entries.size) { null }
        )
    }

    fun reset() {
        _uiState.value = GameState()
    }

    init {
        _uiState.value = GameState()
    }
}
package com.fighthub.fightcard.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fighthub.fightcard.FightCardInteractor
import com.fighthub.fightcard.model.FightCardsViewModel.FightCardsState.Loading
import com.fighthub.fightcard.model.FightCardsViewModel.FightCardsState.NoUpcomingFights
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FightCardsViewModel @Inject constructor(
    private val interactor: FightCardInteractor
) : ViewModel() {

    private val _fightCards = MutableStateFlow<FightCardsState>(NoUpcomingFights)
    val fightCards: StateFlow<FightCardsState> = _fightCards

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _fightCards.value = Loading
            val cards = interactor.loadUpcomingFightCards()
            _fightCards.value = if (cards.isEmpty()) NoUpcomingFights else _fightCards.value
        }
    }

    sealed class FightCardsState {
        object NoUpcomingFights : FightCardsState()
        object Loading : FightCardsState()
    }
}


package com.fighthub.fightcard

import com.fighthub.fightcard.model.FightCard
import kotlinx.coroutines.delay
import javax.inject.Inject

class FightCardInteractor @Inject constructor() {

    suspend fun loadUpcomingFightCards(): List<FightCard> {
        delay(5000)
        return emptyList()
    }
}

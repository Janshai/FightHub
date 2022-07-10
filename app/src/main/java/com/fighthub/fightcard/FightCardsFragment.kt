package com.fighthub.fightcard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.fighthub.databinding.FragmentFightCardsBinding
import com.fighthub.fightcard.model.FightCardsViewModel
import com.fighthub.fightcard.model.FightCardsViewModel.FightCardsState
import com.fighthub.fightcard.model.FightCardsViewModel.FightCardsState.Loading
import com.fighthub.fightcard.model.FightCardsViewModel.FightCardsState.NoUpcomingFights
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FightCardsFragment : Fragment() {

    private lateinit var binding: FragmentFightCardsBinding

    private val viewModel: FightCardsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.fightCards.collect { handleFightCardsUI(it) }
            }
        }
    }

    private fun handleFightCardsUI(state: FightCardsState) {
        binding.noUpcomingFightsLabel.isVisible = state is NoUpcomingFights
        binding.progressIndicator.isVisible = state is Loading
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFightCardsBinding.inflate(inflater).also { binding = it }.root

}

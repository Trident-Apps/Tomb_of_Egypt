package au.net.ab.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import au.net.ab.R
import au.net.ab.databinding.GameFragmentBinding

class GameFragment : Fragment() {

    private var _binding: GameFragmentBinding? = null
    private val binding get() = _binding!!
    private var playerPoints = 0
    private var competitorPoints = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GameFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button2.setOnClickListener {
            rollDice()
        }
    }

    private fun rollDice() {
        val playerDice = roll()
        val competitorDice = roll()

        val playerImage = when (playerDice) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        val competitorImage = when (competitorDice) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }

        if (playerDice > competitorDice) {
            playerPoints++
        } else if (playerDice < competitorDice) {
            competitorPoints++
        }

        binding.diceIv.setImageResource(playerImage)
        binding.dice2Iv.setImageResource(competitorImage)
        binding.playerScoreTv.text = playerDice.toString()
        binding.competitorScoreTv.text = competitorDice.toString()
        binding.textView4.text = "Your Score: $playerPoints"
        binding.textView5.text = "Enemy's score: $competitorPoints"
    }

    private fun roll(): Int {
        return (1..6).random()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
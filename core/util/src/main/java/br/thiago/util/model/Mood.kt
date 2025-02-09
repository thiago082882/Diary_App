package br.thiago.util.model


import androidx.compose.ui.graphics.Color
import br.thiago.ui.theme.AngryColor
import br.thiago.ui.theme.AwfulColor
import br.thiago.ui.theme.BoredColor
import br.thiago.ui.theme.CalmColor
import br.thiago.ui.theme.DepressedColor
import br.thiago.ui.theme.DisappointedColor
import br.thiago.ui.theme.HappyColor
import br.thiago.ui.theme.HumorousColor
import br.thiago.ui.theme.LonelyColor
import br.thiago.ui.theme.MysteriousColor
import br.thiago.ui.theme.NeutralColor
import br.thiago.ui.theme.RomanticColor
import br.thiago.ui.theme.ShamefulColor
import br.thiago.ui.theme.SurprisedColor
import br.thiago.ui.theme.SuspiciousColor
import br.thiago.ui.theme.TenseColor
import br.thiago.util.R


enum class Mood(
    val icon: Int,
    val contentColor: Color,
    val containerColor: Color
) {
    Neutral(
        icon = R.drawable.neutral,
        contentColor = Color.Black,
        containerColor = NeutralColor
    ),
    Happy(
        icon = R.drawable.happy,
        contentColor = Color.Black,
        containerColor = HappyColor
    ),
    Angry(
        icon = R.drawable.angry,
        contentColor = Color.White,
        containerColor = AngryColor
    ),
    Bored(
        icon = R.drawable.bored,
        contentColor = Color.Black,
        containerColor = BoredColor
    ),
    Calm(
        icon = R.drawable.calm,
        contentColor = Color.Black,
        containerColor = CalmColor
    ),
    Depressed(
        icon = R.drawable.depressed,
        contentColor = Color.Black,
        containerColor = DepressedColor
    ),
    Disappointed(
        icon = R.drawable.disappointed,
        contentColor = Color.White,
        containerColor = DisappointedColor
    ),
    Humorous(
        icon = R.drawable.humorous,
        contentColor = Color.Black,
        containerColor = HumorousColor
    ),
    Lonely(
        icon = R.drawable.lonely,
        contentColor = Color.White,
        containerColor = LonelyColor
    ),
    Mysterious(
        icon = R.drawable.mysterious,
        contentColor = Color.Black,
        containerColor = MysteriousColor
    ),
    Romantic(
        icon = R.drawable.romantic,
        contentColor = Color.White,
        containerColor = RomanticColor
    ),
    Shameful(
        icon = R.drawable.shameful,
        contentColor = Color.White,
        containerColor = ShamefulColor
    ),
    Awful(
        icon = R.drawable.awful,
        contentColor = Color.Black,
        containerColor = AwfulColor
    ),
    Surprised(
        icon = R.drawable.surprised,
        contentColor = Color.Black,
        containerColor = SurprisedColor
    ),
    Suspicious(
        icon = R.drawable.suspicious,
        contentColor = Color.Black,
        containerColor = SuspiciousColor
    ),
    Tense(
        icon = R.drawable.tense,
        contentColor = Color.Black,
        containerColor = TenseColor
    )
}
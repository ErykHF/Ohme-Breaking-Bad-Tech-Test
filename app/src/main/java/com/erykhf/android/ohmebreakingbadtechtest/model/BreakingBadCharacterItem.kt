package com.erykhf.android.ohmebreakingbadtechtest.model

import android.os.Parcelable
import java.io.Serializable

data class BreakingBadCharacterItem(
    val appearance: List<Any> = listOf(),
    val better_call_saul_appearance: List<Int> = listOf(),
    val birthday: String = "",
    val category: String = "",
    val char_id: Int = 0,
    val img: String = "",
    val name: String = "",
    val nickname: String = "",
    val occupation: List<String> = listOf(),
    val portrayed: String = "",
    val status: String = ""
): Serializable
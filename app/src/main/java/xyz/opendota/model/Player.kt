package xyz.opendota.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Keep
@Parcelize
data class Player(
    @SerializedName("tracked_until") val trackedUntil: String,
    @SerializedName("solo_competitive_rank") val soloCompetitiveRank: String,
    @SerializedName("competitive_rank") val competitiveRank: String,
    @SerializedName("rank_tier") val rankTier: Int,
    @SerializedName("leaderboard_rank") val leaderboardRank: Int,
    @SerializedName("mmr_estimate") val mmrEstimate: Double,
    @SerializedName("profile") val longitude: Double,
    var dictance: Int?,
    var date: String?
) : Serializable, Parcelable
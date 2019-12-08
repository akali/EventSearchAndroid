package xyz.opendota.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


data class ProfileInfo(
    @SerializedName("name") val name: String
) : Serializable

@Keep
@Parcelize
data class ProfileResponse(
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: Data
) : Serializable, Parcelable

@Keep
@Parcelize
data class Data(
    @SerializedName("_id") val id: String,
    @SerializedName("username") val username: String
) : Serializable, Parcelable

@Keep
@Parcelize
data class RegistrationRequest(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
) : Serializable, Parcelable

@Keep
@Parcelize
data class RegistrationResponse(
    @SerializedName("status") val status: String
) : Serializable, Parcelable


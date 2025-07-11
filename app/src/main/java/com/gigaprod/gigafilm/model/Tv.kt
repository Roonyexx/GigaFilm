import com.gigaprod.gigafilm.model.Actor
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("tv")
data class Tv(
    override val id: Int,
    val name: String,
    val original_name: String?,
    override val overview: String?,
    override val poster_path: String?,
    override val vote_average: Float?,
    override val vote_count: Int?,
    override val actors: List<Actor>? = null,
    val first_air_date: String?,
    val last_air_date: String?,
    val status: String?,
    val number_of_episodes: Int?,
    val number_of_seasons: Int?,
    override val genres: List<String>? = null,
    override var status_id: Int? = null,
    override var user_score: Int? = null,
    override val contentType: String = "tv"
) : Content()
{
    override fun getDisplayName(): String = name
}
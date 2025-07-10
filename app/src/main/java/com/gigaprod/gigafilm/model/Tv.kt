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
    val number_of_episodes: String?,
    val number_of_seasons: String?,
    override val genres: List<String>? = null,
    override val status_id: Int? = null,
    override val user_score: Int? = null
) : Content()
{
    override fun getDisplayName(): String = name
}
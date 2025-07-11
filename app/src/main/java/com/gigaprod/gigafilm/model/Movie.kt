import com.gigaprod.gigafilm.model.Actor
import com.gigaprod.gigafilm.model.Director
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("film")
data class Movie(
    override val id: Int,
    val title: String,
    val original_title: String,
    override val overview: String?,
    override val poster_path: String?,
    override val vote_average: Float?,
    override val vote_count: Int?,
    override val actors: List<Actor>? = null,
    override val genres: List<String>? = null,
    override val status_id: Int? = null,
    override val user_score: Int? = null,
    val director: Director? = null,
    val release_date: String?,
    val budget : Int?,
    val revenue : Long?,
    val runtime: Int?
) : Content()
{
    override fun getDisplayName(): String = title
}



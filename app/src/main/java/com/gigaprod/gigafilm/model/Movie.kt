import com.gigaprod.gigafilm.model.Actor
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.math.BigInteger

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
    override var status_id: Int? = null,
    override var user_score: Int? = null,
    val release_date: String?,
    val budget : Int?,
    val revenue : Long?,
    val runtime: Int?,
    override val contentType: String = "film"
) : Content()
{
    override fun getDisplayName(): String = title
}



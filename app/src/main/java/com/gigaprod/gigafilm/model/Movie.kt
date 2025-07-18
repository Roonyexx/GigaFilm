import com.gigaprod.gigafilm.model.Actor
import com.gigaprod.gigafilm.model.Director
import com.gigaprod.gigafilm.model.Genre
import com.gigaprod.gigafilm.ui.custom.formatDate
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.format.DateTimeFormatter

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
    override val genres: List<Genre>? = null,
    override var status_id: Int? = null,
    override var user_score: Int? = null,
    val director: Director? = null,
    val release_date: String?,
    val budget : Int?,
    val revenue : Long?,
    val runtime: Int?,
    override val contentType: String = "film"
) : Content()
{
    override fun getDisplayName(): String = title
    override fun getDescription(): String {

        val res: String = "Фильм, " +
                (release_date?.take(4) ?: "") + ", " +
                (genres?.firstOrNull()?.name ?: "")
        return res
    }

    override fun getBaseInfo(): String {
        val formattedDate = formatDate(release_date)
        return "Оценка: ${vote_average?.toString() ?: "-"} (${vote_count?.toString() ?: "-"})\n" +
                "Дата выхода: ${formattedDate}\n" +
                "Режиссёр: ${director?.name ?: "-"}\n" +
                "Бюджет: ${budget?.let { "$it$" } ?: "-"}\n" +
                "Длительность: ${runtime?.let { "$it мин." } ?: "-"}"
    }
}


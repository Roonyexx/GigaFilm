import com.gigaprod.gigafilm.model.Actor
import com.gigaprod.gigafilm.model.Genre
import com.gigaprod.gigafilm.ui.custom.formatDate
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
    override val genres: List<Genre>? = null,
    override var status_id: Int? = null,
    override var user_score: Int? = null,
    override val contentType: String = "tv"
) : Content()
{
    override fun getDisplayName(): String = name
    override fun getDescription(): String {
        val firstDate = first_air_date?.take(4)
        val lastDate = last_air_date?.take(4) ?: ""
        val date = if ((firstDate != lastDate) && lastDate != "") "$firstDate - $lastDate"
                   else firstDate

        val genre = genres?.firstOrNull()?.name?.let { ", $it" } ?: ""
        val res = "Сериал, $date$genre"
        return res
    }

    override fun getBaseInfo(): String {
        val firstDateFormatted = formatDate(first_air_date)
        val lastDateFormatted = formatDate(last_air_date)
        return "Оценка: ${vote_average?.toString() ?: "-"} (${vote_count?.toString() ?: "-"})\n" +
                "Премьера: ${firstDateFormatted}\n" +
                "Финал: ${lastDateFormatted}\n" +
                "Сезоны: ${number_of_seasons?.toString() ?: "-"}\n" +
                "Эпизоды: ${number_of_episodes?.toString() ?: "-"}"
    }
}
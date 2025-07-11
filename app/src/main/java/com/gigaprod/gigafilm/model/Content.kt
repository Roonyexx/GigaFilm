import com.gigaprod.gigafilm.model.Actor
import com.gigaprod.gigafilm.model.Genre
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable

@Serializable
@Polymorphic
sealed class Content {
    abstract val id: Int
    abstract val overview: String?
    abstract val poster_path: String?
    abstract val vote_average: Float?
    abstract val vote_count: Int?
    abstract val actors: List<Actor>?
    abstract val genres: List<Genre>?
    abstract var status_id: Int?
    abstract var user_score: Int?

    abstract val contentType: String
    abstract fun getDisplayName():String

    abstract fun getDescription(): String
}
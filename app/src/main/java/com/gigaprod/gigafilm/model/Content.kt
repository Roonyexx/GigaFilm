import com.gigaprod.gigafilm.model.Actor
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
    abstract val genres: List<String>?
    abstract val status_id: Int?
    abstract val user_score: Int?
    abstract fun getDisplayName():String
}
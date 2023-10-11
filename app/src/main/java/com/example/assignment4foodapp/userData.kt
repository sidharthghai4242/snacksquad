import androidx.lifecycle.ViewModel

data class UserData(
    val username: String,
    val email: String,
    val userId: String,
    val cart: List<Any>
)

class UserDataViewModel : ViewModel() {
    var userData: UserData? = null
}

data class UserModel(
    val username: String,
    val email: String,
    val userId: String,
    val cart: List<Any>
)

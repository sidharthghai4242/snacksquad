import androidx.lifecycle.ViewModel

data class UserData(
    val username: String,
    val email: String,
    val userId: String,
    val cart: List<Any>,
    val address:String,
    val phoneno:Number
)

class UserDataViewModel : ViewModel() {
    var userData: UserData? = null
}

data class UserModel(
    val username: String,
    val email: String,
    val userId: String,
    val cart: List<Any>,
    val address:String,
    val phoneno:Number

)

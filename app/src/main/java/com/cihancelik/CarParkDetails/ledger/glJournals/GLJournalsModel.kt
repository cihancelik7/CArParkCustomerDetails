import com.cihancelik.CarParkDetails.ledger.glPeriods.GLPeriodsModel
import java.io.Serializable

data class GLJournalsModel(
    var journalId: Int = 0,
    var periodId: Int = 0, // Bu, FK ilişkisi için periodId
    var journalDate: String = "",
    var status: String = "",
    var amount: Int = 0,
    var updateDate: String = "",
    var creationDate: String = "",
    var glPeriod: GLPeriodsModel? = null // İlişkili periodi tutmak için
): Serializable

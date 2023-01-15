package pers.xiaoyan.recyclerviewdojo.ui.recyclerview

data class Store(
    val id: String,
    var name: String,
    val openTime: String,
    var inBusiness: Boolean,
    var address: String,
    val isFavorite: Boolean
) : Cloneable {
    val status: String
        get() {
            return if (inBusiness) {
                "营业"
            } else {
                "打烊"
            }
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Store

        if (id != other.id) return false
        if (name != other.name) return false
        if (openTime != other.openTime) return false
        if (inBusiness != other.inBusiness) return false
        if (address != other.address) return false
        if (isFavorite != other.isFavorite) return false
        if (status != other.status) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + openTime.hashCode()
        result = 31 * result + inBusiness.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + isFavorite.hashCode()
        result = 31 * result + status.hashCode()
        return result
    }

    public override fun clone(): Any {
        return super.clone()
    }
}
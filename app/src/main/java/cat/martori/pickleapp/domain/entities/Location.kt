package cat.martori.pickleapp.domain.entities


data class Location(val id: Int?, val name: String, val dimension: String?)

data class LocationSummary(val id: Int?, val name: String, val url: String)

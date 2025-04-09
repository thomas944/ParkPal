package com.example.parkpalv1.data.model.openrouter

data class TripPreferences(
    val partySize: Int = 2,
    val tripDuration: Int = 7, // in days
    val preferredSeason: Season = Season.SUMMER,
    val fitnessLevel: FitnessLevel = FitnessLevel.MODERATE,
    val activities: Set<OutdoorActivity> = emptySet(),
    val budget: BudgetLevel = BudgetLevel.MEDIUM,
    val accommodation: AccommodationType = AccommodationType.HOTEL,
    val destinationType: DestinationType = DestinationType.BEACH
)

enum class Season {
    SPRING, SUMMER, FALL, WINTER
}

enum class FitnessLevel {
    LOW, MODERATE, HIGH, ADVANCED
}

enum class OutdoorActivity {
    SWIMMING, HIKING, BIKING, SIGHTSEEING, SHOPPING, FOOD_TASTING,
    CULTURAL_VISITS, WILDLIFE_WATCHING, CAMPING, SKIING, WATERSPORTS
}

enum class BudgetLevel {
    LOW, MEDIUM, HIGH, LUXURY
}

enum class AccommodationType {
    HOTEL, RESORT, HOSTEL, APARTMENT, CAMPING, BNB
}

enum class DestinationType {
    BEACH, MOUNTAIN, CITY, RURAL, HISTORICAL, ADVENTURE
}
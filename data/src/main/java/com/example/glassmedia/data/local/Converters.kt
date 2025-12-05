package com.example.glassmedia.data.local

import androidx.room.TypeConverter
import com.example.glassmedia.core.model.FilterType
import com.example.glassmedia.core.model.MediaType
import com.example.glassmedia.core.model.ReviewedState

class Converters {
    @TypeConverter
    fun fromMediaType(value: MediaType): String = value.name

    @TypeConverter
    fun toMediaType(value: String): MediaType = MediaType.valueOf(value)

    @TypeConverter
    fun fromReviewedState(value: ReviewedState): String = value.name

    @TypeConverter
    fun toReviewedState(value: String): ReviewedState = ReviewedState.valueOf(value)

    @TypeConverter
    fun fromFilterType(value: FilterType): String = value.name

    @TypeConverter
    fun toFilterType(value: String): FilterType = FilterType.valueOf(value)
}

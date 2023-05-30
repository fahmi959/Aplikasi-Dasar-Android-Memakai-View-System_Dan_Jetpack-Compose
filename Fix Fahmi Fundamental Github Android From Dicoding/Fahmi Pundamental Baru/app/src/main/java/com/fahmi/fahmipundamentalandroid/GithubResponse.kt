package com.fahmi.fahmipundamentalandroid

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class GithubResponse(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("restaurant")
	val restaurant: Restaurant,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("items")
	val items: ArrayList<PenggunaGithub>


)
data class PenggunaGithub(
	@PrimaryKey
	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("html_url")
	val htmlUrl: String,

	@field:SerializedName("is_bookmarked")
	var isBookmarked: Boolean = false
)


data class ParsingPengguna(
	@field:SerializedName("login")
	val login: String,
	@field:SerializedName("id")
	val id: String,
	@field:SerializedName("avatar_url")
	val avatar_url: String,


	)



//@Entity(tableName = "fav_user")
//data class FavUser(
//	@field:SerializedName("login")
//	val login: String,
//
//	@PrimaryKey
//	@field:SerializedName("id")
//	val id: Int,
//
//	@field:SerializedName("avatar_url")
//	val avatarUrl: String,
//
//	@field:SerializedName("is_bookmarked")
//	var isBookmarked: Boolean = false,// definisi properti isBookmarked dengan nilai default false
//)


data class Restaurant(

	@field:SerializedName("customerReviews")
	val customerReviews: List<CustomerReviewsItem>,

	@field:SerializedName("pictureId")
	val pictureId: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("rating")
	val rating: Double,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: String
)

data class CustomerReviewsItem(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("review")
	val review: String,

	@field:SerializedName("name")
	val name: String
)

//data class PostReviewResponse(
//
//	@field:SerializedName("customerReviews")
//	val customerReviews: List<CustomerReviewsItem>,
//
//	@field:SerializedName("error")
//	val error: Boolean,
//
//	@field:SerializedName("message")
//	val message: String
//)

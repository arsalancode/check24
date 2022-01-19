package com.check24.app.networking.networking.repo.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

//filters: [
//"Alle",
//"Verfügbar",
//"Vorgemerkt"
//],

@Parcelize
data class RepoModel(

    @SerializedName("header")
    val header: Header,

    @SerializedName("filters")
    val filters: List<String>,

    @SerializedName("products")
    val products: List<Product>,

    ) : Parcelable {
    override fun toString(): String {
        return "RepoModel(header=$header, filters=$filters, products=$products)"
    }
}


//{
//    name: "Yellow Triangle",
//    type: "Triangle",
//    id: 1,
//    color: "Yellow",
//    imageURL: "https://kredit.check24.de/konto-kredit/ratenkredit/nativeapps/imgs/08.png",
//    colorCode: "FFECB3",
//    available: true,
//    releaseDate: 1460629605,
//    description: "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam",
//    longDescription: "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam
//
//    Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam
//
//    Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam
//    Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam",
//    rating: 2.17,
//    price: {
//    value: 225.91,
//    currency: "EUR"
//}
//},


@Parcelize
data class Product (

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("imageURL")
    val imageURL: String,

    @SerializedName("releaseDate")
    val releaseDate: Long,

    @SerializedName("description")
    val description: String,

    @SerializedName("longDescription")
    val longDescription: String,

    @SerializedName("rating")
    val rating: Float,

    @SerializedName("price")
    val price: Price,

) : Parcelable {
    override fun toString(): String {
        return "Product(id=$id, name='$name', imageURL='$imageURL', releaseDate=$releaseDate, description='$description', longDescription='$longDescription', rating=$rating, price=$price)"
    }
}

//header: {
//    headerTitle: "Check24 Shape Compararison",
//    headerDescription: "List of geometric products"
//}

@Parcelize
data class Header (

    @SerializedName("headerTitle")
    val headerTitle: String,

    @SerializedName("headerDescription")
    val headerDescription: String

) : Parcelable {
    override fun toString(): String {
        return "Header(headerTitle='$headerTitle', headerDescription='$headerDescription')"
    }
}


//    price: {
//    value: 225.91,
//    currency: "EUR"
//}

@Parcelize
data class Price (

    @SerializedName("value")
    val value: Float,

    @SerializedName("currency")
    val currency: String

) : Parcelable {
    override fun toString(): String {
        return "Price(value=$value, currency='$currency')"
    }
}

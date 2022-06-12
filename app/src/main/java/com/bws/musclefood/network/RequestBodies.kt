package com.bws.musclefood.network

object RequestBodies {
    data class LoginBody(
        val EmailID: String,
        val Password: String,
        val DeviceType: String,
        val DeviceID: String
    )

    data class PopulateProductDetailsBody(
        val UserID: String,
        val SubCategoryID: String,
        val SubCategoryName: String,
        val Key: String,
        val Category: String
    )

    data class FavouriteListBody(
        val UserID: String,
        val Category: String,
        val SubCategoryID: String,
        val SubCategoryName: String,
        val Key: String

    )

    data class SearchOrdersBody(
        val UserID: String,
        val StoreID: String,
        val OrderNumber: String,
        val DateFrom: String,
        val DateTo: String,
        val TabKey: String


    )

    data class UpdateProfileBody(
        val UserID: String,
        val Title: String,
        val FirstName: String,
        val LastName: String,
        val CompanyName: String,
        val VATNo: String,
        val EmailID: String,
        val MobilePhone: String
    )

    data class GetDeliveryDetails(
        val UserID: String,
    )

    data class GetUserProfileDetailsBody(
        val UserID: String,
    )


    data class GetAllCartDetailsBody(val UserID: String, val SessionID: String)

    data class ReorderBody(val UserID: String, val OrderID: String)

    data class GetRemoveCartProductBody(val ProductID: String, val UserID: String)

    data class AddFavouriteListBody(val ProductID: String, val UserID: String)

    data class RemoveFavoriteProductBody(val ProductID: String, val UserID: String)


    data class PopulateProductDetailsByProductIDBody(
        val UserID: String,
        val Category: String,
        val SubCategoryID: String,
        val SubCategoryName: String,
        val Key: String,
    )

    data class RegistrationDetailsBody(
        val UserID: String,
        val Title: String,
        val FirstName: String,
        val LastName: String,
        val CompanyName: String,
        val VATNo: String,
        val EmailID: String,
        val Password: String,
        val MobilePhone: String,
        val ProfileImage: String,
        val DeviceType: String,
        val DeviceID: String,
        val ActivationCode: String,
        val ActivationURL: String,
        val RoleID: String
    )

    class GetMenu {}


    class InsertUpdateCart : ArrayList<InsertUpdateCartItem>()

    data class InsertUpdateCartItem(
        val CartItemID: String,
        val Price: String,
        val ProductID: String,
        val Quantity: String,
        val SessionID: String,
        val TotalPrice: String,
        val UserID: String
    )


    data class AddEditDeliveryDetails(
        val ID: String,
        val UserID: String,
        val DeliveryAddressName: String,
        val DeliveryAddressHouseNumber: String,
        val DeliveryAddressLine1: String,
        val DeliveryAddressLine2: String,
        val DeliveryCity: String,
        val DeliveryPostcode: String,
        val DeliveryContactNumber: String,
        val DeliveryDate: String,
        val DeliverySlot: String,
        val DefaultAddressFlag: String
    )

    data class AddEditPaymentDetails(
        val ID: String,
        val UserID: String,
        val PaymentType: String,
        val CardNumber: String,
        val OnCardName: String,
        val CardCVVNumber: String,
        val CardExpiryDate: String,
        val PaymentGatewayID: String
    )

}
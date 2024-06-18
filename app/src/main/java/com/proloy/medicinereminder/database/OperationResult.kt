package com.proloy.medicinereminder.database

sealed class OperationResult{
    object Success: OperationResult()
    data class Error(val message:String) : OperationResult()
}
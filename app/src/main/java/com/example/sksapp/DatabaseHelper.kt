package com.example.sksapp

import com.example.sksapp.model.Menu
import com.example.sksapp.model.Order
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DatabaseHelper {

    private val db = Firebase.database

    fun addOrder(foods: List<Menu>, drinks: List<Menu>, date: String, table: String, onComplete: (Order) -> Unit) {

        val listOrder = Order(foods, drinks, date, table)

        db.getReference("order").push().setValue(listOrder)
            .addOnSuccessListener { onComplete(listOrder) }
    }

    fun report(order: Order) {
        val report = db.getReference("report").child(getDate())

        report.runTransaction(object : Transaction.Handler {

            override fun doTransaction(currentData: MutableData): Transaction.Result {

                for (i in order.makanan) {
                    var data = currentData.child(i.name).getValue(Menu::class.java)

                    if (data == null) {
                        data = i
                    } else {
                        data.count += i.count
                    }

                    currentData.child(i.name).value = data
                }

                for (i in order.minuman) {
                    var data = currentData.child(i.name).getValue(Menu::class.java)

                    if (data == null) {
                        data = i
                    } else {
                        data.count += i.count
                    }

                    currentData.child(i.name).value = data
                }

                return Transaction.success(currentData)
            }

            override fun onComplete(
                error: DatabaseError?,
                committed: Boolean,
                currentData: DataSnapshot?
            ) {}
        })
    }

    fun getAllFoods(onComplete: (List<Menu>) -> Unit) {

        db.getReference("makanan").get().addOnSuccessListener {
            val listFood = mutableListOf<Menu>()

            it.children.forEach { data ->
                val food = data.getValue(Menu::class.java)!!
                listFood.add(food)
            }
            onComplete(listFood)
        }
    }

    fun getAllDrinks(onComplete: (List<Menu>) -> Unit) {

        db.getReference("minuman").get().addOnSuccessListener {
            val listDrink = mutableListOf<Menu>()

            it.children.forEach { data ->
                val food = data.getValue(Menu::class.java)!!
                listDrink.add(food)
            }
            onComplete(listDrink)
        }
    }

    fun getAllOthers(onComplete: (List<Menu>) -> Unit) {

        db.getReference("lain").get().addOnSuccessListener {
            val listOther = mutableListOf<Menu>()

            it.children.forEach { data ->
                val food = data.getValue(Menu::class.java)!!
                listOther.add(food)
            }
            onComplete(listOther)
        }
    }
}
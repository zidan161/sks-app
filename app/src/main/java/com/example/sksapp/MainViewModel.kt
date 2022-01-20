package com.example.sksapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sksapp.model.Menu

class MainViewModel: ViewModel() {

    private val listMakanan = MutableLiveData(mutableListOf<Menu>())
    private val listMinuman = MutableLiveData(mutableListOf<Menu>())

    private var total = MutableLiveData(0)

    fun getListMakanan() = listMakanan

    fun getListMinuman() = listMinuman

    fun getTotal() = total

    fun addItemOnMakanan(makanan: Menu) {
        if (listMakanan.value!!.isNotEmpty()) {
            listMakanan.value!!.find { it.name == makanan.name }?.let {
                it.count++
            }
        } else {
            listMakanan.value!!.add(makanan)
        }
        total.value = total.value?.plus(makanan.price)
    }

    fun addItemOnMinuman(minuman: Menu) {
        if (listMinuman.value!!.isNotEmpty()) {
            listMinuman.value!!.find { it.name == minuman.name }?.let {
                it.count++
            }
        } else {
            listMinuman.value!!.add(minuman)
        }
        total.value = total.value?.plus(minuman.price)
    }

    fun removeItemOnMakanan(makanan: Menu, delete: Boolean) {
        if (delete) {
            listMakanan.value!!.remove(makanan)
            total.value = total.value?.minus(makanan.price * makanan.count)
            return
        }
        if (listMakanan.value!!.isNotEmpty()) {
            listMakanan.value!!.find { it.name == makanan.name }?.let {
                if (it.count == 1) {
                    listMakanan.value!!.remove(makanan)
                } else {
                    it.count--
                }
            }
        }
        total.value = total.value?.minus(makanan.price)
    }

    fun removeItemOnMinuman(minuman: Menu, delete: Boolean) {
        if (delete) {
            listMakanan.value!!.remove(minuman)
            return
        }
        if (listMinuman.value!!.isNotEmpty()) {
            listMinuman.value!!.find { it.name == minuman.name }?.let {
                if (it.count == 1) {
                    listMinuman.value!!.remove(minuman)
                } else {
                    it.count--
                }
            }
        }
    }
}
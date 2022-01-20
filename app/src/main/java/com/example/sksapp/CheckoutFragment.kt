package com.example.sksapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.example.sksapp.databinding.FragmentCheckoutBinding
import com.example.sksapp.model.Menu
import java.util.*

class CheckoutFragment : Fragment() {

    private lateinit var binding: FragmentCheckoutBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckoutBinding.inflate(layoutInflater, container, false)

        val transInflater = TransitionInflater.from(requireContext())
        enterTransition = transInflater.inflateTransition(R.transition.slide_up)
        exitTransition = transInflater.inflateTransition(R.transition.slide_down)

        val tables = arrayListOf<Int>()

        tables.addAll(1 until 31)

        (binding.tlMeja.editText as AutoCompleteTextView).apply {
            setAdapter(ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                tables
            ))
        }

        val foods = viewModel.getListMakanan()
        val drinks = viewModel.getListMinuman()

        val foodAdapter = MenuAdapter (
            { viewModel.addItemOnMakanan(it) },
            { viewModel.removeItemOnMakanan(it, false) },
            true
        )

        val drinkAdapter = MenuAdapter (
            { viewModel.addItemOnMinuman(it) },
            { viewModel.removeItemOnMinuman(it, false) },
            true
        )

        foods.observe(viewLifecycleOwner) {
            foodAdapter.setData(it)
        }

        drinks.observe(viewLifecycleOwner) {
            drinkAdapter.setData(it)
        }

        binding.rvFood.apply {
            adapter = foodAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.rvDrink.apply {
            adapter = drinkAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        val total = viewModel.getTotal()

        total.observe(viewLifecycleOwner) {
            binding.tvTotal.text = "Total: ${it.setDecimal()}"
        }

        binding.btnCheckout.setOnClickListener {
            val table = binding.tlMeja.editText?.text.toString()
            if (table.isEmpty()) {
                binding.tlMeja.error = "Field ini harus diisi!"
                return@setOnClickListener
            }

            DatabaseHelper().addOrder(foods.value as List<Menu>, drinks.value as List<Menu>, getDate(), table) {
                PrinterHelper(requireContext()).printText(it, total.value!!.setDecimal())
                DatabaseHelper().report(it)
                activity?.onBackPressed()
            }
        }

        binding.btnSet.setOnClickListener {
            val discount = binding.edtDiscount.text.toString()
            val voucher = binding.edtVoucher.text.toString()

            if (discount.isNotEmpty()) {
                val disc = discount.toInt()
                total.value = total.value!! * disc / 100
            }
            if (voucher.isNotEmpty()) {
                total.value = total.value?.minus(voucher.toInt())
            }
        }

        return binding.root
    }
}
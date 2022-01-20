package com.example.sksapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sksapp.databinding.DialogAddMenuBinding
import com.example.sksapp.databinding.FragmentOtherBinding
import com.example.sksapp.model.Menu

class OtherFragment : Fragment() {

    private lateinit var binding: FragmentOtherBinding
    private lateinit var menuAdapter: MenuAdapter

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentOtherBinding.inflate(layoutInflater, container, false)

        menuAdapter = MenuAdapter(
            { viewModel.addItemOnMakanan(it) },
            { viewModel.removeItemOnMakanan(it, false) },
            false
        )

        DatabaseHelper().getAllOthers { menuAdapter.setData(it) }

        binding.rvOther.apply {
            adapter = menuAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        binding.btnAddMenu.setOnClickListener {

            val view = DialogAddMenuBinding.inflate(layoutInflater, container, false)

            val alert = AlertDialog.Builder(requireContext())
                .setView(view.root)
                .create()

            alert.show()

            view.btnOke.setOnClickListener {
                var isDone = true

                val name = view.edtName.text.toString().trim()
                val price = view.edtPrice.text.toString()

                if (name.isEmpty()) {
                    view.edtName.error = "Harus diisi!"
                    isDone = false
                }

                if (price.isEmpty()) {
                    view.edtPrice.error = "Harus diisi!"
                    isDone = false
                }

                if (isDone) {
                    menuAdapter.addData(Menu(name, price.toInt()))
                    alert.dismiss()
                }
            }
            view.btnCancel.setOnClickListener { alert.cancel() }
        }

        return binding.root
    }
}
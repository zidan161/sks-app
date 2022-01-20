package com.example.sksapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sksapp.databinding.FragmentMinumanBinding

class MinumanFragment : Fragment() {

    private lateinit var binding: FragmentMinumanBinding
    private lateinit var menuAdapter: MenuAdapter

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMinumanBinding.inflate(layoutInflater, container, false)

        menuAdapter = MenuAdapter(
            { viewModel.addItemOnMinuman(it) },
            { viewModel.removeItemOnMinuman(it, false) },
            false
        )

        DatabaseHelper().getAllDrinks { menuAdapter.setData(it) }

        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.stackFromEnd

        binding.rvDrink.apply {
            this.adapter = menuAdapter
            this.layoutManager = layoutManager
            setHasFixedSize(true)
        }

        return binding.root
    }
}
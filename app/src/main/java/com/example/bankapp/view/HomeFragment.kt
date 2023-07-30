package com.example.bankapp.view

import android.content.ClipData
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankapp.ItemAdapter
import com.example.bankapp.R
import com.example.bankapp.databinding.FragmentHomeBinding
import com.google.android.material.appbar.MaterialToolbar


class HomeFragment : Fragment() {
   lateinit var binding: FragmentHomeBinding
    private lateinit var itemAdapter: ItemAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
     binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.appBar)
        val recyclerView = binding.checkingTransactionPreview
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val itemList = listOf(
            ClipData.Item("Electric bill",R.drawable.ic_baseline_lightbulb_24.toString()),
            ClipData.Item("Item 2", R.drawable.ic_baseline_attach_money_24.toString()),

            // Add more items as needed
        )
        // Initialize and set the adapter
        itemAdapter = ItemAdapter(itemList)
        recyclerView.adapter = itemAdapter
        binding.checkingCardView.setOnClickListener{
            findNavController().navigate(R.id.transactionDetailsFragment)
        }

        return view
    }
}



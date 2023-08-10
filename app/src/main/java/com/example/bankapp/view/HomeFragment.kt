package com.example.bankapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankapp.R
import com.example.bankapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
     binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.appBar)
        val recyclerView = binding.checkingTransactionPreview
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.checkingCardView.setOnClickListener{
            findNavController().navigate(R.id.transactionDetailsFragment)
        }

        return view
    }
}



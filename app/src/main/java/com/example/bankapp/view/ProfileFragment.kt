package com.example.bankapp.view

import android.os.Bundle
import android.provider.Telephony.Mms.Part.TEXT
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankapp.ItemsAdapter
import com.example.bankapp.R
import com.example.bankapp.databinding.FragmentProfileBinding
import com.example.bankapp.model.FirestoreDataModel
import com.example.bankapp.model.ItemData
import com.example.bankapp.model.ItemType
import com.google.firebase.firestore.FirebaseFirestore
import org.xmlpull.v1.XmlPullParser.TEXT


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var itemAdapter: ItemsAdapter
    private val firestoreDataModel = FirestoreDataModel()
    private val itemList = mutableListOf<ItemData>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        val recyclerView = binding.profileRecycle
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        itemAdapter = ItemsAdapter(itemList)
        recyclerView.adapter = itemAdapter
        fetchAndDisplayData()
        return view
    }
    private fun fetchAndDisplayData() {
        firestoreDataModel.fetchItemsData { itemList ->
            itemAdapter.setItems(itemList)
        }
    }
}

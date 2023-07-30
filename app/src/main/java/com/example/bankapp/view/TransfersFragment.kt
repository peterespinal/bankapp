package com.example.bankapp.view
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.bankapp.databinding.FragmentTransfersBinding
import com.example.bankapp.viewmodel.FirestoreViewModel
import com.example.bankapp.viewmodel.TransactionViewModel


class TransfersFragment : Fragment() {
    private lateinit var binding: FragmentTransfersBinding
    private lateinit var viewModel: FirestoreViewModel
    private var senderAccount:String=""
    private lateinit var transactionViewModel: TransactionViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
         binding = FragmentTransfersBinding.inflate(inflater,container,false)
        val  view = binding.root
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.transfersAppBar)
        viewModel = ViewModelProvider(this).get(FirestoreViewModel::class.java)
        transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)
        val chooseAccount = binding.autoCompleteTextView
        viewModel.fetchAccount().observe(this) { accountList ->
            val adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, accountList)
            chooseAccount.adapter = adapter
        }

        binding.makeTransferButton.setOnClickListener {
          binding.autoCompleteTextView.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long) {
                   senderAccount = parent?.getItemAtPosition(position) as String
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Handle the case when nothing is selected.
                }

            }

            val receiverAccount = binding.receiverAccount.text.toString()
            val amount = binding.Amount.text.toString().toDoubleOrNull()
            val description = binding.description.text.toString()

            if (amount == null || amount <= 0) {
                showToast("Invalid amount")
            } else if (senderAccount.isEmpty() || receiverAccount.isEmpty()||description.isEmpty()) {
                showToast("Please fill in all fields")
            } else {
                // Call the ViewModel's performTransaction method.
                transactionViewModel.performTransaction(senderAccount, receiverAccount, amount)
            }
        }
        return view
    }

    private fun showToast(message: String) {
        context?.let {
            Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
        }
    }

}
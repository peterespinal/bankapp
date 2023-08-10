package com.example.bankapp.view
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.bankapp.R
import com.example.bankapp.databinding.FragmentTransfersBinding
import com.example.bankapp.viewmodel.FirestoreViewModel
import com.example.bankapp.viewmodel.TransactionViewModel
import com.google.firebase.firestore.FirebaseFirestore


class TransfersFragment : Fragment() {
    private lateinit var binding: FragmentTransfersBinding
    private lateinit var viewModel: FirestoreViewModel
    private var senderAccount:String=""
    private lateinit var transactionViewModel: TransactionViewModel
    private val fireStore =FirebaseFirestore.getInstance()
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
                validateTransactionData(senderAccount, receiverAccount, amount)
            }
        }
        return view
    }

    private fun showToast(message: String) {
        context?.let {
            Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateTransactionData(senderAccount: String, receiverAccount: String, amount: Double) {
        fireStore.collection("user").document(senderAccount)
            .get()
            .addOnSuccessListener { senderDocumentSnapshot ->
                fireStore.collection("user2").document(receiverAccount)
                    .get()
                    .addOnSuccessListener { receiverDocumentSnapshot ->
                        // Validate sender's and receiver's account information
                        if (senderDocumentSnapshot.exists() && receiverDocumentSnapshot.exists()) {
                            val senderBalance = senderDocumentSnapshot.getDouble("balance") ?: 0.0
                            val receiverBalance = receiverDocumentSnapshot.getDouble("balance") ?: 0.0

                            // Validate transaction amount against sender's balance
                            if (senderBalance >= amount) {
                                // All information is valid, show the confirmation dialog
                                showTransactionConfirmationDialog(senderAccount, receiverAccount, amount)
                            } else {
                                // Insufficient funds, notify the user
                                showToast("Insufficient funds")
                            }
                        } else {
                            // Invalid sender's or receiver's account, notify the user
                            showToast("Invalid account information")
                        }
                    }
                    .addOnFailureListener { exception ->
                        // Handle any errors that occur while reading receiver's account information
                        showToast("Failed to get receiver account information")
                    }
            }
            .addOnFailureListener { exception ->
                // Handle any errors that occur while reading sender's account information
                showToast("Failed to get sender account information")
            }
    }
    private fun showTransactionConfirmationDialog(senderAccount: String, receiverAccount: String, amount: Double) {
        // Inflate the transaction_info_layout.xml and populate it with transaction details
        // ...
        val transactionInfoView = layoutInflater.inflate(R.layout.confirm_transfer_info, null)
        val textSenderAccount: TextView = transactionInfoView.findViewById(R.id.textSenderAccount)
        val textReceiverAccount: TextView = transactionInfoView.findViewById(R.id.textReceiverAccount)
        val textTransactionAmount: TextView = transactionInfoView.findViewById(R.id.textTransactionAmount)

        textSenderAccount.text = "Sender's Account: $senderAccount"
        textReceiverAccount.text = "Receiver's Account: $receiverAccount"
        textTransactionAmount.text = "Transaction Amount: $amount"
        // Show a confirmation dialog to the user
        AlertDialog.Builder(requireContext())
            .setTitle("Confirm Transaction")
            .setView(transactionInfoView)
            .setPositiveButton("Confirm") { _, _ ->
                // If the user confirms the transaction, proceed with the actual transaction
                transactionViewModel.performTransaction(senderAccount, receiverAccount, amount)
                clearAllFields()
            }
            .setNegativeButton("Cancel", null)
            .show()

    }

    private fun clearAllFields() {
        // Clear all fields in your views representing sender's account, receiver's account, and transaction amount
        // For example:
        binding.receiverAccount.text?.clear()
        binding.Amount.text?.clear()
        binding.description.text?.clear()
    }
}
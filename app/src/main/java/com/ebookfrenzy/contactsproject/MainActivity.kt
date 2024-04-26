package com.ebookfrenzy.contactsproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ebookfrenzy.contactsproject.databinding.ActivityMainBinding
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import java.util.*

class MainActivity : AppCompatActivity(), OnButtonClickListener {
    private lateinit var binding: ActivityMainBinding
    private var adapter: ContactListAdapter? = null
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listenerSetup()
        observerSetup()
        recyclerSetup()
    }

    private fun clearFields() {
        binding.contactName.setText("")
        binding.contactPhone.setText("")
    }

    private fun listenerSetup() {
        binding.addButton.setOnClickListener {
            val name = binding.contactName.text.toString()
            val phone = binding.contactPhone.text.toString()
            if (name != "" && phone != "") {
                val contact = Contact(name, phone)
                viewModel.insertContact(contact)
                clearFields()
            } else {
                val text = "You must enter a name and phone number"
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(this, text, duration) // in Activity
                toast.show()
            }
        }
        binding.findButton.setOnClickListener {
            val name = binding.contactName.text.toString()
            if (name != "") {
                viewModel.findContact(binding.contactName.text.toString())
            }
            else {
                val text = "You must enter a search criteria in the name field"
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(this, text, duration) // in Activity
                toast.show()
            }
        }
        binding.ascButton.setOnClickListener {
            viewModel.sortASC()
        }
        binding.descButton.setOnClickListener {
            viewModel.sortDESC()
        }
    }

    private fun observerSetup() {
        viewModel.getAllcontacts()?.observe(this) { contacts ->
            contacts?.let {
                adapter?.setContactList(it)
            }
        }
        viewModel.getSearchResults().observe(this) { contacts ->
            contacts?.let {
                if (it.isNotEmpty()) {
                    adapter?.setContactList(it)
                } else {
                    val text = "There are no contacts that match your search"
                    val duration = Toast.LENGTH_SHORT

                    val toast = Toast.makeText(this, text, duration) // in Activity
                    toast.show()
                }
            }
        }
    }

    private fun recyclerSetup() {
        adapter = ContactListAdapter(R.layout.contact_list_item)
        binding.contactRecycler.layoutManager = LinearLayoutManager(this)
        binding.contactRecycler.adapter = adapter
    }

    override fun onButtonClick(id: Int) {
        viewModel.deleteContact(id)
    }

}
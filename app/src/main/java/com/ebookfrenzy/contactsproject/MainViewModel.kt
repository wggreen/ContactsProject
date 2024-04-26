package com.ebookfrenzy.contactsproject

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ContactRepository = ContactRepository(application)
    private val allContacts: LiveData<List<Contact>>?
    private val searchResults: MutableLiveData<List<Contact>>

    init {
        allContacts = repository.allContacts
        searchResults = repository.searchResults

    }

    fun insertContact(contact: Contact) {
        repository.insertContact(contact)
    }

    fun findContact(name: String) {
        repository.findContact(name)
    }

    fun deleteContact(id: Int) {
        repository.deleteContact(id)
    }

    fun getSearchResults(): MutableLiveData<List<Contact>> {
        return searchResults
    }

    fun getAllcontacts(): LiveData<List<Contact>>? {
        return allContacts
    }

    fun sortASC() {
        repository.sortASC()
    }

    fun sortDESC() {
        repository.sortDESC()
    }


}
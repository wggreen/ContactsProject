package com.ebookfrenzy.contactsproject

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

class ContactRepository(application: Application) {
    val searchResults = MutableLiveData<List<Contact>>()
    private var contactDao: ContactDao?

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertContact(newcontact: Contact) {
        coroutineScope.launch(Dispatchers.IO) {
            asyncInsert(newcontact)
        }
    }

    private fun asyncInsert(contact: Contact) {
        contactDao?.insertContact(contact)
    }
    fun deleteContact(id: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            asyncDelete(id)
        }
    }
    private fun asyncDelete(id: Int) {
        contactDao?.deleteContact(id)
    }
    fun findContact(name: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFind(name).await()
        }
    }

    fun sortASC(){
        coroutineScope.launch(Dispatchers.Main){
            searchResults.value = asyncSortASC().await()
        }
    }

    private suspend fun asyncSortASC(): Deferred<List<Contact>?> =
        coroutineScope.async(Dispatchers.IO){
            return@async contactDao?.sortASC()
        }

    fun sortDESC(){
        coroutineScope.launch(Dispatchers.Main){
            searchResults.value = asyncSortDESC().await()
        }
    }

    private suspend fun asyncSortDESC(): Deferred<List<Contact>?> =
        coroutineScope.async(Dispatchers.IO){
            return@async contactDao?.sortDESC()
        }


    private fun asyncFind(name: String): Deferred<List<Contact>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async contactDao?.findContact(name)
        }

    val allContacts: LiveData<List<Contact>>?

    init {
        val db: ContactRoomDatabase? =
            ContactRoomDatabase.getDatabase(application)
        contactDao = db?.contactDao()
        allContacts = contactDao?.getAllContacts()
    }}
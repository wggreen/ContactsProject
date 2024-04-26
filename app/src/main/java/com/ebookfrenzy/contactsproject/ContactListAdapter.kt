package com.ebookfrenzy.contactsproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactListAdapter(private val contactItemLayout: Int) :
    RecyclerView.Adapter<ContactListAdapter.ViewHolder>()  {
    private var contactList: List<Contact>? = null
    private var listener: OnButtonClickListener? = null

    override fun onBindViewHolder(holder: ViewHolder, listPosition: Int) {
        val item = holder.item
        contactList.let {
            val nameTextView = item.findViewById<TextView>(R.id.contact_row_name)
            nameTextView.text = it!![listPosition].contactName
            val phoneTextView = item.findViewById<TextView>(R.id.contact_row_phone)
            phoneTextView.text = it!![listPosition].contactPhone
            val button = item.findViewById<ImageButton>(R.id.deleteButton)

            button.setOnClickListener(View.OnClickListener {
                val data: Contact = contactList!!.get(listPosition)
                listener!!.onButtonClick(data.id)
            })
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            contactItemLayout, parent, false)
        return ViewHolder(view)
    }

    fun setContactList(contacts: List<Contact>) {
        contactList = contacts
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (contactList == null) 0 else contactList!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var item: LinearLayout = itemView.findViewById(R.id.contact_row)

    }
}
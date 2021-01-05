package com.example.receipo.ui.creation

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.receipo.R
import java.text.DateFormat
import java.util.*

class DateFragment : Fragment() {

    lateinit var dateText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_date, container, false)

        val cal = Calendar.getInstance()
        dateText = root.findViewById<TextView>(R.id.date_picker_text)
        setDateText(cal)

        // setup dateText to show date picker dialog on click
        dateText.setOnClickListener { view ->
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            val dialog = DatePickerDialog(requireContext(), dateSetListener, year, month, day)
            dialog.show()
        }

        return root
    }

    // OnDateSetListener defines what to do when a date is selected
    private val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
        val cal = Calendar.getInstance()
        cal.set(year, month, dayOfMonth)
        setDateText(cal)
    }

    private fun setDateText(cal: Calendar) {
//        view?.findViewById<TextView>(R.id.date_picker_text)?.apply {
//            text = DateFormat.getDateInstance().format(cal.time)
//        }
        dateText.text = DateFormat.getDateInstance().format(cal.time)
    }
}

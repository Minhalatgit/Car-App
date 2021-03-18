package com.oip.carapp.home.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.michalsvec.singlerowcalendar.calendar.CalendarChangesObserver
import com.michalsvec.singlerowcalendar.calendar.CalendarViewManager
import com.michalsvec.singlerowcalendar.calendar.SingleRowCalendarAdapter
import com.michalsvec.singlerowcalendar.selection.CalendarSelectionManager
import com.michalsvec.singlerowcalendar.utils.DateUtils
import com.oip.carapp.BaseFragment
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentHistoryBinding
import com.oip.carapp.home.adapters.HistoryAdapter
import com.oip.carapp.home.models.History
import kotlinx.android.synthetic.main.calendar_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class HistoryFragment : BaseFragment() {

    private lateinit var binding: FragmentHistoryBinding
    private val calendar = Calendar.getInstance()
    private var currentMonth = 0
    private lateinit var historyRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        setToolbarView()

        historyRecyclerView = binding.historyRecyclerView

        // set current date to calendar and current month to currentMonth variable
        calendar.time = Date()
        currentMonth = calendar[Calendar.MONTH]

        val myCalendarViewManager = object : CalendarViewManager {
            override fun setCalendarViewResourceId(
                position: Int,
                date: Date,
                isSelected: Boolean
            ): Int {
                if (isSelected) {
                    return R.layout.calendar_item_selected
                }
                return R.layout.calendar_item
            }

            override fun bindDataToCalendarView(
                holder: SingleRowCalendarAdapter.CalendarViewHolder,
                date: Date,
                position: Int,
                isSelected: Boolean
            ) {
                // bind data to calendar item views
                holder.itemView.date.text = DateUtils.getDayNumber(date)
                holder.itemView.day.text = DateUtils.getDay3LettersName(date)
            }
        }
        val mySelectionManager = object : CalendarSelectionManager {
            override fun canBeItemSelected(position: Int, date: Date): Boolean {
//                val cal = Calendar.getInstance()
//                cal.time = date
//                //in this example sunday and saturday can't be selected, other item can be selected
//                return when (cal[Calendar.DAY_OF_WEEK]) {
//                    Calendar.SATURDAY -> false
//                    Calendar.SUNDAY -> false
//                    else -> true
//                }
                return true
            }
        }
        val myCalendarChangesObserver = object : CalendarChangesObserver {
            override fun whenSelectionChanged(isSelected: Boolean, position: Int, date: Date) {
                Log.d(
                    "HistoryFragment",
                    "${DateUtils.getMonthName(date)}, ${DateUtils.getDayNumber(date)} "
                )
                super.whenSelectionChanged(isSelected, position, date)
            }
        }

        val singleRowCalendar = binding.calendar.apply {
            calendarViewManager = myCalendarViewManager
            calendarChangesObserver = myCalendarChangesObserver
            calendarSelectionManager = mySelectionManager
            setDates(getFutureDatesOfCurrentMonth())
            init()
        }

        val list = ArrayList<History>()
        list.apply {
            add(
                History(
                    R.drawable.profile_two,
                    "Test",
                    "25.00",
                    "2.2 km",
                    "7958 Swift Village",
                    "105 William St, Chicago, US"
                )
            )
            add(
                History(
                    R.drawable.profile_two,
                    "Test",
                    "25.00",
                    "2.2 km",
                    "7958 Swift Village",
                    "105 William St, Chicago, US"
                )
            )
            add(
                History(
                    R.drawable.profile_two,
                    "Test",
                    "25.00",
                    "2.2 km",
                    "7958 Swift Village",
                    "105 William St, Chicago, US"
                )
            )
            add(
                History(
                    R.drawable.profile_two,
                    "Test",
                    "25.00",
                    "2.2 km",
                    "7958 Swift Village",
                    "105 William St, Chicago, US"
                )
            )
            add(
                History(
                    R.drawable.profile_two,
                    "Test",
                    "25.00",
                    "2.2 km",
                    "7958 Swift Village",
                    "105 William St, Chicago, US"
                )
            )
            add(
                History(
                    R.drawable.profile_two,
                    "Test",
                    "25.00",
                    "2.2 km",
                    "7958 Swift Village",
                    "105 William St, Chicago, US"
                )
            )
        }

        historyRecyclerView.layoutManager = LinearLayoutManager(activity)
        historyRecyclerView.adapter = HistoryAdapter(list)

        return binding.root
    }

    private fun getFutureDatesOfCurrentMonth(): List<Date> {
        // get all next dates of current month
        currentMonth = calendar[Calendar.MONTH]
        return getDates(mutableListOf())
    }

    private fun getDates(list: MutableList<Date>): List<Date> {
        // load dates of whole month
        calendar.set(Calendar.MONTH, currentMonth)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        list.add(calendar.time)
        while (currentMonth == calendar[Calendar.MONTH]) {
            calendar.add(Calendar.DATE, +1)
            if (calendar[Calendar.MONTH] == currentMonth)
                list.add(calendar.time)
        }
        calendar.add(Calendar.DATE, -1)
        return list
    }

    private fun setToolbarView() {
        title.text = "History"
        switch.visibility = View.GONE
        navigationIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.yellow))
        mactivity.window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
    }
}
package com.moneyflow.tracker.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moneyflow.tracker.data.model.Expense
import com.moneyflow.tracker.data.model.FlowType
import com.moneyflow.tracker.databinding.ItemExpenseBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class ExpenseAdapter(
    private val onExpenseClick: (Expense) -> Unit
) : ListAdapter<Expense, ExpenseAdapter.ExpenseViewHolder>(ExpenseDiffCallback()) {

    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US)
    private val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val binding = ItemExpenseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ExpenseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ExpenseViewHolder(
        private val binding: ItemExpenseBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(expense: Expense) {
            binding.apply {
                // Title and date
                tvExpenseTitle.text = expense.title
                tvExpenseDate.text = dateFormat.format(Date(expense.timestamp))
                
                // Amount with sign
                val amountText = when (expense.flowType) {
                    FlowType.INFLOW -> "+${currencyFormat.format(expense.amount)}"
                    FlowType.OUTFLOW -> "-${currencyFormat.format(expense.amount)}"
                }
                tvExpenseAmount.text = amountText
                
                // Color based on flow type
                val color = when (expense.flowType) {
                    FlowType.INFLOW -> Color.parseColor("#4CAF50") // Green
                    FlowType.OUTFLOW -> Color.parseColor("#F44336") // Red
                }
                tvExpenseAmount.setTextColor(color)
                
                // Memo (optional)
                if (expense.memo.isNotBlank()) {
                    tvExpenseMemo.text = expense.memo
                    tvExpenseMemo.visibility = View.VISIBLE
                } else {
                    tvExpenseMemo.visibility = View.GONE
                }
                
                // Tags (optional)
                if (expense.tags.isNotBlank()) {
                    tvExpenseTags.text = "🏷️ ${expense.tags}"
                    tvExpenseTags.visibility = View.VISIBLE
                } else {
                    tvExpenseTags.visibility = View.GONE
                }
                
                // Click listener
                root.setOnClickListener { onExpenseClick(expense) }
            }
        }
    }

    class ExpenseDiffCallback : DiffUtil.ItemCallback<Expense>() {
        override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem == newItem
        }
    }
}

package com.moneyflow.tracker.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moneyflow.tracker.data.model.Wallet
import com.moneyflow.tracker.databinding.ItemWalletBinding
import java.text.NumberFormat
import java.util.*

class WalletAdapter(
    private val onWalletClick: (Wallet) -> Unit,
    private val onWalletLongClick: (Wallet) -> Unit
) : ListAdapter<Wallet, WalletAdapter.WalletViewHolder>(WalletDiffCallback()) {

    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder {
        val binding = ItemWalletBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WalletViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WalletViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class WalletViewHolder(
        private val binding: ItemWalletBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(wallet: Wallet) {
            binding.apply {
                // Wallet info
                tvIcon.text = wallet.icon
                tvWalletName.text = wallet.name
                
                // Balance and limit
                if (wallet.limit > 0) {
                    tvWalletBalance.text = "${currencyFormat.format(wallet.balance)} / ${currencyFormat.format(wallet.limit)}"
                    
                    // Progress bar
                    progressBar.progress = wallet.percentUsed.toInt()
                    
                    // Remaining amount
                    tvRemaining.text = currencyFormat.format(wallet.remaining)
                    
                    // Color based on status
                    val color = when {
                        wallet.isOverLimit -> Color.parseColor("#F44336") // Red
                        wallet.remaining < wallet.limit * 0.2 -> Color.parseColor("#FF9800") // Orange
                        else -> Color.parseColor("#4CAF50") // Green
                    }
                    tvRemaining.setTextColor(color)
                    progressBar.progressTintList = android.content.res.ColorStateList.valueOf(color)
                } else {
                    tvWalletBalance.text = currencyFormat.format(wallet.balance)
                    progressBar.progress = 0
                    tvRemaining.text = currencyFormat.format(wallet.balance)
                    tvRemaining.setTextColor(Color.parseColor("#00BCD4")) // Cyan
                }
                
                // Icon background color
                try {
                    cvIcon.setCardBackgroundColor(Color.parseColor(wallet.colorHex))
                } catch (e: Exception) {
                    cvIcon.setCardBackgroundColor(Color.parseColor("#00BCD4"))
                }
                
                // Click listeners
                root.setOnClickListener { onWalletClick(wallet) }
                root.setOnLongClickListener { 
                    onWalletLongClick(wallet)
                    true
                }
            }
        }
    }

    class WalletDiffCallback : DiffUtil.ItemCallback<Wallet>() {
        override fun areItemsTheSame(oldItem: Wallet, newItem: Wallet): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Wallet, newItem: Wallet): Boolean {
            return oldItem == newItem
        }
    }
}

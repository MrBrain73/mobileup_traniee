package com.example.mobileup.view.main.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mobileup.data.repository.Repository
import com.example.mobileup.databinding.FragmentListBinding
import com.example.mobileup.view.main.adapter.MainAdapter
import com.example.mobileup.view.main.viewmodel.ListViewModel
import com.example.mobileup.view.main.viewmodel.ListViewModelFactory

class ListFragment : Fragment() {

    private lateinit var binding : FragmentListBinding
    private lateinit var viewModel: ListViewModel
    private val adapter = MainAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.listCoin.adapter = adapter

        val repository = Repository()
        val viewModelFactory = ListViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[ListViewModel::class.java]

        binding.usdChip.setOnClickListener {
            getData("usd")
        }

        binding.eurChip.setOnClickListener {
            getData("eur")
        }

        viewModel.listLiveData.observe(viewLifecycleOwner) {
            binding.loadBar.visibility = View.GONE
            if (it.isSuccessful) {
                binding.errorLayout.visibility = View.GONE
                it.body()?.let { it1 -> adapter.addCoins(it1) }
            } else {
                binding.errorLayout.visibility = View.VISIBLE
            }
        }

        binding.tryAgain.setOnClickListener{
            binding.errorLayout.visibility = View.GONE
            if (binding.eurChip.isChecked) getData("eur")
            else getData("usd")
            binding.listCoin.visibility = View.VISIBLE
        }
    }


    private fun getData(query : String) {
        binding.listCoin.visibility = View.VISIBLE
        if (isOnline(requireContext())) {
            binding.loadBar.visibility = View.VISIBLE
            viewModel.getAllCoin(query, 30)
        } else
            binding.errorLayout.visibility = View.VISIBLE
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
                return true
        }
        return false
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
    }
}
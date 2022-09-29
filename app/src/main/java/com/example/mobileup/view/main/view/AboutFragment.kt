package com.example.mobileup.view.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mobileup.data.repository.Repository
import com.example.mobileup.databinding.FragmentAboutBinding
import com.example.mobileup.view.main.viewmodel.AboutViewModel
import com.example.mobileup.view.main.viewmodel.AboutViewModelFactory
import com.squareup.picasso.Picasso

class AboutFragment : Fragment() {

    private lateinit var binding : FragmentAboutBinding
    private lateinit var viewModel: AboutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.loadBar.visibility = View.VISIBLE

        val repository = Repository()
        val viewModelFactory = AboutViewModelFactory(repository)
        viewModel = ViewModelProvider(requireActivity(),
            viewModelFactory)[AboutViewModel::class.java]

        viewModel.getInfoAboutCoin(arguments?.getString("id")!!)

        viewModel.liveData.observe(viewLifecycleOwner) {
            binding.loadBar.visibility = View.GONE
            if (it.isSuccessful) {
                Picasso.get().load(arguments?.getString("logo")).into(binding.iconCoin)
                binding.descriptionCoin.text = it.body()?.description?.get("en")?.asString

                val list = arrayListOf<String>()
                for (i in 0 until it.body()?.categories?.size()!!) {
                    list.add(it.body()?.categories?.get(i)?.asString!!)
                }

                binding.categoriesCoin.text = list.toString().drop(1).dropLast(1)

                binding.aboutCoin.visibility = View.VISIBLE
            } else binding.errorLayout.visibility = View.VISIBLE
        }

        binding.tryAgain.setOnClickListener{
            binding.errorLayout.visibility = View.GONE
            binding.loadBar.visibility = View.VISIBLE
            viewModel.getInfoAboutCoin(arguments?.getString("id")!!)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AboutFragment()
    }
}
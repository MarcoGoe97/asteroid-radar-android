package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, MainViewModelFactory(requireActivity().application)).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val adapter = MainAsteroidAdapter(AsteroidListener { asteroid ->
            this.findNavController().navigate(MainFragmentDirections.actionShowDetail(asteroid))
        })
        binding.rvAsteroids.adapter = adapter

        viewModel.asteroids.observe(viewLifecycleOwner) {
            it?.let { asteroids ->
                adapter.submitList(asteroids)
            }
        }

        viewModel.pictureOfDay.observe(viewLifecycleOwner) {
            it?.let { pictureOfDay ->
                Picasso.with(context).load(pictureOfDay.url).into(binding.activityMainImageOfTheDay)
            }
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}

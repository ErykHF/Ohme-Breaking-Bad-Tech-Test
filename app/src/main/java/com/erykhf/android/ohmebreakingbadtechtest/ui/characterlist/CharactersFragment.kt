package com.erykhf.android.ohmebreakingbadtechtest.ui.characterlist

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.erykhf.android.ohmebreakingbadtechtest.R
import com.erykhf.android.ohmebreakingbadtechtest.databinding.FragmentCharacterListBinding
import com.erykhf.android.ohmebreakingbadtechtest.model.BreakingBadCharacterItem
import com.erykhf.android.ohmebreakingbadtechtest.networkutils.ConnectionLiveData
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : Fragment(R.layout.fragment_character_list) {

    private lateinit var binding: FragmentCharacterListBinding
    private val recyclerViewAdapter = MyCharactersRecyclerViewAdapter()
    private val viewModel: CharactersViewModel by viewModels()
    private lateinit var connectionLiveData: ConnectionLiveData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateToDetails()
        setupRecyclerView()
        observeLoadingAndError()
        observeViewModel()

        connectionLiveData = ConnectionLiveData(requireContext())
        connectionLiveData.observe(viewLifecycleOwner) { isNetworkAvailable ->

            if (isNetworkAvailable == false) {
                Snackbar.make(view, "No Internet Connection", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.characters.observe(viewLifecycleOwner) { characters ->
            if (characters == null) {
                binding.list.visibility = View.GONE
            }
            recyclerViewAdapter.updateList(characters)
            binding.list.visibility = View.VISIBLE
            Log.d("TAG", "onViewCreated: ${characters?.size}")
        }
    }

    private fun observeLoadingAndError() {
        viewModel.errorText.observe(viewLifecycleOwner) { text ->
            text?.let {
                binding.errorTextView.text = it
                binding.errorTextView.visibility = View.VISIBLE
                binding.errorButton?.visibility = View.VISIBLE
                binding.errorButton?.setOnClickListener {
                    viewModel.getAllCharacters()
                }
                viewModel.onErrorTextShown()
            }
        }
        viewModel.spinner.observe(viewLifecycleOwner) { value ->
            value.let {
                binding.spinner.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    binding.errorTextView.visibility = View.GONE
                    binding.errorButton?.visibility = View.GONE
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =

        when (item.itemId) {
            R.id.menu_filter -> {
                showFilteringPopUpMenu()
                true
            }
            else -> {
                false
            }
        }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.characters_fragment_menu, menu)

        val searchItem: MenuItem = menu.findItem(R.id.menu_item_search)
        val searchView = searchItem.actionView as SearchView

        searchView.apply {

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(queryText: String): Boolean {
                    Log.d("MainActivity", "QueryTextSubmit: $queryText")
                    return false
                }

                override fun onQueryTextChange(queryText: String): Boolean {
                    Log.d("MainActivity", "QueryTextChange: $queryText")
                    recyclerViewAdapter.filter.filter(queryText)
                    return true
                }
            })
        }
    }

    private fun navigateToDetails() {
        recyclerViewAdapter.setOnItemClickListener {
            val action =
                CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailsFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() {
        binding.list.apply {
            adapter = recyclerViewAdapter
        }
    }

    private fun showFilteringPopUpMenu() {
        val view = activity?.findViewById<View>(R.id.menu_filter) ?: return
        PopupMenu(requireContext(), view).run {
            menuInflater.inflate(R.menu.filter_seasons, menu)

            setOnMenuItemClickListener {

                when (it.itemId) {
                    R.id.one -> {
                        viewModel.filterSeasons(1)
                        Toast.makeText(requireContext(), "Season One", Toast.LENGTH_SHORT)
                            .show()
                    }
                    R.id.two -> {
                        viewModel.filterSeasons(2)
                        Toast.makeText(requireContext(), "Season Two", Toast.LENGTH_SHORT)
                            .show()
                    }
                    R.id.three -> {
                        viewModel.filterSeasons(3)
                        Toast.makeText(requireContext(), "Season Three", Toast.LENGTH_SHORT)
                            .show()
                    }
                    R.id.four -> {
                        viewModel.filterSeasons(4)
                        Toast.makeText(requireContext(), "Season Four", Toast.LENGTH_SHORT)
                            .show()
                    }
                    R.id.five -> {
                        viewModel.filterSeasons(5)
                        Toast.makeText(requireContext(), "Season Five", Toast.LENGTH_SHORT)
                            .show()
                    }
                    else -> {
                        viewModel.filterSeasons(0)
                        Toast.makeText(requireContext(), "All Seasons", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                true
            }
            show()
        }
    }
}


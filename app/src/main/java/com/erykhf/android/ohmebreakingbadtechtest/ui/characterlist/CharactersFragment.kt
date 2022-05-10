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
import com.erykhf.android.ohmebreakingbadtechtest.R
import com.erykhf.android.ohmebreakingbadtechtest.databinding.FragmentCharacterListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : Fragment(R.layout.fragment_character_list) {

    private lateinit var binding: FragmentCharacterListBinding
    private val recyclerViewAdapter = MyCharactersRecyclerViewAdapter()
    private val viewModel: CharactersViewModel by viewModels()


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
        setupRecyclerView(FilterSeasons.ALL_SEASONS)
        navigateToDetails()

    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.menu_filter -> {
                showFilteringPopUpMenu()
                true
            }
            else -> false
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

    private fun setupRecyclerView(filterSeasons: FilterSeasons) {
        binding.list.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = recyclerViewAdapter

            viewModel.characters.observe(viewLifecycleOwner) {
                val list = viewModel.filterSeasons(filterSeasons)
                recyclerViewAdapter.updateList(list)
                Log.d("TAG", "onViewCreated: ${list.size}")
            }
        }
    }

    private fun showFilteringPopUpMenu() {
        val view = activity?.findViewById<View>(R.id.menu_filter) ?: return
        PopupMenu(requireContext(), view).run {
            menuInflater.inflate(R.menu.filter_seasons, menu)

            setOnMenuItemClickListener {

                when (it.itemId) {
                    R.id.one -> {
                        setupRecyclerView(FilterSeasons.SEASON_ONE)
                        Toast.makeText(requireContext(), "Season One", Toast.LENGTH_SHORT).show()
                    }
                    R.id.two -> setupRecyclerView(FilterSeasons.SEASON_TWO)
                    R.id.three -> setupRecyclerView(FilterSeasons.SEASON_THREE)
                    R.id.four -> setupRecyclerView(FilterSeasons.SEASON_FOUR)
                    R.id.five ->  setupRecyclerView(FilterSeasons.SEASON_FIVE)
                    else -> FilterSeasons.ALL_SEASONS
                }
                true
            }
            show()
        }
    }
}
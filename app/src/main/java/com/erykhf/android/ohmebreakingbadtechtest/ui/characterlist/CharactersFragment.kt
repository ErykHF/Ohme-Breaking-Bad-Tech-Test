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
        showError()
        setupRecyclerView()
        navigateToDetails()

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
            layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = recyclerViewAdapter

            viewModel.characters.observe(viewLifecycleOwner) {
                it?.let {
                    recyclerViewAdapter.updateList(it)
                    Log.d("TAG", "onViewCreated: ${it.size}")
                }
            }
        }
    }

    private fun showError() {
        viewModel.spinner.observe(viewLifecycleOwner) { value ->
            value.let { show ->
                binding.spinner.visibility = if (show) View.VISIBLE else View.GONE
            }
        }
        viewModel.errorText.observe(viewLifecycleOwner) { text ->
            text?.let {
                binding.errorTextView.apply {
                    this.text = text
                    visibility = View.VISIBLE
                }
                binding.list.visibility = View.GONE
                viewModel.onErrorTextShown()
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
                        viewModel.season.value = FilterSeasons.SEASON_ONE
                        Toast.makeText(requireContext(), "Season One", Toast.LENGTH_SHORT)
                            .show()
                    }
                    R.id.two -> {
                        viewModel.season.value = FilterSeasons.SEASON_TWO
                        Toast.makeText(requireContext(), "Season Two", Toast.LENGTH_SHORT)
                            .show()
                    }
                    R.id.three -> {
                        viewModel.season.value = FilterSeasons.SEASON_THREE
                        Toast.makeText(requireContext(), "Season Three", Toast.LENGTH_SHORT)
                            .show()
                    }
                    R.id.four -> {
                        viewModel.season.value = FilterSeasons.SEASON_FOUR
                        Toast.makeText(requireContext(), "Season Four", Toast.LENGTH_SHORT)
                            .show()
                    }
                    R.id.five -> {
                        viewModel.season.value = FilterSeasons.SEASON_FIVE
                        Toast.makeText(requireContext(), "Season Five", Toast.LENGTH_SHORT)
                            .show()
                    }
                    else -> {
                        viewModel.season.value = FilterSeasons.ALL_SEASONS
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


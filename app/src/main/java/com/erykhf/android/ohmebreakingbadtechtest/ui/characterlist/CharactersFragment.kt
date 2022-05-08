package com.erykhf.android.ohmebreakingbadtechtest.ui.characterlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.erykhf.android.ohmebreakingbadtechtest.R
import com.erykhf.android.ohmebreakingbadtechtest.databinding.FragmentCharacterListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : Fragment(R.layout.fragment_character_list) {

    lateinit var binding: FragmentCharacterListBinding
    private val recyclerViewAdapter = MyCharactersRecyclerViewAdapter()
    private val viewModel: CharactersViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.list.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = recyclerViewAdapter

            viewModel.characters.observe(viewLifecycleOwner) {
                recyclerViewAdapter.updateList(it)
                Log.d("TAG", "onViewCreated: ${it?.firstOrNull()?.name}")
            }
        }

        navigateToDetails()
    }

    private fun navigateToDetails(){
        recyclerViewAdapter.setOnItemClickListener {
            val action = CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailsFragment(it)
            findNavController().navigate(action)
        }
    }
}
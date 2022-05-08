package com.erykhf.android.ohmebreakingbadtechtest.ui.characterdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.erykhf.android.ohmebreakingbadtechtest.R
import com.erykhf.android.ohmebreakingbadtechtest.databinding.FragmentCharacterDetailsBinding

class CharacterDetailsFragment : Fragment(R.layout.fragment_character_details) {


    private val viewModel: CharacterDetailsViewModel by viewModels()
    private val args: CharacterDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentCharacterDetailsBinding


//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
//
//        return binding.root
//    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCharacterDetailsBinding.bind(view)

        binding.message.text = args.character.name

    }


}
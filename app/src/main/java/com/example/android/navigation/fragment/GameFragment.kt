/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.android.navigation.R
import com.example.android.navigation.database.NumberDatabase
import com.example.android.navigation.databinding.FragmentGameBinding
import com.example.android.navigation.viewModel.GameViewModel
import com.example.android.navigation.viewModel.GameViewModelFactory

class GameFragment : Fragment() {


    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val dataSource = NumberDatabase.getInstance(application).numberDatabaseDao
        val viewModelFactory = GameViewModelFactory(dataSource, application)

        val viewModel =
            ViewModelProvider(
                this, viewModelFactory).get(GameViewModel::class.java)

        val binding = DataBindingUtil.inflate<FragmentGameBinding>(
            inflater, R.layout.fragment_game, container, false
        )

        //viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        binding.gameViewModel = viewModel

        viewModel.generatedNumber.observe(viewLifecycleOwner, Observer { newNumber ->
            binding.text.text = newNumber.toString()
        })

        binding.generateButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        {
            viewModel.setGeneratedNumber((1..100).random())
            viewModel.enableSubmitButton()
        }

        binding.submitButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->
            viewModel.saveNumber()
            viewModel.disableSubmitButton()
            view.findNavController()
                .navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment(viewModel.generatedNumber.value?:0))
        }

        binding.setLifecycleOwner(this)

        return binding.root
    }

}

package com.mobile.finance.newentry

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import com.mobile.finance.R
import com.mobile.finance.data.entity.Tag
import com.mobile.finance.databinding.FragmentNewEntryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewEntryFragment : Fragment() {

  val viewModel: NewEntryViewModel by viewModels()
  private lateinit var binding: FragmentNewEntryBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentNewEntryBinding.inflate(inflater, container, false)

    binding.newEntryViewModel = viewModel

    activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation).let {
      it?.visibility = View.GONE
    }

    binding.lifecycleOwner = viewLifecycleOwner
    return binding.root
  }

  @SuppressLint("ResourceType")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    viewModel.categories.asLiveData().observe(viewLifecycleOwner) {
      it.let {
        val adapter = ArrayAdapter(requireContext(),
          R.layout.array_adapter_item,
          it.map { category -> category.name })
        (binding.categoryTextField.editText as? AutoCompleteTextView)?.setAdapter(adapter)
      }
    }

    viewModel.funds.asLiveData().observe(viewLifecycleOwner) {
      it.let {
        val adapter = ArrayAdapter(requireContext(),
          R.layout.array_adapter_item,
          it.map { fund -> fund.name })
        (binding.fundTextField.editText as? AutoCompleteTextView)?.setAdapter(adapter)
      }
    }

    viewModel.doneSaving.observe(viewLifecycleOwner) {
      if (it) {
        viewModel.entrySaved()
        findNavController().navigate(NewEntryFragmentDirections.actionNewEntryFragmentToEntriesFragment())
        Toast.makeText(context, "Entry saved", Toast.LENGTH_SHORT).show()
      }
    }

    binding.addTagButton.setOnClickListener { view ->
      val tagText: String = binding.tagsTextField.editText?.text.toString()
      addTag(tagText)
      binding.tagsTextField.editText?.text?.clear()
    }

    binding.saveEntryButton.setOnClickListener {
        viewModel.onSave()
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    binding.lifecycleOwner = null
  }

  private fun addTag(text: String) {
    var newTag = Chip(context)
    newTag.isCloseIconVisible = true
    newTag.text = text
    newTag.setOnCloseIconClickListener {
      binding.tagsChipGroup.removeView(it)
      viewModel.formTags.remove((it as Chip).text)
    }
    viewModel.formTags[text] = Tag(text, viewModel.newEntry.type)
    binding.tagsChipGroup.addView(newTag)
  }
}
package com.mobile.finance.entries

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mobile.finance.R
import com.mobile.finance.databinding.FragmentEntriesBinding
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [EntriesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class EntryListFragment : Fragment() {

  private val viewModel: EntryListViewModel by viewModels()
  lateinit var binding: FragmentEntriesBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    setHasOptionsMenu(true)
    // Inflate the layout for this fragment

    binding = FragmentEntriesBinding.inflate(inflater, container, false)
    binding.entryListViewModel = viewModel

    val adapter = EntryListAdapter(viewModel)
    binding.entryList.adapter = adapter
    viewModel.entryList.observe(viewLifecycleOwner, Observer {
        it?.let {
          adapter.submitList(it)
        }
    })

    binding.newEntryButton.setOnClickListener {
      findNavController().navigate(EntryListFragmentDirections.actionEntriesFragmentToNewEntryFragment())
    }

    activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation).let {
      it?.visibility = View.VISIBLE
    }

    binding.lifecycleOwner = viewLifecycleOwner
    return binding.root
  }

  override fun onDestroyView() {
    super.onDestroyView()
    binding.lifecycleOwner = null
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.entry_list_menu, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.upload_entries -> {
        viewModel.uploadEntries()
        true
      }
      else -> {
        super.onOptionsItemSelected(item)
      }
    }
  }
}
package com.shaya.loginauthentication.ui.database

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.shaya.loginauthentication.R
import com.shaya.loginauthentication.databinding.FragmentRecyclerViewBinding
import com.shaya.loginauthentication.ui.AuthorsAdapter
import com.shaya.loginauthentication.ui.AuthorsViewModel

class RecyclerViewFragment : Fragment() {

    private lateinit var binding: FragmentRecyclerViewBinding
    private lateinit var viewModel: AuthorsViewModel
    private val adapter = AuthorsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this)[AuthorsViewModel::class.java]
        binding = FragmentRecyclerViewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewAuthors.adapter = adapter
        binding.progressBar.visibility = View.VISIBLE
        viewModel.fetchAuthors()
        viewModel.authors.observe(viewLifecycleOwner, Observer {
            adapter.setAuthors(it)
            binding.progressBar.visibility = View.GONE
        })

        binding.btnAdd.setOnClickListener {
            AddAuthorDialogFragment().show(childFragmentManager, "")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sort_by_name -> viewModel.fetchFilteredAuthors(1)
            R.id.sort_by_age -> viewModel.fetchFilteredAuthors(2)
            R.id.sort_by_city -> viewModel.fetchFilteredAuthors(3)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
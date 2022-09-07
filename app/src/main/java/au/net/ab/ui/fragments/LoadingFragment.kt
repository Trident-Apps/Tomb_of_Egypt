package au.net.ab.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import au.net.ab.R
import au.net.ab.databinding.LoadingFragmentBinding
import au.net.ab.model.UrlDatabase
import au.net.ab.ui.viewmodel.MyViewModel
import au.net.ab.ui.viewmodel.MyViewModelFactory
import au.net.ab.util.Checker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoadingFragment : Fragment() {
    private var _binding: LoadingFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MyViewModel
    private val checker = Checker()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoadingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelFactory = MyViewModelFactory(
            this@LoadingFragment.requireActivity().application,
            UrlDatabase(this@LoadingFragment.requireActivity().applicationContext)
        )

        viewModel = ViewModelProvider(this, viewModelFactory)[MyViewModel::class.java]

        val smth: Boolean = true
        checker.isDeviceSecured(this@LoadingFragment.requireActivity())
        if (smth) {
            startGame()
        } else {
            viewModel.getUrlFromDb().observe(viewLifecycleOwner) { entity ->
                if (entity == null) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        viewModel.fetchDeeplink(this@LoadingFragment.requireActivity())
                    }
                    lifecycleScope.launch(Dispatchers.Main) {
                        viewModel.urlLiveData.observe(viewLifecycleOwner) {
                            startWeb(it)
                        }
                    }
                } else {
                    viewModel.getUrlFromDb().observe(viewLifecycleOwner) {
                        startWeb(it.url)
                    }
                }

            }
        }

    }


    private fun startGame() {
        findNavController().navigate(R.id.startGameFragment)
    }

    private fun startWeb(url: String) {
        val bundle = bundleOf("url" to url)
        findNavController().navigate(R.id.webViewFragment, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
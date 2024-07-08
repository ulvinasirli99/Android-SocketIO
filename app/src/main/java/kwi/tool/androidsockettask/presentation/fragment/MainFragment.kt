package kwi.tool.androidsockettask.presentation.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import kwi.tool.androidsockettask.core.utils.ApiStatus
import kwi.tool.androidsockettask.data.db.entity.toDataResponse
import kwi.tool.androidsockettask.domain.model.DataResponse
import kwi.tool.androidsockettask.domain.model.toDataEntity
import kwi.tool.androidsockettask.presentation.adapter.DataAdapter
import kwi.tool.androidsockettask.presentation.viewModel.WebSocketViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kwi.tool.androidsockettask.R
import kwi.tool.androidsockettask.databinding.MainFragmentBinding

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WebSocketViewModel by viewModels()
    private var listOfData = mutableListOf<DataResponse>()
    private lateinit var adapter: DataAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        val layoutManager = GridLayoutManager(context, VERTICAL)
        binding.recyclerView.layoutManager = layoutManager
        adapter = DataAdapter(listOfData)
        binding.recyclerView.adapter = adapter
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.webSocketData.collect { data ->
                when (data) {
                    is ApiStatus.Connected -> {
                        Log.d("TAG", "onViewCreated: Connected status ")
                        binding.connectionStatusLabel.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.green
                            )
                        )
                        binding.connected.setAnimation(R.raw.connected_anim)
                        binding.connected.playAnimation()
                        binding.progressBar.isVisible = true
                    }

                    is ApiStatus.Update -> {
                        Log.d("TAG", "onViewCreated: Update status")
                        showDataFromRemote(data.value)
                        if (databaseIsEmpty()) addDataToDatabase(data.value)
                        else updatedDatabase(data.value)
                        binding.progressBar.isVisible = false
                    }

                    is ApiStatus.Error -> {
                        binding.progressBar.isVisible = false
                        showDataFromLocal()
                        binding.connectionStatusLabel.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.red
                            )
                        )
                        binding.connected.setAnimation(R.raw.not_connected_anim)
                        binding.connected.playAnimation()
                        Log.d("TAG", "onViewCreated: Error message is -> ${data.message}")
                    }

                    else -> {
                        // todo do something
                    }
                }
            }

        }
        viewModel.connect()
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun showDataFromRemote(data: List<DataResponse>) {
        listOfData.clear()
        listOfData.addAll(data)
        adapter.notifyDataSetChanged()
    }

    private fun updatedDatabase(data: List<DataResponse>) {
        data.onEach {
            viewModel.updateDatabase(it.toDataEntity())
        }
    }

    private fun addDataToDatabase(data: List<DataResponse>) {
        data.onEach {
            viewModel.insertData(it.toDataEntity())
        }
    }

    private fun databaseIsEmpty(): Boolean {
        viewModel.getAllData()
        return viewModel.localData.value?.isEmpty() == true
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showDataFromLocal() {
        listOfData.clear()
        viewModel.getAllData()
        lifecycleScope.launch {
            viewModel.localData.collect {
                it?.onEach { dataEntity ->
                    listOfData.add(dataEntity.toDataResponse())
                }
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



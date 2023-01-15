package pers.xiaoyan.recyclerviewdojo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import pers.xiaoyan.recyclerviewdojo.databinding.FragmentHomeBinding
import pers.xiaoyan.recyclerviewdojo.ui.recyclerview.Store
import pers.xiaoyan.recyclerviewdojo.ui.recyclerview.StoreAdapter
import pers.xiaoyan.recyclerviewdojo.ui.recyclerview.StoreDiffCallBack

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val storeAdapter by lazy {
        StoreAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initViews()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {
        storeAdapter.data = initListData()
        binding.rvStoreList.adapter = storeAdapter
        binding.refresh.setOnClickListener {
            mockRefresh()
        }
        binding.insert.setOnClickListener {
            mockInsert()
        }
        binding.delete.setOnClickListener {
            mockDelete()
        }
        binding.rangeChange.setOnClickListener {
            mockRangeChanged()
        }
    }

    private fun mockRangeChanged() {
        val newStoreList: MutableList<Store> = copyAdapterData()
        newStoreList[1].name = "改名字了1"
        newStoreList[2].name = "改名字了2"
        newStoreList[3].name = "改名字了3"
        storeAdapter.data = newStoreList
    }

    private fun mockDelete() {
        val newStoreList: MutableList<Store> = copyAdapterData()
        newStoreList.removeAt(1)
        storeAdapter.data = newStoreList
    }

    private fun mockInsert() {
        val newStoreList: MutableList<Store> = copyAdapterData()
        newStoreList.add(
            1, Store(
                id = "new 0",
                name = "这是insert No.XXXXX店",
                openTime = "09:00-20:00",
                inBusiness = true,
                address = "陕西省西安市No.XXXXX店",
                isFavorite = false
            )
        )
        storeAdapter.data = newStoreList
    }

    private fun initListData(): MutableList<Store> {
        val list: MutableList<Store> = mutableListOf()
        list.clear()
        for (i in 0..29) {
            list.add(
                Store(
                    id = i.toString(),
                    name = "No.${i}店",
                    openTime = "09:00-20:00",
                    inBusiness = true,
                    address = "陕西省西安市No.${i}店",
                    isFavorite = false
                )
            )
        }
        return list
    }

    private fun mockRefresh() {
        val newStoreList: MutableList<Store> = copyAdapterData()
        newStoreList.forEachIndexed { index, _ ->
            if (index % 3 == 0) {
                newStoreList.getOrNull(index)?.apply {
                    name = "改变了No.${index}店！！"
                    inBusiness = false
                    address = "地址改了！！陕西省西安市No.${index}店"
                }
            }
        }
        storeAdapter.data = newStoreList
    }

    private fun copyAdapterData(): MutableList<Store> {
        val newStoreList: MutableList<Store> = mutableListOf()
        storeAdapter.data.takeIf { it.isNotEmpty() }?.onEach { store ->
            (store.clone() as? Store)?.let {
                newStoreList.add(it)
            }
        }
        return newStoreList
    }
}
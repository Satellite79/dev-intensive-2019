package ru.skillbranch.devintensive.ui.archive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_archive.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.ui.adapters.ChatAdapter
import ru.skillbranch.devintensive.ui.adapters.ChatItemTouchHelperCallback
import ru.skillbranch.devintensive.viewmodels.ArchiveViewModel
import ru.skillbranch.devintensive.viewmodels.MainViewModel

class ArchiveActivity : AppCompatActivity() {
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var viewModel: ArchiveViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archive)

        initToolbar()
        initViews()
        initViewModel()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }
    private fun initViews() {
        chatAdapter = ChatAdapter { Snackbar.make(rv_archive_list, "Click on ${it.title}", Snackbar.LENGTH_LONG)}

        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        val touchCallback = ChatItemTouchHelperCallback(chatAdapter){
            viewModel.restoreFromArchive(it.id)
            val snackbar = Snackbar.make(rv_archive_list,"Восстановить чат с ${it.title} из архива?", Snackbar.LENGTH_LONG)
            snackbar.setAction("Отмена", object: View.OnClickListener{
                override fun onClick(v: View?) {
                    viewModel.addToArchive(it.id)
                }
            })

            val textView = snackbar.view.findViewById(R.id.snackbar_text) as TextView
            val textColorTyped = TypedValue()
            theme.resolveAttribute(R.attr.colorSnackbarText, textColorTyped, true)
            textView.setTextColor(textColorTyped.data)
            snackbar.show()
        }
        val touchHelper = ItemTouchHelper(touchCallback)
        touchHelper.attachToRecyclerView(rv_archive_list)

        with(rv_archive_list){
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(this@ArchiveActivity)
            addItemDecoration(divider)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ArchiveViewModel::class.java)
        viewModel.getChatData().observe(this, Observer{chatAdapter.updateData(it)})
    }
}

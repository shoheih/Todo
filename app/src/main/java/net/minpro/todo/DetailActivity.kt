package net.minpro.todo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), DetailFragment.OnFragmentInteractionListener {

    //DetailFragment.OnFragmentInteractionListener#onEditSelectedTodo
    override fun onEditSelectedTodo(
        title: String,
        deadline: String,
        taskDetail: String,
        isCompleted: Boolean,
        mode: ModeInEdit
    ) {
        var intent = Intent(this@DetailActivity, EditActivity::class.java).apply {
            putExtra(IntentKey.TITLE.name, title)
            putExtra(IntentKey.DEADLINE.name, deadline)
            putExtra(IntentKey.TASK_DETAIL.name, taskDetail)
            putExtra(IntentKey.IS_COMPLETED.name, isCompleted)
            putExtra(IntentKey.MODE_IN_EDIT.name, mode)
        }
        startActivity(intent)
        finish()
    }

    //DetailFragment.OnFragmentInteractionListener#onDataDeleted
    override fun onDataDeleted() {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

        toolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
            setNavigationOnClickListener {
                finish()
            }
        }

        val bundle = intent.extras
        val title = bundle.getString(IntentKey.TITLE.name)
        val deadline = bundle.getString(IntentKey.DEADLINE.name)
        val taskDetail = bundle.getString(IntentKey.TASK_DETAIL.name)
        val isCompleted = bundle.getBoolean(IntentKey.IS_COMPLETED.name)

        supportFragmentManager.beginTransaction()
            .add(R.id.container_detail,
                    DetailFragment.newInstance(title, deadline, taskDetail, isCompleted),
                    FragmentTag.DETAIL.toString()).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        menu!!.apply {
            findItem(R.id.menu_delete).isVisible = false
            findItem(R.id.menu_edit).isVisible = false
            findItem(R.id.menu_register).isVisible = false
        }
        return true
    }

}

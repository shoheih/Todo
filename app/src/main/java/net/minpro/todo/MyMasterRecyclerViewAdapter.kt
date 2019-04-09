package net.minpro.todo


import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_master.view.*
import net.minpro.todo.MasterFragment.OnListFragmentInteractionListener
import java.text.SimpleDateFormat
import java.util.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyMasterRecyclerViewAdapter(
    private val mValues: RealmResults<TodoModel>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyMasterRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            Log.d("test", v.toString())
            val item = v.tag as TodoModel
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListItemClicked(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_master, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.textViewTitle.text = item!!.title
        holder.textViewDeadline.text = MyApplication.appContext.getString(R.string.deadline) + ": " + item!!.deadLine
        // TODO Cardの先頭画像 (期限切れの場合とまだの場合で分ける)
        val changedDeadline = SimpleDateFormat("yyyy/MM/dd").parse(item!!.deadLine)
        val today = Date()
        if (today >= changedDeadline){
            holder.imageStatus.setImageResource(R.drawable.ic_warning_black_24dp)
        } else {
            holder.imageStatus.setImageResource(R.drawable.ic_work_black_24dp)
        }

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val textViewTitle: TextView = mView.textViewTitle
        val textViewDeadline: TextView = mView.textViewDeadline
        val imageStatus: ImageView = mView.imageStatus
    }
}

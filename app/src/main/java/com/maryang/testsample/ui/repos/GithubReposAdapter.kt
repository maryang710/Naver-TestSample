package com.maryang.testsample.ui.repos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maryang.testsample.R
import com.maryang.testsample.entity.GithubRepo
import com.maryang.testsample.ui.repo.GithubRepoActivity
import kotlinx.android.synthetic.main.item_github_repo.view.*

class GithubReposAdapter : RecyclerView.Adapter<GithubReposAdapter.RepoViewHolder>() {

    var items: List<GithubRepo> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder =
        RepoViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_github_repo, parent, false)
        )

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(repo: GithubRepo) {
            with(itemView) {
                repoName.text = repo.name
                repoDescription.text = repo.description
                repoStar.setImageResource(
                    if (repo.star) R.drawable.baseline_star_24
                    else R.drawable.baseline_star_border_24
                )
                setOnClickListener {
                    GithubRepoActivity.start(context, repo)
                }
            }
        }
    }
}

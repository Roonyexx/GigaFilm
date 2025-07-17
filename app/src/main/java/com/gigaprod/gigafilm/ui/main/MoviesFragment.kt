package com.gigaprod.gigafilm.ui.main

import Content
import android.animation.ObjectAnimator
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.gigaprod.gigafilm.R
import com.gigaprod.gigafilm.adapter.MovieAdapter
import com.gigaprod.gigafilm.api.StandartResponse
import com.gigaprod.gigafilm.api.ContentStatus
import com.gigaprod.gigafilm.network.ServerRepository
import com.gigaprod.gigafilm.ui.custom.CardStackLayoutManager
import com.gigaprod.gigafilm.ui.custom.SharedViewModel
import com.gigaprod.gigafilm.ui.dialog.MovieInfoBottomSheet
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.abs

import android.animation.*
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import kotlin.math.min


enum class Status(val status: Int) {
    like(1),
    dislike(2),
    unrated(3)
}
class MoviesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter

    private var RecommedationsJob: Job? = null
    private lateinit var serverRepository: ServerRepository

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var swipeIcon: ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = CardStackLayoutManager()
        swipeIcon = view.findViewById(R.id.swipeIcon)

        val activity = requireActivity() as MainActivity
        serverRepository = activity.serverRepository

        lifecycleScope.launch {
            adapter = MovieAdapter(serverRepository.getRecommendations().toMutableList())
            recyclerView.adapter = adapter
        }



        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.UP
        ) {
            private var currentSwipeAnimation: AnimatorSet? = null
            private var isAnimating = false

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (isAnimating) {
                    currentSwipeAnimation?.cancel()
                    swipeIcon.visibility = View.GONE
                    isAnimating = false
                }

                val position = viewHolder.adapterPosition
                val movie = adapter.currentList()[position]

                when (direction) {
                    ItemTouchHelper.LEFT, ItemTouchHelper.RIGHT -> {
                        animateSwipeIcon(direction) {
                            swipeIcon.visibility = View.GONE
                            isAnimating = false

                            adapter.removeAt(position)

                            if (adapter.itemCount < 5 && (RecommedationsJob == null || RecommedationsJob?.isActive == false)) {
                                RecommedationsJob = lifecycleScope.launch {
                                    val content: List<Content> =
                                        serverRepository.getRecommendations()
                                    adapter.addMovieList(content.toMutableList())
                                }
                            }
                        }

                        lifecycleScope.launch {
                            if (direction == ItemTouchHelper.LEFT) movie.status_id = Status.dislike.status
                            else movie.status_id = Status.like.status

                            sendData(movie)

                            val request: ContentStatus =
                                ContentStatus(movie.id, movie.contentType, movie.status_id!!)
                            val response: StandartResponse =
                                serverRepository.setContentStatus(request)
                        }
                    }

                    ItemTouchHelper.UP -> {
                        val existing = parentFragmentManager.findFragmentByTag("movie_info")
                        if (existing == null) {
                            MovieInfoBottomSheet(movie).show(parentFragmentManager, "movie_info")
                        }

                        viewHolder.itemView.animate()
                            .translationX(0f)
                            .translationY(0f)
                            .setDuration(200)
                            .start()
                        adapter.notifyItemChanged(position)

                        swipeIcon.visibility = View.GONE
                    }
                }
            }

            override fun onChildDraw(
                c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
            ) {
                val maxUpDistance = -100f
                val clampedDY = if (dY < maxUpDistance) maxUpDistance else dY

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && isCurrentlyActive) {
                    swipeIcon.visibility = View.VISIBLE
                    swipeIcon.alpha = min(1f, abs(dX) / recyclerView.width)

                    if (dX > 0) swipeIcon.setImageResource(R.drawable.ic_like)
                    else if (dX < 0) swipeIcon.setImageResource(R.drawable.ic_dislike)
                    else swipeIcon.visibility = View.GONE
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, clampedDY, actionState, isCurrentlyActive)
            }
            private fun animateSwipeIcon(direction: Int, onAnimationEnd: () -> Unit) {
                currentSwipeAnimation?.cancel()

                swipeIcon.translationX = 0f
                swipeIcon.translationY = 0f
                swipeIcon.rotation = 0f
                swipeIcon.scaleX = 1f
                swipeIcon.scaleY = 1f

                swipeIcon.alpha = 1f
                isAnimating = true

                val animatorSet = AnimatorSet()

                when (direction) {
                    ItemTouchHelper.RIGHT -> {
                        val rotationAnimator = ObjectAnimator.ofFloat(swipeIcon, "rotation", 0f, 15f, -10f, 5f, 0f)
                        rotationAnimator.duration = 600
                        rotationAnimator.interpolator = DecelerateInterpolator()

                        val scaleXAnimator = ObjectAnimator.ofFloat(swipeIcon, "scaleX", 1f, 1.2f, 1f)
                        val scaleYAnimator = ObjectAnimator.ofFloat(swipeIcon, "scaleY", 1f, 1.2f, 1f)
                        scaleXAnimator.duration = 300
                        scaleYAnimator.duration = 300

                        animatorSet.playTogether(rotationAnimator, scaleXAnimator, scaleYAnimator)
                    }

                    ItemTouchHelper.LEFT -> {
                        val shakeAnimator = ObjectAnimator.ofFloat(swipeIcon, "translationX", 0f, -10f, 10f, -8f, 8f, -5f, 5f, 0f)
                        shakeAnimator.duration = 500
                        shakeAnimator.interpolator = LinearInterpolator()

                        val rotationAnimator = ObjectAnimator.ofFloat(swipeIcon, "rotation", 0f, -5f, 5f, -3f, 3f, 0f)
                        rotationAnimator.duration = 500
                        rotationAnimator.interpolator = LinearInterpolator()

                        animatorSet.playTogether(shakeAnimator, rotationAnimator)
                    }
                }

                animatorSet.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        currentSwipeAnimation = null
                        onAnimationEnd()
                    }

                    override fun onAnimationCancel(animation: Animator) {
                        super.onAnimationCancel(animation)
                        currentSwipeAnimation = null
                        isAnimating = false
                    }
                })

                currentSwipeAnimation = animatorSet
                animatorSet.start()
            }

        })


        itemTouchHelper.attachToRecyclerView(recyclerView)

        sharedViewModel.barContent.observe(viewLifecycleOwner) { content ->
            val index = adapter.findContentById(content.id)
            if (index != null) {
                adapter.removeAt(index)
            }
        }
    }
    private fun sendData(content: Content) {
        sharedViewModel.setContent(content)
    }
}

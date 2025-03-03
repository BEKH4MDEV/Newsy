package com.bekhamdev.newsy.main.presentation.home.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.bekhamdev.newsy.core.domain.utils.SharedValues
import com.bekhamdev.newsy.main.presentation.model.ArticleUi
import com.bekhamdev.newsy.ui.theme.NewsyTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HeadlineItem(
    articles: List<ArticleUi>,
    modifier: Modifier = Modifier,
    onCardClick: (ArticleUi) -> Unit,
    onFavouriteChange: (ArticleUi) -> Unit,
    pageCount: Int = SharedValues.PAGER_PAGE_COUNT,
    listState: LazyListState,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { pageCount }
    )
    val scope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current

    var autoScrolling by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collect {
                val visibleItems = it.map { item -> item.index }
                autoScrolling = visibleItems.contains(2)
            }
    }

    LaunchedEffect(pagerState.currentPage, pageCount, autoScrolling) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                delay(6000L)
                val nextPage = if (pagerState.currentPage + 1 < pageCount) {
                    pagerState.currentPage + 1
                } else {
                    0
                }
                scope.launch {
                    if (!pagerState.isScrollInProgress && autoScrolling) {
                        pagerState.animateScrollToPage(nextPage)
                    }
                }
            }
        }
    }

    HorizontalPager(
        modifier = modifier
            .fillMaxWidth(),
        state = pagerState,
        contentPadding = PaddingValues(NewsyTheme.dimens.defaultPadding),
        pageSize = PageSize.Fill,
        pageSpacing = NewsyTheme.dimens.itemSpacing,
    ) { page ->
        HeadlineCard(
            article = articles[page],
            onCardClick = onCardClick,
            onFavouriteChange = onFavouriteChange,
            sharedTransitionScope = sharedTransitionScope,
            animatedVisibilityScope = animatedVisibilityScope
        )
    }
}
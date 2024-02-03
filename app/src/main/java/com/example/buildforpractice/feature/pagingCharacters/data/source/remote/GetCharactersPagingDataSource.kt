

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.buildforpractice.feature.characters.data.model.domain.RMCharacter
import com.example.buildforpractice.feature.characters.data.model.response.CharacterResponse
import com.example.buildforpractice.feature.characters.data.model.response.UnifiedCharacterResponse
import com.example.buildforpractice.feature.characters.data.model.toDomain
import com.example.buildforpractice.feature.pagingCharacters.data.source.remote.GetCharactersPagingService
import com.example.buildforpractice.utils.network.toResource
import javax.inject.Inject

class GetCharactersPagingDataSource @Inject constructor(
    private val charactersPagingService: GetCharactersPagingService
) : PagingSource<Int, RMCharacter>() {

    override fun getRefreshKey(state: PagingState<Int, RMCharacter>): Int {
        //how much page to refresh
        return 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RMCharacter> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize

            val response = charactersPagingService.getCharactersByPage(page)
            val pagedResponse = response.body()
            var nextPageNumber: Int? = null

            if (pagedResponse?.info?.next != null) {
                val uri = Uri.parse(pagedResponse.info.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }
            LoadResult.Page(
                data = pagedResponse?.results.orEmpty().map { it.toDomain() },
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
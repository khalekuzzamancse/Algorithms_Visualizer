package layers.data.data.remote

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import layers.data.data.remote.other.DataFetcher
import layers.domain.repoisitory.TutorialRepository
import layers.domain.response_model.TutorialTheoryResponseModel

class TutorialRepositoryImpl : TutorialRepository {
    override fun getTutorialTheory(): Flow<TutorialTheoryResponseModel> {
        TODO()
//        return DataFetcher().fetchTutorialTheory().map {
//            TutorialTheoryResponseModel(it.richString)
//        }
    }
}
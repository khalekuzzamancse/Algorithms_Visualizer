package layers.domain.repoisitory

import layers.domain.response_model.TutorialTheoryResponseModel
import kotlinx.coroutines.flow.Flow

interface TutorialRepository {
    fun getTutorialTheory(): Flow<TutorialTheoryResponseModel>
}
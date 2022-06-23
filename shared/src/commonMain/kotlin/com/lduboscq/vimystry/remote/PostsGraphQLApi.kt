package com.lduboscq.vimystry.remote
/*
import org.koin.core.component.KoinComponent
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.normalizedCache
*/
// @Serializable
data class Post(
    val id: Long,
    val createdAt: String,
    val fileUrl: String,
    val likes: Long,
    val author: User
)

// @Serializable
data class User(
    val id: Long,
    val avatar: String,
    val title: String
)
/*
@Serializable
data class GraphQLResponse(val message: String, val iss_position: IssPosition, val timestamp: Long)
*/
/*
class PostsGraphQLApi(
    private val client: HttpClient,
    var baseUrl: String = "https://mockend.com/theGlenn/fake-api/graphql",
) : KoinComponent {



    // Creates a 10MB MemoryCacheFactory
    val cacheFactory = MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024)


    private val apolloClient = ApolloClient.Builder()
        .serverUrl("https://rickandmortyapi.com/graphql")
        .normalizedCache(cacheFactory)
        .build()
/*
    suspend fun getPosts(page: Int): GetCharactersQuery.Characters {
        val response = apolloClient.query(GetCharactersQuery(page)).execute()
        return response.dataAssertNoErrors.characters
    }

    suspend fun getCharacter(characterId: String): CharacterDetail {
        val response = apolloClient.query(GetCharacterQuery(characterId)).execute()
        return response.dataAssertNoErrors.character.characterDetail
    }*/

   // suspend fun fetchISSPosition() = client.get(baseUrl).body<GraphQLResponse>()
}
 */

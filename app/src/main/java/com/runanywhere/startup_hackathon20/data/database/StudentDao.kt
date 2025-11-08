package com.runanywhere.startup_hackathon20.data.database

import androidx.room.*
import com.runanywhere.startup_hackathon20.data.models.KnightProfile
import com.runanywhere.startup_hackathon20.data.models.LearningRoute
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    // Knight Profile operations
    @Query("SELECT * FROM knight_profiles WHERE studentId = :studentId AND passwordHash = :passwordHash LIMIT 1")
    suspend fun login(studentId: String, passwordHash: String): KnightProfile?

    @Query("SELECT * FROM knight_profiles WHERE studentId = :studentId LIMIT 1")
    suspend fun getStudentByStudentId(studentId: String): KnightProfile?

    @Query("SELECT * FROM knight_profiles WHERE id = :id LIMIT 1")
    suspend fun getStudentById(id: String): KnightProfile?

    @Query("SELECT * FROM knight_profiles ORDER BY totalXP DESC")
    fun getAllStudentsFlow(): Flow<List<KnightProfile>>

    @Query("SELECT * FROM knight_profiles ORDER BY totalXP DESC LIMIT 10")
    suspend fun getLeaderboard(): List<KnightProfile>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: KnightProfile)

    @Update
    suspend fun updateStudent(student: KnightProfile)

    @Delete
    suspend fun deleteStudent(student: KnightProfile)

    @Query("UPDATE knight_profiles SET passwordHash = :newPasswordHash WHERE id = :studentId")
    suspend fun updatePassword(studentId: String, newPasswordHash: String)

    @Query("UPDATE knight_profiles SET knightName = :name, email = :email, phoneNumber = :phone WHERE id = :studentId")
    suspend fun updateProfile(studentId: String, name: String, email: String, phone: String)

    // Learning Routes operations
    @Query("SELECT * FROM learning_routes WHERE id = :routeId LIMIT 1")
    suspend fun getRoute(routeId: String): LearningRoute?

    @Query("SELECT * FROM learning_routes")
    fun getAllRoutesFlow(): Flow<List<LearningRoute>>

    @Query("SELECT * FROM learning_routes")
    suspend fun getAllRoutes(): List<LearningRoute>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoute(route: LearningRoute)

    @Update
    suspend fun updateRoute(route: LearningRoute)

    @Transaction
    suspend fun insertAllRoutes(routes: List<LearningRoute>) {
        routes.forEach { insertRoute(it) }
    }

    // Clear database methods
    @Query("DELETE FROM knight_profiles")
    suspend fun clearAllStudents()

    @Query("DELETE FROM learning_routes")
    suspend fun clearAllRoutes()

    @Transaction
    suspend fun clearAllData() {
        clearAllStudents()
        clearAllRoutes()
    }
}

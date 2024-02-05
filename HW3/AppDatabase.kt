package com.example.myapplication

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Upsert

@Database(
    entities = [User::class],
    version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val dao: UserDao

}

@Entity
data class User(
    val userName: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)

@Dao
interface UserDao {
    @Upsert
    fun addUser(user: User)

    @Query("SELECT * FROM user WHERE id=(SELECT max(id) FROM user)")
    fun getUser(): User

    @Query("SELECT COUNT(*) FROM user")
    fun getRowCount(): Int

}

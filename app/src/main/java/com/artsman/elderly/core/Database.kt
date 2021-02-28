package com.artsman.elderly.core

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.artsman.elderly.care_taker.repo.DBEvent
import com.artsman.elderly.patient_list.repo.DBPatient
import java.util.*

@Dao
interface EventDao {
    @Insert
    fun insertAll(vararg events: DBEvent)

    @Query("select * from events_table")
    fun getAllEventAsync(): List<DBEvent>

    @Query("select * from events_table")
    fun getAllEvent(): LiveData<List<DBEvent>>

    @Delete
    fun delete(vararg events:DBEvent)
}

@Dao
interface PatientDao{
    @Insert
    fun insertAll(vararg patients:DBPatient)

    @Query("select * from patient_table")
    fun getAllPatientsAsync():List<DBPatient>

    @Query("select * from patient_table")
    fun getAllPatients():LiveData<List<DBPatient>>

    @Delete
    fun delete(vararg patients:DBPatient)
}


@Database(entities = arrayOf(DBEvent::class,DBPatient::class), version = 1)
abstract class StepDataBase : RoomDatabase() {
    abstract fun getEventDao(): EventDao
    abstract fun getPatientDao():PatientDao
}


//    val db=Room.databaseBuilder(
//        applicationContext,
//        StepDataBase::class.java,"EventDatabase"
//    ).build()

class DatabaseProvider private constructor(private var context: Context) {

    private var mDatabaseInstance: StepDataBase?= null

    init {
        mDatabaseInstance= createDatabase()
    }

    fun getDatabase()= mDatabaseInstance

    private fun createDatabase(): StepDataBase {
        return  Room.databaseBuilder(
            context,
            StepDataBase::class.java, "database_elderly"
        ).build()
    }

    companion object{
        private var _INSTANCE: DatabaseProvider?=null
        fun getInstance(context: Context): DatabaseProvider {
             if(_INSTANCE==null){
                _INSTANCE= DatabaseProvider(context)
             }
            return _INSTANCE as DatabaseProvider
        }
    }
}

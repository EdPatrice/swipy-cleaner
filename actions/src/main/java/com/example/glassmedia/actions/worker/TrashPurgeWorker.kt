package com.example.glassmedia.actions.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.glassmedia.data.local.dao.TrashDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.io.File
import android.net.Uri

@HiltWorker
class TrashPurgeWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val trashDao: TrashDao
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val now = System.currentTimeMillis()
            val expiredItems = trashDao.getExpiredItems(now)

            for (item in expiredItems) {
                // Delete the physical file
                try {
                    val trashUri = Uri.parse(item.trashUri)
                    // If it is a file scheme
                    if (trashUri.scheme == "file") {
                        val file = File(trashUri.path!!)
                        if (file.exists()) {
                            file.delete()
                        }
                    } else {
                        // Content provider (SAF)
                        applicationContext.contentResolver.delete(trashUri, null, null)
                    }
                    // Remove from DB
                    trashDao.delete(item)
                } catch (e: Exception) {
                    e.printStackTrace()
                    // Continue with next item even if one fails
                }
            }
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}

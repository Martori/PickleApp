package cat.martori.pickleapp

import android.app.Application
import cat.martori.pickleapp.data.di.dataModule
import cat.martori.pickleapp.domain.di.domainModule
import cat.martori.pickleapp.ui.di.uiModule
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PickleApp : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@PickleApp)
            modules(domainModule + uiModule + dataModule)
        }
    }

    override fun newImageLoader() = ImageLoader.Builder(this)
        .memoryCache {
            MemoryCache.Builder(this).maxSizePercent(0.25).build()
        }
        .diskCache {
            DiskCache.Builder()
                .directory(cacheDir.resolve("image_cache"))
                .maxSizePercent(0.02)
                .build()
        }
        .build()

}